package com.dsn.andy.bgmlauncher.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.andy.greendao.dao.COMCommandDao;
import com.andy.music.COMCommand;
import com.dsn.andy.bgmlauncher.DSNApplication;
import com.dsn.andy.bgmlauncher.db2.DBOperator;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceType;
import com.dson.smart.common.entity.DsonSmartScene;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 广播接收者
 *
 * @author ysc
 */

public class AiuiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.intent.msg.AIUITEXT".equals(action)) {
            Bundle bundle = intent.getExtras();
            String aiuitext = bundle.getString("hktext");
            Log.i("andy", "这个是语义结果=" + aiuitext);

            boolean supportSmarthome = false;
            if(AndroidUtils.isInstallApp(context,AndroidUtils.SDK_PACKAGE_NAME)){ //已安装智能家居插件APP
                parseSmartHome(context, aiuitext);
                supportSmarthome = true;
            }

            if(DSNApplication.getInstance().isSupport485()){//支持485智能家居
                supportSmarthome = true;
                try {
                    JSONObject json = new JSONObject(aiuitext);
                    String text = json.getString("text");
                    parseSmartHome485(context, text);
                }catch (JSONException ex){

                }
            }

            if(!supportSmarthome){
                reply(context,0,aiuitext);
            }

        }
    }

    /*
  解析执行485指令
   */
    public boolean parseSmartHome485(Context context,String text) {
        Log.e("andy","485 "+text);
        String[] ops = {"打开", "启动", "开启", "执行", "关闭", "停止", "关上"};
        String start = null;
        int position = -1;
        for (String str : ops) {
            position++;
            if (text.startsWith(str)) {
                start = str;
                break;
            }
        }
        //表示语音命令是以上述关键词开头
        if (start != null && !text.equals(start)) {
            String name = text.substring(start.length());//获取设备名称
            //485的每一个指令的命名就是一条语音命令
            String where = " where "+ COMCommandDao.Properties.CommandName.columnName+" like \'%"+text+"%\'";
            Log.e("andy","485 where="+where);
            List<COMCommand> comCommandList = new DBOperator<COMCommand>(context).query(
                    new COMCommand(),where,null
            );

            if(comCommandList!=null && !comCommandList.isEmpty()){
                Log.e("andy","485 "+comCommandList.size());
                COMCommand command = comCommandList.get(0);
                boolean ret = DSNApplication.getInstance().sendSerialPortData(command.getCommandBytes());
                if(ret){
                    reply(context,1,text+" 成功");
                }else{
                    reply(context,1,text+" 失败");
                }
            }else{
                Log.e("andy","485 command not found");
                reply(context,0,"没有找到485指令");
            }

        }

        return false;

    }



    /*
      *result:1-操作成功，-1-操作失败 0:不处理
      * str:操作结果，比如空调已为您调到33摄氏度
     */
    public static void reply(Context context,int result, String str) {
        Intent intent = new Intent("android.intent.msg.OTHER");
        try {
            JSONObject json = new JSONObject();
            json.put("result", String.valueOf(result));
            json.put("text", str);
            Bundle bundle = new Bundle();
            bundle.putString("othertext", json.toString());
            intent.putExtras(bundle);
            context.sendBroadcast(intent);
            Log.e("andy","reply str="+json.toString());
        }catch (Exception e){

        }
    }

    /*
    单纯从字面上去解析
     */
    private static boolean doDevice(Context context,String text){
        Log.e("andy","do device,text="+text);
        SmarthomeTool smarthome = SmarthomeTool.getInstance(context.getApplicationContext());
        return smarthome.doDevice(text);
    }

    public static  void parseSmartHome(Context context, String result) {
        try {
            Log.e("andy", "aiui=" + result);
            JSONObject json = new JSONObject(result);
            String text = json.getString("text");

            //将语音识别的字符串通过广播发给第三方APP
            Intent it = new Intent("com.dson.smarthome_audio_text");
            it.putExtra("audio_text",text);
            context.sendBroadcast(it);

            //先自定义解析处理用户的语音命令词句，提高精准度
            if(doDevice(context,text)){
                return;
            }


            int rc = json.getInt("rc");
            if (rc == 0) {// 符合语义

                SmarthomeTool smarthome = SmarthomeTool.getInstance(context.getApplicationContext());

                JSONObject semantic = json.getJSONObject("semantic");
                JSONObject slots = semantic.getJSONObject("slots");

                String atype = slots.getString("attrType");
                String attr = slots.getString("attr");

                String op = json.getString("operation");
                String service = json.getString("service");

                String modifier = "";
                String room = "";
                String avalue = "";
                try {
                    if(result.contains("modifier")) {
                        modifier = slots.getString("modifier");
                    }
                } catch (Exception e) {
                    modifier = "";
                }
                if (modifier == null || "所有".equals(modifier)
                        || "全部".equals(modifier))
                    modifier = "";


                try {
                    if(result.contains("location")) {
                        JSONObject location = slots
                                .getJSONObject("location");
                        if (location != null) {
                            room = location.getString("room");
                        }
                    }
                } catch (Exception e) {
                    room = "";
                }

                try {
                    avalue = slots.getString("attrValue");
                } catch (Exception e) {
                    avalue = "";
                }

                Log.e("andy", "service=" + service+",room="+room+",modifier="+modifier);

                if ("SET".equals(op)) {
                    if (service.equals("airControl_smartHome")) {// 空调
                        if ("开关".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setACPower(room, modifier, true);
                            } else if ("关".equals(avalue)) {
                                Log.e("andy","关空调");
                                smarthome.setACPower(room, modifier, false);
                            }
                        } else if ("制热".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_HOT);
                            } else if ("关".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_AUTO);
                            }
                        } else if ("制冷".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_COOL);
                            } else if ("关".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_AUTO);
                            }
                        } else if ("除湿".equals(attr) || "干燥".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_DRY);
                            } else if ("关".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_AUTO);
                            }
                        } else if ("自动".equals(attr)) {
                            smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_AUTO);
                        } else if ("送风".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_FAN);
                            } else if ("关".equals(avalue)) {
                                smarthome.setACMode(room, modifier, DsonCommand.DATA_MODE_AUTO);
                            }
                        } else if ("温度".equals(attr)) {
                            try {
                                avalue = slots.getString("attrValue");
                                smarthome.setACTemperature(room, modifier, Integer.parseInt(avalue));
                            } catch (Exception e) {
                                JSONObject avalue2 = slots
                                        .getJSONObject("attrValue");
                                String direct = avalue2.getString("direct");
                                String offset = avalue2.getString("offset");
                                if ("+".equals(direct)) {
                                    smarthome.setACTemperatureOffset(room, modifier, true, Integer.parseInt(offset));
                                } else if ("-".equals(direct)) {
                                    smarthome.setACTemperatureOffset(room, modifier, false, Integer.parseInt(offset));
                                }
                            }
                        } else if ("风速".equals(attr)) {
                            if (atype.equals("string")) {
                                if ("高风".equals(avalue)) {
                                    smarthome.setACWindSpeed(room, modifier, DsonCommand.DATA_WIND_SPEED_HIGH);
                                } else if ("中风".equals(avalue)) {
                                    smarthome.setACWindSpeed(room, modifier, DsonCommand.DATA_WIND_SPEED_MIDDLE);
                                } else if ("低风".equals(avalue)) {
                                    smarthome.setACWindSpeed(room, modifier, DsonCommand.DATA_WIND_SPEED_LOW);
                                } else if ("自动风".equals(avalue)) {
                                    smarthome.setACWindSpeed(room, modifier, DsonCommand.DATA_WIND_SPEED_AUTO);
                                }
                            } else if (atype.equals("Object(digital)")) {
//                                JSONObject avalue = slots
//                                        .getJSONObject("attrValue");
//                                String direct = avalue.getString("direct");
//                                if ("+".equals(direct)) {
//                                    doIRDevice(TYPE_IR_AC, "windup", "-1");
//                                } else if ("-".equals(direct)) {
//                                    doIRDevice(TYPE_IR_AC, "winddown", "-1");
//                                }
                            }
                        } else if ("左右扫风".equals(attr)) {
                            smarthome.setACWindDir(room, modifier, DsonCommand.DATA_WIND_DIR_LEFT_RIGHT);
                        } else if ("上下扫风".equals(attr)) {
                            smarthome.setACWindDir(room, modifier, DsonCommand.DATA_WIND_DIR_UP_DOWN);
                        }
                    } else if (service.equals("tv_smartHome")) {// 电视
                        if ("开关".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setTVPower(room, modifier, true);
                            } else if ("关".equals(avalue)) {
                                smarthome.setTVPower(room, modifier, false);
                            }
                        } else if ("频道".equals(attr)) {
                            if ("Object(digital)".equals(atype)) {
                                JSONObject obj = slots
                                        .getJSONObject("attrValue");
                                String direct = obj.getString("direct");
                                String offset = obj.getString("offset");
                                if ("+".equals(direct)) {
                                    smarthome.setTVChannel(room, modifier, true);
                                } else if ("-".equals(direct)) {
                                    smarthome.setTVChannel(room, modifier, false);
                                }
                            } else if ("Integer".equals(atype)) {
                                String str = slots.getString("attrValue");
                                smarthome.setTVChannel(room, modifier, Integer.parseInt(str));
                            }
                        } else if ("音量".equals(attr)) {
                            if ("Object(digital)".equals(atype)) {
                                JSONObject obj = slots
                                        .getJSONObject("attrValue");
                                String direct = obj.getString("direct");
                                if ("+".equals(direct)) {
                                    smarthome.setTVVolume(room, modifier, true);
                                } else if ("-".equals(direct)) {
                                    smarthome.setTVVolume(room, modifier, false);
                                }
                            }
                        } else if ("静音".equals(attr)) {
                            smarthome.setTVMute(room, modifier, true);
                        }else if("确定".equals(attr)){
                            if("设置".equals(avalue)){
                                smarthome.setTVOK(room,modifier);
                            }
                        }
                    } else if (service.equals("light_smartHome")
                    || service.equals("switch_smartHome")
                            ) {// 灯
                        Log.e("andy","light smarthome ,attr="+attr+",avalue="+avalue);
                        Log.e("andy","room="+room+",modifier="+modifier);

                        if ("开关".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setLightSwitch(room, modifier, true);
                            } else if ("关".equals(avalue)) {
                                smarthome.setLightSwitch(room, modifier, false);
                            }
                        } else if (text.contains("模式")) {
                            smarthome.openScene(attr);
                        }
                    } else if ( service.equals("slot_smartHome")
                            ) {

                        if ("开关".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setSocket(room, modifier, true);
                            } else if ("关".equals(avalue)) {
                                smarthome.setSocket(room, modifier, false);
                            }

                        }
                    } else if (service.equals("curtain_smartHome")) {
                        if ("开关".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setCurtainSwitch(room, modifier, true);
                            } else if ("关".equals(avalue)) {
                                smarthome.setCurtainSwitch(room, modifier, false);
                            }

                        }
                    } else if (service.equals("window_smartHome")) {// 门窗
                        if ("开关".equals(attr)) {
                            if ("开".equals(avalue)) {
                                smarthome.setWindowPush(room, modifier, true);
                            } else if ("关".equals(avalue)) {
                                smarthome.setWindowPush(room, modifier, false);
                            }

                        }
                    }else {
                        Log.e("andy","other service");
                        reply(context,0,result);
                    }
                }

            } else {

                parseInvalidStr(context.getApplicationContext(),result, text);

            }

        } catch (Exception e) {
            e.printStackTrace();
            reply(context,0,result);
        }

    }

    private static void parseInvalidStr(Context context,String result, String text) {
        SmarthomeTool smarthome = SmarthomeTool.getInstance(context);
        if ((text.startsWith("执行")
                || text.startsWith("启动")
                || text.startsWith("打开"))
                && text.contains("模式")) {
            int start = 2;
            int end = text.lastIndexOf("模式");
            String sceneName = text.substring(start, end);
            if(TextUtils.isEmpty(sceneName)){
                Log.e("andy","parse scene");
                reply(context,0,result);
                return;
            }

            smarthome.openScene(sceneName);

        }else{
            Log.e("andy","parse scene 222");
            reply(context,0,result);
        }
    }

}
