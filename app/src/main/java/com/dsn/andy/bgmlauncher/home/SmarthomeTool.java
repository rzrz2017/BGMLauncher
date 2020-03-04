package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceOrder;
import com.dson.smart.common.entity.DsonSmartDeviceType;
import com.dson.smart.common.entity.DsonSmartRoom;
import com.dson.smart.common.entity.DsonSmartScene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/5/10.
 * 命令组装
 */

public class SmarthomeTool {

    private static SmarthomeTool instance;
    private Context mContext;

    private SmarthomeTool(Context context) {
        this.mContext = context;
    }

    public synchronized static SmarthomeTool getInstance(Context context) {
        if (instance == null) {
            instance = new SmarthomeTool(context);
        }
        return instance;
    }

    /*
    先从字面意义上去解析
     */
    public boolean doDevice(String text) {
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
            DsonSmartDevice device = findDeviceByNameEquals(name);
            Log.e("andy","find device "+(device==null));
            if (device != null) {//说 ‘打开客厅灯’
                if (DsonSmartDeviceType.isOnOffDevice(Integer.parseInt(device.getDeviceType()))
                        || DsonSmartDeviceType.isThreeStatusDevice(Integer.parseInt(device.getDeviceType()))
                        ) {
                    if (position <= 3) {
                        setONOFF(device, true);
                    } else {
                        setONOFF(device, false);
                    }

                    reply("已为您"+start+device.getDeviceName());
                    return true;
                }
            }else{//说“执行起床模式”
                DsonSmartScene scene = findSceneByNameEquals(name);
                if(scene != null){
                    openScene(scene.getSceneName());
                    reply("已为您"+start+scene.getSceneName());
                    return true;
                }
            }

        }else if(start == null){ // 直接说“起床”
            DsonSmartScene scene = findSceneByNameEquals(text);
            if(scene != null){
                openScene(scene.getSceneName());
                reply("已为您启动"+scene.getSceneName());
                return true;
            }
        }

        return false;

    }

    /*
    精准匹配设备名称
     */
    public DsonSmartDevice findDeviceByNameEquals(String name) {
        name = name.trim();
        Log.e("andy","name="+name);
        if (TextUtils.isEmpty(name.trim())) return null;


        List<DsonSmartDevice> devices = DsonConstants.getInstance().getDevices().getDevices();
        for (DsonSmartDevice d : devices) {
            String dname = d.getDeviceName().trim();
            Log.e("andy","dname="+dname);
            if(dname.equals(name)) {
                Log.e("andy","equal s  ");
                return d;
            }
            if ((name.startsWith(dname) || dname.startsWith(name))
                    ) {
                Log.e("andy","find "+dname);
                return d;
            }
        }
        Log.e("andy","find not device");
        return null;

    }


    interface SmarthomeCallback {
        void control(DsonSmartDevice device);
    }

    private List<DsonSmartDevice> findDeviceFrom(String room) {
        if (TextUtils.isEmpty(room)) {
            if (DsonConstants.getInstance().getDevices() == null) return null;
            return DsonConstants.getInstance().getDevices().getDevices();
        } else {
            if (DsonConstants.getInstance().getRooms() == null) return null;
            for (DsonSmartRoom r : DsonConstants.getInstance().getRooms().getRooms()) {
                if (r.getRoomName().contains(room)) {
                    return r.getDevices();
                }
            }
        }

        return null;
    }

    private DsonSmartScene findSceneByNameEquals(String name) {
        if (TextUtils.isEmpty(name)) return null;
        if (DsonConstants.getInstance().getScenes() == null) return null;
        for (DsonSmartScene scene : DsonConstants.getInstance().getScenes().getScenes()) {
            if (scene.getSceneName().equals(name)) {
                return scene;
            }
        }

        return null;
    }

    private DsonSmartScene findScene(String name) {
        if (TextUtils.isEmpty(name)) return null;
        if (DsonConstants.getInstance().getScenes() == null) return null;
        for (DsonSmartScene scene : DsonConstants.getInstance().getScenes().getScenes()) {
            if (scene.getSceneName().contains(name)) {
                return scene;
            }
        }

        return null;
    }

    public void openScene(String name) {
        DsonSmartScene smartScene = findScene(name);
        if (smartScene != null) {
            SmarthomeHelper.getInstance(mContext).openScene(smartScene);
            AiuiReceiver.reply(mContext, 1, "已为您启动情景模式:" + smartScene.getSceneName());
        } else {
            AiuiReceiver.reply(mContext, -1, "未找到合适的情景模式");
        }
    }


    private List<DsonSmartDevice> controlDevice(String room, String modifier, int type, SmarthomeCallback callback) {
        if (DsonConstants.getInstance().getDevices() == null) return null;
        List<DsonSmartDevice> devices = findDeviceFrom(room);
        if (devices == null) {
            Log.e("andy", "devices is null");
//            return null;

        }

        //打开客厅灯
        if (TextUtils.isEmpty(modifier) && !TextUtils.isEmpty(room)) {
            modifier = room;
        }

        devices = DsonConstants.getInstance().getDevices().getDevices();

        List<DsonSmartDevice> result = new ArrayList();
        for (DsonSmartDevice d : devices) {
            if ((d.getDeviceName().contains(modifier) || TextUtils.isEmpty(modifier))
                    && d.getDeviceType().equals(type + "")) {
                callback.control(d);
                result.add(d);
                Log.e("andy", "find a device:" + d.getDeviceName());
            }
        }

        return result;

    }

    private void reply(String text){
        AiuiReceiver.reply(mContext, 1, text);
    }

    private void reply(List<DsonSmartDevice> devices, String action) {
        if (devices == null || devices.isEmpty()) {
            Log.e("andy", "devices is null");
            AiuiReceiver.reply(mContext, -1, "没有找到相关设备");
            return;
        }

        if (devices.size() == 1) {
            AiuiReceiver.reply(mContext, 1, action);
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(action + ",包括");
            for (int i = 0; i < devices.size(); i++) {
                DsonSmartDevice device = devices.get(i);
                buffer.append(device.getDeviceName());
                if (i == devices.size() - 1) {
                    buffer.append("。");
                } else {
                    buffer.append("，");
                }

            }

            AiuiReceiver.reply(mContext, 1, buffer.toString());

        }

    }

    public void setACPower(String room, String modifier, final boolean isON) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(isON ? DsonSmartDeviceOrder.OPEN : DsonSmartDeviceOrder.CLOSE);
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });

        reply(result, isON ? "已为您打开空调" : "已为您关闭空调");
    }

    public void setACMode(String room, String modifier, final int mode) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.SET);
                        cmd.setValue1(DsonSmartDeviceOrder.AIRCONDITION_MODE_TYPE);
                        String[] modeValues = {
                                DsonSmartDeviceOrder.AIRCONDITION_MODE_AUTO,
                                DsonSmartDeviceOrder.AIRCONDITION_MODE_COOL,
                                DsonSmartDeviceOrder.AIRCONDITION_MODE_DEHUMIDIFY,
                                DsonSmartDeviceOrder.AIRCONDITION_MODE_WIND,
                                DsonSmartDeviceOrder.AIRCONDITION_MODE_HEAT

                        };
                        cmd.setValue2(modeValues[mode - 1]);
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        reply(result, "已为您将空调调到" + DsonCommand.getModeName(mode));
    }

    public void setACTemperature(String room, String modifier, final int temperature) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.MOVE_TO_LEVEL);
                        cmd.setValue1(temperature + "");
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        reply(result, "已为您将空调调到" + temperature + "摄氏度");
    }

    public void setACTemperatureOffset(String room, String modifier, final boolean isUp, final int offset) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.TEMPERATURE_OFFSET);
                        cmd.setValue1((isUp ? 1 : 0) + "");
                        cmd.setValue2(offset + "");
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        String action = "已为您将空调温度" + (isUp ? "调高" : "调低") + offset + "度";
        reply(result, action);
    }

    public void setACWindSpeed(String room, String modifier, final int speed) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.SET);
                        cmd.setValue1(DsonSmartDeviceOrder.AIRCONDITION_WIND_RATE_TYPE);
                        String[] speedValues = {
                                DsonSmartDeviceOrder.AIRCONDITION_WIND_RATE_AUTO,
                                DsonSmartDeviceOrder.AIRCONDITION_WIND_RATE_LOW,
                                DsonSmartDeviceOrder.AIRCONDITION_WIND_RATE_MIDDLE,
                                DsonSmartDeviceOrder.AIRCONDITION_WIND_RATE_HIGH,
                        };
                        cmd.setValue2(speedValues[speed - 1]);
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        String action = "已为您将空调风速调到" + DsonCommand.getFanLevelName(speed);
        reply(result, action);
    }


    public void setACWindDir(String room, String modifier, final int dir) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.SET);
                        cmd.setValue1(DsonSmartDeviceOrder.AIRCONDITION_WIND_DIRECTION_TYPE);
                        String[] dirValues = {

                                DsonSmartDeviceOrder.AIRCONDITION_WIND_DIRECTION_LEFT_RIGHT,
                                DsonSmartDeviceOrder.AIRCONDITION_WIND_DIRECTION__UP_DOWN,

                        };
                        cmd.setValue2(dirValues[dir - 1]);
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        String action = "已为您将空调风向调到" + DsonCommand.getFanDirName(dir);
        reply(result, action);
    }

    public void setTVPower(String room, String modifier, final boolean isON) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_TV,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(isON ? DsonSmartDeviceOrder.OPEN : DsonSmartDeviceOrder.CLOSE);
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        String action = "已为您" + (isON ? "打开" : "关闭") + "电视";
        reply(result, action);
    }

    public void setTVChannel(String room, String modifier, final boolean isNext) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_TV,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.TV_CHANNEL_DIR);
                        cmd.setValue1(isNext ? "1" : "0");
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        reply(result, "已跳转到" + (isNext ? "下一频道" : "上一频道"));
    }

    public void setTVChannel(String room, String modifier, final int channel) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_TV,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.TV_CHANNEL);
                        cmd.setValue1(channel + "");
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        reply(result, "已跳转到" + channel + "频道");
    }

    public void setTVVolume(String room, String modifier, final boolean isNext) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_TV,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.TV_VOLUME_DIR);
                        cmd.setValue1(isNext ? "1" : "0");
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        reply(result, "电视音量已调" + (isNext ? "大" : "小"));
    }

    public void setTVMute(String room, String modifier, final boolean isMute) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_TV,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.MUTE);
                        cmd.setValue1(isMute ? "1" : "0");
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        reply(result, "电视已" + (isMute ? "静音" : "取消静音"));
    }

    public void setTVOK(String room, String modifier){
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_TV,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
                        cmd.setOrder(DsonSmartDeviceOrder.TV_OK);
                        cmd.setDeviceId(device.getDeviceId());
                        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
                    }
                });
        reply(result, "已确定");
    }


    public void setLightSwitch(String room, String modifier, final boolean isON) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_LAMP,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        setONOFF(device, isON);
                    }
                });
        String action = "已为您" + (isON ? "打开" : "关闭") + "灯光";
        if (result != null && result.size() == 1) {
            action = "已为您" + (isON ? "打开" : "关闭") + result.get(0).getDeviceName();
        }
        reply(result, action);
    }


    public void setSocket(String room, String modifier, final boolean isON) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_SOCKET,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        setONOFF(device, isON);
                    }
                });
        String action = "已为您" + (isON ? "打开" : "关闭") + "插座";
        if (result != null && result.size() == 1) {
            action = "已为您" + (isON ? "打开" : "关闭") + result.get(0).getDeviceName();
        }
        reply(result, action);
    }

    public void setCurtainSwitch(String room, String modifier, final boolean isON) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_CURTAINS,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        setONOFF(device, isON);
                    }
                });

        String action = "已为您" + (isON ? "打开" : "关闭") + "窗帘";
        if (result != null && result.size() == 1) {
            action = "已为您" + (isON ? "打开" : "关闭") + result.get(0).getDeviceName();
        }
        reply(result, action);
    }

    public void setWindowPush(String room, String modifier, final boolean isON) {
        List<DsonSmartDevice> result = controlDevice(room, modifier, DsonSmartDeviceType.DEVICE_TYPE_WINDOW_CONTROLER,
                new SmarthomeCallback() {
                    @Override
                    public void control(DsonSmartDevice device) {
                        setONOFF(device, isON);
                    }
                });
        String action = "已为您" + (isON ? "打开" : "关闭") + "推窗器";
        if (result != null && result.size() == 1) {
            action = "已为您" + (isON ? "打开" : "关闭") + result.get(0).getDeviceName();
        }
        reply(result, action);
    }

    public void setONOFF(DsonSmartDevice device, boolean isON) {
        DsonSmartCtrlCmd cmd = new DsonSmartCtrlCmd();
        cmd.setOrder(isON ? DsonSmartDeviceOrder.OPEN : DsonSmartDeviceOrder.CLOSE);
        cmd.setDeviceId(device.getDeviceId());
        SmarthomeHelper.getInstance(mContext).controlDevice(cmd);
    }


}
