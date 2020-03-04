package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.IOException;

/**
 * Created by dell on 2018/1/31.
 */

public class ShutdownTest extends Activity {

    public void reboot(){
        new Thread(){
            public void run(){
                String cmd = "su -c reboot";

                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (IOException e) {


                }
            }
        }.start();


    }

    public void reboot1(){
        Intent intent2 = new Intent(Intent.ACTION_REBOOT);
        intent2.putExtra("nowait", 1);
        intent2.putExtra("interval", 1);
        intent2.putExtra("window", 0);
        sendBroadcast(intent2);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shutdown);

        findViewById(R.id.reboot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reboot();
            }
        });
    }
}
