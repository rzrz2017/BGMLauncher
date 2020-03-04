package com.dson.smart.common.entity;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartDeviceOrder {
//    public static final String ON = "on";
//    public static final String OFF = "off";

    public static final String OPEN = "open";
    public static final String CLOSE = "close";
    public static final String PLAY = "play";
    public static final String STOP = "stop";
    public static final String PAUSE = "pause";
    public static final String MUTE = "mute";
    public static final String IR_CONTROL = "ir control";
    /** @deprecated */
    public static final String MoveToLevel = "move to level";
    public static final String MOVE_TO_LEVEL = "move to level";
    public static final String SET = "set";
    public static final String NEXT = "next";
    public static final String AIRCONDITION_MODE_TYPE = "air_condition_mode_level";
    public static final String AIRCONDITION_WIND_RATE_TYPE = "air_condition_wind_rate_level";
    public static final String AIRCONDITION_WIND_DIRECTION_TYPE = "air_condition_wind_direction_level";
    public static final String TEMPERATURE = "temperature";
    public static final String TEMPERATURE_OFFSET = "temperature_set";
    public static final String AIRCONDITION_MODE_COOL = "cooling";
    public static final String AIRCONDITION_MODE_HEAT = "heating";
    public static final String AIRCONDITION_MODE_WIND = "winding";
    public static final String AIRCONDITION_MODE_DEHUMIDIFY = "dehumidify";
    public static final String AIRCONDITION_MODE_AUTO = "auto";
    public static final String AIRCONDITION_WIND_RATE_AUTO = "auto_wind";
    public static final String AIRCONDITION_WIND_RATE_HIGH = "high_wind";
    public static final String AIRCONDITION_WIND_RATE_MIDDLE = "middle_wind";
    public static final String AIRCONDITION_WIND_RATE_LOW = "low_wind";
    public static final String AIRCONDITION_WIND_RATE_MUTE = "mute_wind";
    public static final String AIRCONDITION_WIND_DIRECTION_LEFT_RIGHT = "left_right";
    public static final String AIRCONDITION_WIND_DIRECTION__UP_DOWN = "up_dowm";
    public static final String AIRCONDITION_WIND_DIRECTION_NO_DIRECTION = "no_direction";

    public static final String TV_CHANNEL_DIR = "channel_dir";
    public static final String TV_VOLUME_DIR = "volume_dir";
    public static final String TV_CHANNEL = "channel";
    public static final String TV_VOLUME = "volume";
    public static final String TV_OK = "ok";

    public DsonSmartDeviceOrder() {
    }

    public static String parseChinese(String jdorder) {
        if(jdorder == null) {
            return "";
        } else {
            byte var2 = -1;
            switch(jdorder.hashCode()) {
                case 3551:
                    if(jdorder.equals("on")) {
                        var2 = 0;
                    }
                    break;
                case 109935:
                    if(jdorder.equals("off")) {
                        var2 = 2;
                    }
                    break;
                case 113762:
                    if(jdorder.equals("set")) {
                        var2 = 7;
                    }
                    break;
                case 3363353:
                    if(jdorder.equals("mute")) {
                        var2 = 6;
                    }
                    break;
                case 3417674:
                    if(jdorder.equals("open")) {
                        var2 = 1;
                    }
                    break;
                case 3443508:
                    if(jdorder.equals("play")) {
                        var2 = 4;
                    }
                    break;
                case 94756344:
                    if(jdorder.equals("close")) {
                        var2 = 3;
                    }
                    break;
                case 106440182:
                    if(jdorder.equals("pause")) {
                        var2 = 5;
                    }
            }

            switch(var2) {
                case 0:
                case 1:
                    return "打开";
                case 2:
                case 3:
                    return "关闭";
                case 4:
                    return "播放";
                case 5:
                    return "停止播放";
                case 6:
                    return "静音";
                case 7:
                    return "设置";
                default:
                    return "操作";
            }
        }
    }
}

