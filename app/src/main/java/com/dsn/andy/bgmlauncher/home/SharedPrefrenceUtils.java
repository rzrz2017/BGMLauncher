package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dell on 2018/4/10.
 */

public class SharedPrefrenceUtils {

    private static final String KEY_USERNAME = "dson_user";
    private static final String KEY_PASSWORD = "dson_pwd";

    public static final String KEY_SMARTHOME = "smarthome";


    public static void writeSp(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static String readSp(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static void setUsername(Context context, String username) {
        writeSp(context, KEY_USERNAME, username);
    }

    public static void setPassword(Context context, String password) {
        writeSp(context, KEY_PASSWORD, password);
    }

    public static String getUsername(Context context) {
        return readSp(context, KEY_USERNAME);
    }

    public static String getPassword(Context context) {
        return readSp(context, KEY_PASSWORD);
    }

    public static boolean isSupportSmarthome(Context context){
        String cb = SharedPrefrenceUtils.readSp(context,SharedPrefrenceUtils.KEY_SMARTHOME);
        if(cb == null || cb.equals("0")){
            return  false;
        }else{
            return true;
        }
    }

    public static void setSupportSmarthome(Context context,boolean isSupport){
        if(isSupport)
        writeSp(context,KEY_SMARTHOME,"1");
        else
            writeSp(context,KEY_SMARTHOME,"0");
    }
}