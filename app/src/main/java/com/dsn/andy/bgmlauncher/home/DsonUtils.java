package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by dell on 2018/4/11.
 */

public class DsonUtils {



    public static void show(final Context context, final String str){
        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
            }
        });
    }



}
