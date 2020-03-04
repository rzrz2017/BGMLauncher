package com.dsn.andy.bgmlauncher.home;

/**
 * Created by dell on 2018/4/8.
 */

public class DsonCommand {

    public static final int CMD_ON = 1;
    public static final int CMD_OFF = 2;
    public static final int CMD_OPEN = 3;
    public static final int CMD_CLOSE = 4;
    public static final int CMD_PAUSE = 5;

    public static final int CMD_AC_POWER_ON = 6;
    public static final int CMD_AC_POWER_OFF = 7;
    public static final int CMD_AC_MODE_AUTO = 8;
    public static final int CMD_AC_MODE_COOL = 9;
    public static final int CMD_AC_MODE_HOT = 10;
    public static final int CMD_AC_MODE_DRY = 11;
    public static final int CMD_AC_MODE_FAN = 12;

    public static final int CMD_AC_WIND_SPEED = 13;
    public static final int CMD_AC_WIND_DIR = 14;
    public static final int CMD_AC_TEMP_SET = 15;


    public static final int CMD_TV = 16;



    public static final int DATA_MODE_AUTO = 1;
    public static final int DATA_MODE_COOL = 2;
    public static final int DATA_MODE_DRY = 3;
    public static final int DATA_MODE_FAN = 4;
    public static final int DATA_MODE_HOT = 5;

    public static final int DATA_WIND_SPEED_AUTO = 1;
    public static final int DATA_WIND_SPEED_LOW = 2;
    public static final int DATA_WIND_SPEED_MIDDLE = 3;
    public static final int DATA_WIND_SPEED_HIGH = 4;

    public static final int DATA_WIND_DIR_LEFT_RIGHT = 1;
    public static final int DATA_WIND_DIR_UP_DOWN = 2;


    public static final int DATA_TV_POWER = 1;
    public static final int DATA_TV_CHANNEL_PLUS = 2;
    public static final int DATA_TV_CHANNEL_MINUS = 3;
    public static final int DATA_TV_VOLUME_PLUS = 4;
    public static final int DATA_TV_VOLUME_MINUS = 5;
    public static final int DATA_TV_MUTE = 6;

    public static String getModeName(int mode){
        String name = null;
        switch (mode)
        {
            case DsonCommand.DATA_MODE_AUTO:
                name = "自动模式";
                break;
            case DsonCommand.DATA_MODE_COOL:
                name = "制冷模式";
                break;
            case DsonCommand.DATA_MODE_DRY:
                name = "干燥模式";
                break;
            case DsonCommand.DATA_MODE_FAN:
                name = "送风模式";
                break;
            case DsonCommand.DATA_MODE_HOT:
                name = "制热模式";
                break;
        }

        return name;
    }

    public static String getFanLevelName(int level){
        String name = null;
        switch (level)
        {
            case DATA_WIND_SPEED_AUTO:
                name = "自动风速";
                break;
            case DATA_WIND_SPEED_LOW:
                name = "低风";
                break;
            case DATA_WIND_SPEED_MIDDLE:
                name = "中风";
                break;
            case DATA_WIND_SPEED_HIGH:
                name = "高风";
                break;
        }

        return name;
    }

    public static String getFanDirName(int dir){
        String name = null;
        switch (dir)
        {
            case DATA_WIND_DIR_LEFT_RIGHT:
                name = "左右摆风";
                break;
            case DATA_WIND_DIR_UP_DOWN:
                name = "上下摆风";
                break;
        }

        return name;
    }


}
