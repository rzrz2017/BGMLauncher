package com.dsn.andy.bgmlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by Administrator on 2018/3/6.
 */

public class AppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent == null) {
            return;
        }
        if(intent.getAction().equals("android.intent.msg.TEXT")){
            Log.d("LEO",intent.getStringExtra("xftext"));
        }
    }
}
