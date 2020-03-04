package com.dsn.andy.bgmlauncher.home;

import com.dsn.andy.bgmlauncher.R;
import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceOrder;
import com.dson.smart.common.entity.DsonSmartDeviceType;

/**
 * Created by dell on 2018/5/3.
 */

public class ResUtils {

    public static int getDeviceImg(DsonSmartDevice device){
        int res = R.drawable.ic_device_light_on;
        int type = Integer.parseInt(device.getDeviceType());
        DsonSmartCtrlCmd cmd = device.getDsonSmartCtrlCmd();
        String order = cmd.getOrder();
        switch(type)
        {
            case DsonSmartDeviceType.DEVICE_TYPE_LAMP:
                if(DsonSmartDeviceOrder.OPEN.equals(order)){
                    res = R.drawable.ic_dson_device_light_on;
                }else if(DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_light_off;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_CURTAINS:
                if(DsonSmartDeviceOrder.OPEN.equals(order)){
                    res = R.drawable.ic_dson_device_curtain_on;
                }else if (DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_curtain_off;
                }else if(DsonSmartDeviceOrder.STOP.equals(order)){
                    res = R.drawable.ic_dson_device_curtain_on;
//                    cmd.getValue1();
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_SOCKET:
                if(DsonSmartDeviceOrder.OPEN.equals(order)){
                    res = R.drawable.ic_dson_device_stoke_on;
                }else if(DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_stoke_off;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_AIRCONDITION:
                res = R.drawable.ic_dson_device_ac_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_DIMMER:
                if(DsonSmartDeviceOrder.OPEN.equals(order)){
                    res = R.drawable.ic_dson_device_dim_on;
                }else{
                    res = R.drawable.ic_dson_device_dim_off;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_WINDOW_CONTROLER:
                if(DsonSmartDeviceOrder.OPEN.equals(order)){
                    res = R.drawable.ic_dson_device_push_open;
                }else if(DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_push_close;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_TV:
                res = R.drawable.ic_dson_device_tv_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_STB:
                res = R.drawable.ic_dson_device_stb_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_SELF_DEFINE_IR:
                res = R.drawable.ic_dson_device_ir_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_CAMERA:
                res = R.drawable.ic_dson_device_camera_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_SCENE_SWITCH:
                res = R.drawable.ic_dson_device_scene_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_IR:
                res = R.drawable.ic_dson_device_ir_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_LOCK:
                if(DsonSmartDeviceOrder.OPEN.equals(order)) {
                    res = R.drawable.ic_dson_device_lock_on;
                }else if(DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_lock_off;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_LINK_SWITCH_GANG1:
                if(DsonSmartDeviceOrder.OPEN.equals(order)) {
                    res = R.drawable.ic_dson_device_link_1_on;
                }else if(DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_link_1_off;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_LINK_SWITCH_GANG2:
                if(DsonSmartDeviceOrder.OPEN.equals(order)) {
                    res = R.drawable.ic_dson_device_link_2_on;
                }else if(DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_link_2_off;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_LINK_SWITCH_GANG3:
                if(DsonSmartDeviceOrder.OPEN.equals(order)) {
                    res = R.drawable.ic_dson_device_link_3_on;
                }else if(DsonSmartDeviceOrder.CLOSE.equals(order)){
                    res = R.drawable.ic_dson_device_link_3_off;
                }
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_FINGER_PRINT:
                res = R.drawable.ic_dson_device_finger_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_CONTROL:
                res = R.drawable.ic_dson_device_control_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_TEMPERATURE_SENSOR:
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_SOS_SENSOR:
                res = R.drawable.ic_dson_device_help_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_INFRARED_SENSOR:
                res = R.drawable.ic_dson_device_infrared_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_MAGNETIC_WINDOW:
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_FLAMMABLE_GAS:
                res = R.drawable.ic_dson_device_gas_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_HUMIDITY_SENSOR:
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_SMOKE_SENSOR:
                res = R.drawable.ic_dson_device_smoke_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_DOOR_SENSOR:
                res = R.drawable.ic_dson_device_door_sensor_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_WATER_LOG_SENSOR:
                res = R.drawable.ic_dson_device_water_on;
                break;
            case DsonSmartDeviceType.DEVICE_TYPE_WIND_RAIN_SENSOR:
                res = R.drawable.ic_dson_device_wind_rain_on;
                break;
        }
        return res;
    }






}
