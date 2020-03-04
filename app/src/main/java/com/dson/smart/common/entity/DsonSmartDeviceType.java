package com.dson.smart.common.entity;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartDeviceType {
    //一般控制设备
    public static final String[] DEVICE_NAMES = {
        "灯光","窗帘","插座","SPEAKER","空调","空气净化器","调光灯","推窗器",
            "加热器","REPEATER","电视","机顶盒","学习型红外","门","床","桌","椅",
            "热水器","摄像头","投影仪","风扇","情景开关","遥控器","红外遥控器",
            "门锁","联动一路","联动二路","联动三路","指纹","控制器"
    };
    public static final int ALL = 0;
    public static final int DEVICE_TYPE_LAMP = 1;
    public static final int DEVICE_TYPE_CURTAINS = 2;
    public static final int DEVICE_TYPE_SOCKET = 3;
    public static final int DEVICE_TYPE_SPEAKER = 4;
    public static final int DEVICE_TYPE_AIRCONDITION = 5;
    public static final int DEVICE_TYPE_AIRPURIFIER = 6;
    public static final int DEVICE_TYPE_DIMMER = 7;
    public static final int DEVICE_TYPE_WINDOW_CONTROLER = 8;
    public static final int DEVICE_TYPE_HEATER = 9;
    public static final int DEVICE_TYPE_IR_REPEATER = 10;
    public static final int DEVICE_TYPE_TV = 11;
    public static final int DEVICE_TYPE_STB = 12;
    public static final int DEVICE_TYPE_SELF_DEFINE_IR = 13;
    public static final int DEVICE_TYPE_DOOR = 14;
    public static final int DEVICE_TYPE_BED = 15;
    public static final int DEVICE_TYPE_DESK = 16;
    public static final int DEVICE_TYPE_CHAIR = 17;
    public static final int DEVICE_TYPE_WATER_HEATER = 18;
    public static final int DEVICE_TYPE_CAMERA = 19;
    public static final int DEVICE_TYPE_PROJECT = 20;
    public static final int DEVICE_TYPE_PULL_FAN = 21;
    //add by andy
    public static final int DEVICE_TYPE_SCENE_SWITCH = 22;//情景开关
    public static final int DEVICE_TYPE_REMOTE_CONTROL = 23;//遥控
    public static final int DEVICE_TYPE_IR = 24;//红外转发器
    public static final int DEVICE_TYPE_LOCK = 25;//门锁
    public static final int DEVICE_TYPE_LINK_SWITCH_GANG1 = 26;//联动一路开关
    public static final int DEVICE_TYPE_LINK_SWITCH_GANG2 = 27;//联动二路开关
    public static final int DEVICE_TYPE_LINK_SWITCH_GANG3 = 28;//联动三路开关
    public static final int DEVICE_TYPE_FINGER_PRINT = 29;//指纹
    public static final int DEVICE_TYPE_CONTROL = 30;//控制器  例如机械手等


    public static final int DEVICE_MIN = ALL+1;//控制设备类型最大值
    public static final int DEVICE_MAX = 300;//控制设备类型最大值
   //以下为传感器类型
    public static final String[] SENSOR_NAMES = {
        "温度","SOS","人体红外","电磁窗帘","可燃气体","湿度","烟感",
           "门窗传感器","水浸探测","风光雨",
   };

    public static final int DEVICE_TYPE_TEMPERATURE_SENSOR = 301;

    public static final int DEVICE_TYPE_SOS_SENSOR = 302;

    public static final int DEVICE_TYPE_INFRARED_SENSOR = 303;

    public static final int DEVICE_TYPE_MAGNETIC_WINDOW = 304;

    public static final int DEVICE_TYPE_FLAMMABLE_GAS = 305;

    public static final int DEVICE_TYPE_HUMIDITY_SENSOR = 306;

    public static final int DEVICE_TYPE_SMOKE_SENSOR = 307;//烟雾报警

    public static final int DEVICE_TYPE_DOOR_SENSOR = 308;//门窗传感器

    public static final int DEVICE_TYPE_WATER_LOG_SENSOR = 309;//水浸

    public static final int DEVICE_TYPE_WIND_RAIN_SENSOR = 310;//风光雨

    public static final int DEVICE_SENSOR_MAX = 10000;

    public static final int DEVICE_SUB_TYPE_IR = 10001;
    public static final int DEVICE_SUB_TYPE_CONTROL_BOX = 10002;

    int type;

    public DsonSmartDeviceType() {
    }

    public DsonSmartDeviceType(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public static String getName(int type){
        String result = "";
        if(type == ALL) {
            result = "全部";
        }if(type >=DEVICE_MIN && type <= DEVICE_MAX){
            result = DEVICE_NAMES[type-DEVICE_MIN];
        }else if(type > DEVICE_MAX && type <= DEVICE_SENSOR_MAX){
            result = SENSOR_NAMES[type-DEVICE_MAX-1];
        }
        return result;
    }

    public static boolean isSubType(int type) {
        return type <= 100001 && type >= DEVICE_SENSOR_MAX;
    }

    public static boolean isSensorType(int type) {
        return type < DEVICE_SENSOR_MAX && type > DEVICE_MAX;
    }

    public static boolean isOnOffDevice(int type){
        if(type == DEVICE_TYPE_LAMP
            || type == DEVICE_TYPE_SOCKET
             || type == DEVICE_TYPE_LINK_SWITCH_GANG1
              ||   type == DEVICE_TYPE_LINK_SWITCH_GANG2
                || type == DEVICE_TYPE_LINK_SWITCH_GANG3
                ){
            return true;
        }
        return false;
    }

    public static boolean isThreeStatusDevice(int type){
        if(type == DEVICE_TYPE_CURTAINS
                || type == DEVICE_TYPE_WINDOW_CONTROLER
                || type == DEVICE_TYPE_CONTROL
                ){
            return true;
        }
        return false;
    }

}

