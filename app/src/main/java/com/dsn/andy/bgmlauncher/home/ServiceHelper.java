package com.dsn.andy.bgmlauncher.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDevices;
import com.dson.smart.common.entity.DsonSmartHostInfo;
import com.dson.smart.common.entity.DsonSmartRooms;
import com.dson.smart.common.entity.DsonSmartScene;
import com.dson.smart.common.entity.DsonSmartScenes;
import com.dson.smart.common.virtualhost.DsonSmartHostActionConstant;
import com.dson.support.dsonbase.DsonbaseContant;
import com.dson.support.dsonbase.aidl.IAidlCallback;
import com.dson.support.dsonbase.aidl.IAidlCtrl;

import java.util.List;

/**
 * Created by dell on 2018/4/25.
 */

public class ServiceHelper {

    private static ServiceHelper instance;


    public interface OnRemoteCallbackListener{
         void onInit(int code);
         void onGetHostInfo(int code,DsonSmartHostInfo info);
         void onGetDeviceList(int code, DsonSmartDevices devices);
         void onLogin(int code);
         void onLogout(int code);
         void onControlScene(int code, String data);
         void onGetSceneList(int code, DsonSmartScenes scenes);
         void onGetAllDeviceType(int code,List<String> types);
         void onGetRoomList(int code, DsonSmartRooms rooms);
         void onUpdateDevice(int code, DsonSmartDevice device);
        void onControlDevice(int code,DsonSmartCtrlCmd cmd);

    }

    private static final String ACTION = "com.andy.dson.bind_service";
    private static final String SERVICE_NAME = "com.andy.dson.dsonsmart.open.CustomSmartService";

    private Context mContext;
    private Handler handler;
    private Intent intent;
    private ServiceConnection serviceConnection;

    OnRemoteCallbackListener listener;

    private static boolean isInit ;

    /*
    远程接口的代理
     */
    IAidlCtrl aidlCtrl;

    /*
    远程操作结果的回调
     */
    IAidlCallback aidlCallback = new IAidlCallback.Stub() {
        @Override
        public void onResult(int action, int code, String data1, String data2) throws RemoteException {
            Log.e("andy","aidlcallbck onResult,action=" + action + ",code=" + code + ",data1=" + data1 + ",data2=" + data2);
            if(listener == null) return;
            switch (action) {
                case DsonSmartHostActionConstant.ACTION_GET_ALL_DEVICE://获取设备列表
                    if (code == DsonbaseContant.RESULT_SUCCESS) {
                        try {
                            DsonSmartDevices devices = JSON.parseObject(data1, DsonSmartDevices.class);
                            listener.onGetDeviceList(code, devices);

                        } catch (Exception e) {
                            listener.onGetDeviceList(DsonbaseContant.RESULT_FAIL, null);
                        }
                    } else {
                        listener.onGetDeviceList(code, null);
                    }
                    break;
                case DsonSmartHostActionConstant.ACTION_DEVICE_STATUS_UPDATE://设备状态改变
                    listener.onUpdateDevice(code, JSON.parseObject(data1, DsonSmartDevice.class));
                    break;
                case DsonSmartHostActionConstant.ACTION_INIT_HOST://初始化完毕
                    listener.onInit(code);

                    if (code == DsonbaseContant.RESULT_SUCCESS) {
                        String hostInfo = aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_GET_HOST_INFORMATION, null, null, this);
                        Log.e("andy","host="+hostInfo);
//                        show(hostInfo);
                        DsonSmartHostInfo info = JSON.parseObject(hostInfo, DsonSmartHostInfo.class);
                        if (info != null) {
                            listener.onGetHostInfo(DsonbaseContant.RESULT_SUCCESS, info);
                            //不支持登录，则直接获取设备列表
                            if(!info.isSupportLogin()){
                                aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_GET_ALL_DEVICE,null,null,this);
                            }

                        } else {
                            listener.onGetHostInfo(DsonbaseContant.RESULT_FAIL, null);
                        }

                    }
                    break;
                case DsonSmartHostActionConstant.ACTION_LOGIN:
                    listener.onLogin(code);
                    Log.e("andy","login "+code);
                    if(code == DsonbaseContant.RESULT_SUCCESS){
                        //登录成功，则获取设备列表

                    }else{
                        show(data1);
                    }
                    break;
                case DsonSmartHostActionConstant.ACTION_LOGOUT:
                    listener.onLogout(code);
                    break;
                case DsonSmartHostActionConstant.ACTION_CONTROL_SCENE:
                    try {
//                        DsonSmartScene scene = JSON.parseObject(data1, DsonSmartScene.class);
                        listener.onControlScene(code, data1);
                    } catch (Exception e) {
                        listener.onControlScene(DsonbaseContant.RESULT_FAIL, null);
                    }
                    break;
                case DsonSmartHostActionConstant.ACTION_GET_SCENES:
                    if (code == DsonbaseContant.RESULT_SUCCESS) {
                        try {
                            DsonSmartScenes scenes = JSON.parseObject(data1, DsonSmartScenes.class);
                            listener.onGetSceneList(code, scenes);
//                            aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_GET_ALL_DEVICE_TYPE,null,null,this);
                        } catch (Exception e) {
                            listener.onGetSceneList(DsonbaseContant.RESULT_FAIL, null);
                        }
                    } else {
                        listener.onGetSceneList(code, null);
                    }
                    break;
                case DsonSmartHostActionConstant.ACTION_GET_ROOMS:
                    if (code == DsonbaseContant.RESULT_SUCCESS) {
                        try {
                            DsonSmartRooms rooms = JSON.parseObject(data1, DsonSmartRooms.class);
                            listener.onGetRoomList(code, rooms);
//                            aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_GET_SCENES,null,null,this);
                        } catch (Exception e) {
                            listener.onGetRoomList(DsonbaseContant.RESULT_FAIL, null);
                        }
                    } else {
                        listener.onGetRoomList(code, null);
                    }
                    break;
                case DsonSmartHostActionConstant.ACTION_CONTROL_DEVICE:
                    if (code == DsonbaseContant.RESULT_SUCCESS) {
                        try {
                            DsonSmartCtrlCmd cmd = JSON.parseObject(data1, DsonSmartCtrlCmd.class);
                            listener.onControlDevice(code, cmd);
//                            aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_GET_SCENES,null,null,this);
                        } catch (Exception e) {
                            listener.onControlDevice(DsonbaseContant.RESULT_FAIL, null);
                        }
                    } else {
                        listener.onControlDevice(code, null);
                    }
                    break;

                case DsonSmartHostActionConstant.ACTION_GET_ALL_DEVICE_TYPE:
                    if (code == DsonbaseContant.RESULT_SUCCESS){
                        try {
                            List<String> types = JSON.parseObject(data1,List.class);
                            listener.onGetAllDeviceType(code,types);
                        }catch (Exception e){
                            listener.onGetAllDeviceType(code,null);
                        }
                    }else{
                        listener.onGetAllDeviceType(code,null );
                    }
                    break;
            }
        }
    };

    public boolean isInit(){
        return isInit;
    }

    void show(final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ServiceHelper(Context context){
        this.mContext = context;
        handler = new Handler(mContext.getMainLooper());
    }

    public synchronized static ServiceHelper getInstance(Context context){
        if(instance == null){
            instance = new ServiceHelper(context);
        }
        return instance;
    }

    public void setOnRemoteListener(OnRemoteCallbackListener listener){
        this.listener = listener;
    }

    /*
    初始化
     */
    public boolean init(){
        Log.e("andy","init");
        final Intent intent1 = new Intent();
        intent1.setAction(ACTION);
        intent = new Intent(AndroidUtils.createExplicitFromImplicitIntent(mContext, intent1));
        if(intent == null){
            return false;
        }
        if(!AndroidUtils.isServiceRunning(mContext,SERVICE_NAME)){
            Log.e("andy","init start service");
            mContext.startService(intent);
        }

        if(aidlCtrl != null) {
//            show("bind");
            return true;
        }
        Handler handler = new Handler(mContext.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myBindService();
            }
        },2000);

        return true;
    }


    /*
    绑定服务
     */
    void myBindService(){
        if(isInit()) return;
        mContext.bindService(intent, serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e("andy","-----------onServiceConnected");
                isInit = true;

                try {
                    aidlCtrl = IAidlCtrl.Stub.asInterface(service);
                    Log.e("andy","get binder ok");
                   // addStr("初始化host...");
                    aidlCtrl.setCallback("10001",aidlCallback);
                    Log.e("andy","set callback");
                    aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_INIT_HOST, null, null, aidlCallback);
                    Log.e("andy","do action ,init..");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e("andy","-----------------onServiceDisconnected");
                aidlCtrl = null;

                isInit = false;

                if(SmartHomeActivity.getInstance() != null) {
                    SmartHomeActivity.getInstance().finish();
                }

                if(LoginSmartHomeActivity.getInstance() != null) {
                    LoginSmartHomeActivity.getInstance().finish();
                }

            }
        }, Context.BIND_AUTO_CREATE);
    }


    public void login(String user,String pwd){
        try {
            if(aidlCtrl != null)
            aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_LOGIN, user, pwd, aidlCallback);
        }catch (RemoteException e){

        }
    }

    public void logout(){
        try {
            if(aidlCtrl != null)
            aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_LOGOUT, null, null, aidlCallback);
        }catch (RemoteException e){

        }

    }

    public void controlDevice(DsonSmartCtrlCmd cmd){
        try {
            if(aidlCtrl != null)
            aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_CONTROL_DEVICE, JSON.toJSONString(cmd), null, aidlCallback);
        }catch (RemoteException ex){

        }
    }

    public void controlScene(DsonSmartScene scene){
        try {
            if(aidlCtrl != null)
            aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_CONTROL_SCENE, JSON.toJSONString(scene), null, aidlCallback);
        }catch (RemoteException ex){

        }
    }

    public DsonSmartHostInfo getHostInfo(){
        try {
            String hostInfo = aidlCtrl.doAction(DsonSmartHostActionConstant.ACTION_GET_HOST_INFORMATION, null, null, null);

            DsonSmartHostInfo info = JSON.parseObject(hostInfo, DsonSmartHostInfo.class);
            if (info != null) {
                DsonConstants.getInstance().setHostInfo(info);
            }

            return info;

        }catch (RemoteException ex){

        }

        return null;
    }







}
