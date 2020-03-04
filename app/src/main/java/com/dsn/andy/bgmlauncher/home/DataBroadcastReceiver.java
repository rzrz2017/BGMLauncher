package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.content.Intent;

/**
 * Created by dell on 2018/5/4.
 */

public class DataBroadcastReceiver extends BaseBroadcastReceiver {
    public static final String ACTION_GET_ALL_DEVICE = "action_get_all_device";
    public static final String ACTION_GET_ALL_ROOM = "action_get_all_room";
    public static final String ACTION_GET_ALL_SCENE = "action_get_all_scene";
    public static final String ACTION_GET_ALL_DEVICE_TYPE = "action_get_all_device_type";
    public static final String ACTION_DEVICE_UPDATE = "action_device_update";

    private static final String[] DATA_ACTIONS = {
         ACTION_GET_ALL_DEVICE,
            ACTION_GET_ALL_ROOM,
            ACTION_GET_ALL_SCENE,
            ACTION_GET_ALL_DEVICE_TYPE,
            ACTION_DEVICE_UPDATE
    };

    OnDataChangeListener listener;

    public DataBroadcastReceiver(Context context) {
        super(context, DATA_ACTIONS);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(listener == null) return;
        String action = intent.getAction();
        if(ACTION_GET_ALL_DEVICE.equals(action)){
            listener.onDeviceChange();
        }else if(ACTION_GET_ALL_ROOM.equals(action)){
            listener.onRoomChange();
        }else if(ACTION_GET_ALL_SCENE.equals(action)){
            listener.onSceneChange();;
        }else if(ACTION_GET_ALL_DEVICE_TYPE.equals(action)){
            listener.onDeviceTypeChange();
        }else if(ACTION_DEVICE_UPDATE.equals(action)){
            listener.onDeviceUpdate();
        }

    }

    public void setOnDataChangeListener(OnDataChangeListener listener){
        this.listener = listener;
    }


    public interface OnDataChangeListener{
        void onDeviceChange();
        void onRoomChange();
        void onSceneChange();
        void onDeviceTypeChange();
        void onDeviceUpdate();
    }


}
