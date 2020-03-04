package com.dsn.andy.bgmlauncher.com485;

import com.andy.music.COMDevice;
import com.dsn.andy.bgmlauncher.R;

/**
 * Created by dell on 2018/10/2.
 */

public class COMConstants {

    public static final int TYPE_LIGHT = 1;
    public static final int TYPE_CURTAIN = 2;
    public static final int TYPE_WINDOW = 3;
    public static final int TYPE_AC = 4;
    public static final int TYPE_TV = 5;
    public static final int TYPE_SCENE_SWITCH = 6;
    public static final int TYPE_CAMERA = 7;


    public static int getDeviceImgResID(COMDevice device){
        switch (device.getDeviceType())
        {
            case TYPE_LIGHT:
                return R.drawable.ic_device_light_on;
            case TYPE_CURTAIN:
                return R.drawable.com_device_curtain_open;
            case TYPE_WINDOW:
                return R.drawable.com_device_window_open;
            case TYPE_AC:
                return R.drawable.com_device_ac_on;
            case TYPE_TV:
                return R.drawable.com_device_tv_on;
            case TYPE_SCENE_SWITCH:
                return R.drawable.com_device_scene_switch_on;
            case TYPE_CAMERA:
                return R.drawable.com_device_camera_on;
            default:
                return R.drawable.smarthome485_add_device;
        }
    }

}
