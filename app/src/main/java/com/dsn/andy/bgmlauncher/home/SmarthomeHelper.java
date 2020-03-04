package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceType;
import com.dson.smart.common.entity.DsonSmartDevices;
import com.dson.smart.common.entity.DsonSmartHostInfo;
import com.dson.smart.common.entity.DsonSmartRooms;
import com.dson.smart.common.entity.DsonSmartScene;
import com.dson.smart.common.entity.DsonSmartScenes;
import com.dson.support.dsonbase.DsonbaseContant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/4/25.
 */

public class SmarthomeHelper implements ServiceHelper.OnRemoteCallbackListener{

    private static SmarthomeHelper instance;
    private Context mContext;
    private ServiceHelper helper;

    private boolean hasLogin;

    private SmarthomeHelper(Context context){
        this.mContext = context;
        helper = ServiceHelper.getInstance(context);
    }

    public synchronized static SmarthomeHelper getInstance(Context context){
        if(instance == null){
            instance = new SmarthomeHelper(context.getApplicationContext());
        }

        return instance;
    }

    public void init(){

        helper.init();

        helper.setOnRemoteListener(this);

    }

    private void broadcast(String action){
        Intent it = new Intent(action);
        mContext.sendBroadcast(it);
    }

    void show(final String str){
        Handler handler = new Handler(mContext.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                DsonUtils.show(mContext,str);
            }
        });
    }

    public void login(String user,String pwd){
        helper.login(user,pwd);
    }

    public void exit(){
        helper.logout();
    }

    public void controlDevice(DsonSmartCtrlCmd cmd){
        helper.controlDevice(cmd);
    }

    public void openScene(DsonSmartScene scene){
        helper.controlScene(scene);
    }





    @Override
    public void onInit(int code) {
        Intent it = new Intent(LoginBroadcastReceiver.ACTION_INIT_RESULT);
        it.putExtra(BaseBroadcastReceiver.FIELD_RESULT_CODE,code);
        mContext.sendBroadcast(it);
    }

    @Override
    public void onGetHostInfo(int code, DsonSmartHostInfo info) {
        if(code == DsonbaseContant.RESULT_SUCCESS){
            DsonConstants.getInstance().setHostInfo(info);
        }

        broadcast(LoginBroadcastReceiver.ACTION_GET_HOSTINFO);

    }

    @Override
    public void onGetDeviceList(int code, DsonSmartDevices devices) {
        if(code == DsonbaseContant.RESULT_SUCCESS){
            Log.e("andy","get device list success");
            DsonConstants.getInstance().setDevices(devices);
        }

        broadcast(DataBroadcastReceiver.ACTION_GET_ALL_DEVICE);
    }

    @Override
    public void onLogin(int code) {
        Intent it = new Intent(LoginBroadcastReceiver.ACTION_LOGIN_RESULT);
        it.putExtra(BaseBroadcastReceiver.FIELD_RESULT_CODE,code);
        mContext.sendBroadcast(it);

        if(code == DsonbaseContant.RESULT_SUCCESS){
            DsonConstants.getInstance().setLogin(true);
        }
    }

    @Override
    public void onLogout(int code) {
        if(code == DsonbaseContant.RESULT_SUCCESS){
           show("退出成功");
        }
    }

    @Override
    public void onControlScene(int code, String str) {
        if(code == DsonbaseContant.RESULT_SUCCESS){
             show(str);
        }
    }

    @Override
    public void onGetSceneList(int code, DsonSmartScenes scenes) {
        if(code == DsonbaseContant.RESULT_SUCCESS){
            DsonConstants.getInstance().setScenes(scenes);
        }

        broadcast(DataBroadcastReceiver.ACTION_GET_ALL_SCENE);

    }

    @Override
    public void onGetAllDeviceType(int code, List<String> types) {
        if(code == DsonbaseContant.RESULT_SUCCESS){
            List<DsonSmartDeviceType> list = new ArrayList();
            for (String t:types){
                DsonSmartDeviceType obj = new DsonSmartDeviceType(Integer.parseInt(t));
                if(obj.getType() == -1) continue;
                list.add(obj);
            }
            DsonConstants.getInstance().setTypes(list);

            broadcast(DataBroadcastReceiver.ACTION_GET_ALL_DEVICE_TYPE);

        }
    }

    @Override
    public void onGetRoomList(int code, DsonSmartRooms rooms) {
        if(code == DsonbaseContant.RESULT_SUCCESS){

            DsonConstants.getInstance().setRooms(rooms);
        }

        broadcast(DataBroadcastReceiver.ACTION_GET_ALL_ROOM);
    }

    @Override
  public void onControlDevice(int code,DsonSmartCtrlCmd cmd){
        if(code == DsonbaseContant.RESULT_SUCCESS){
            DsonSmartDevices devices = DsonConstants.getInstance().getDevices();
            List<DsonSmartDevice> list = devices.getDevices();
            for (int i=0;i<list.size();i++){
                DsonSmartDevice d = list.get(i);
                if(d.getDeviceId().equals(cmd.getDeviceId())){
                    d.setDsonSmartCtrlCmd(cmd);
                    broadcast(DataBroadcastReceiver.ACTION_DEVICE_UPDATE);
                    break;
                }
            }
        }
  }


    @Override
    public void onUpdateDevice(int code, DsonSmartDevice device) {
        if(code == DsonbaseContant.RESULT_SUCCESS){
            DsonSmartDevices devices = DsonConstants.getInstance().getDevices();
            List<DsonSmartDevice> list = devices.getDevices();
            int position = -1;
            for (int i=0;i<list.size();i++){
                DsonSmartDevice d = list.get(i);
                if(d.equals(device)){
                    position = i;
                    break;
                }
            }
            Log.e("andy","position="+position);
            if(position != -1){
                list.set(position,device);

                broadcast(DataBroadcastReceiver.ACTION_DEVICE_UPDATE);

            }
        }
    }
}
