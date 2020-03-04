package com.dsn.andy.bgmlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

/**
 * Created by dell on 2018/1/26.
 */

public class AlarmRebootBroadcast extends BroadcastReceiver {

    public void reboot(){
        String cmd = "su -c reboot -p";

        try {
            Runtime.getRuntime().exec("su -c reboot -p");
        } catch (IOException e) {


        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
       // Log.d("hahahhaha",intent.getAction());
       // reboot();
        if (intent.getAction().equals("com.android.deskclock.ALARM_ALERT")){
           Log.d("LEO","com.android.deskclock.ALARM_ALERT");
        }
        //reboot();
    }
}
