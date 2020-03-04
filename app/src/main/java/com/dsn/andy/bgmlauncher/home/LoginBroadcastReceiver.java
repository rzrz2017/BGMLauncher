package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.content.Intent;

/**
 * Created by dell on 2018/5/4.
 */

public class LoginBroadcastReceiver extends BaseBroadcastReceiver {
    public static final String ACTION_INIT_RESULT = "action_init_result";
    public static final String ACTION_GET_HOSTINFO = "action_get_hostinfo";
    public static final String ACTION_LOGIN_RESULT = "action_login_result";


    private static final String[] LOGIN_ACTION = {
            ACTION_INIT_RESULT,
            ACTION_GET_HOSTINFO,
            ACTION_LOGIN_RESULT
    };

    OnLoginListener listener;


    public LoginBroadcastReceiver(Context context) {
        super(context,LOGIN_ACTION);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(listener == null) return;
        String action = intent.getAction();
        if(ACTION_INIT_RESULT.equals(action)){
            int code = intent.getIntExtra(BaseBroadcastReceiver.FIELD_RESULT_CODE,-1);
            listener.onInit(code);
        }else if(ACTION_GET_HOSTINFO.equals(action)){
            listener.onGetHostInfo();
        }else if(ACTION_LOGIN_RESULT.equals(action)){
            int code = intent.getIntExtra(BaseBroadcastReceiver.FIELD_RESULT_CODE,-1);
            listener.onLogin(code);
        }
    }

    public void setOnLiginListener(OnLoginListener listener){
        this.listener = listener;
    }

    public interface OnLoginListener{
        void onInit(int code);
        void onGetHostInfo();
        void onLogin(int code);
    }
}
