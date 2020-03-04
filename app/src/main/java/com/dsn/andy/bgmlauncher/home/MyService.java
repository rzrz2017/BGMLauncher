package com.dsn.andy.bgmlauncher.home;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by dell on 2018/5/15.
 * 智能家居插件APP绑定此service，用来检测app挂掉
 */

public class MyService extends Service {

    class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
}
