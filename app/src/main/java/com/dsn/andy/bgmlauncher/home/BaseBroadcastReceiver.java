package com.dsn.andy.bgmlauncher.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

/**
 * Created by dell on 2018/5/4.
 */

public abstract class BaseBroadcastReceiver extends BroadcastReceiver {

    public static final String FIELD_RESULT_CODE = "result_code";

    String[] actions;
    Context mContext;
    public BaseBroadcastReceiver(Context context,String[] actions){
        this.mContext = context;
        this.actions = actions;
    }

    public BaseBroadcastReceiver register(){
        IntentFilter filter = new IntentFilter();
        for (String action:actions){
            filter.addAction(action);
        }
        mContext.registerReceiver(this,filter);
        return this;
    }

    public void unregister(){
        mContext.unregisterReceiver(this);
    }





}
