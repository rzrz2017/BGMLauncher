package com.dsn.andy.bgmlauncher.receiver;

/**
 * Created by dell on 2018/1/16.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

public class HomeWatcherReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "received in MyBroadcastReceiver",Toast.LENGTH_SHORT).show();
//    	Intent goHomeintent = new Intent("android.intent.action.MAIN");
//    	//goHomeintent.addCategory("android.intent.category.LAUNCHER");
//    	goHomeintent.addCategory(Intent.CATEGORY_HOME);
//    	goHomeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    	context.startActivity(goHomeintent);
        Intent goHomeintent = new Intent();
        goHomeintent.setClassName("com.dsn.andy.bgmlauncher", "com.dsn.andy.bgmlauncher.MainActivity");
        goHomeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        // Verify it resolves
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities =packageManager.queryIntentActivities(goHomeintent, 0);
        boolean isIntentSafe = activities.size() > 0;
        // Start an activity if it's safe
        if (isIntentSafe) {
            context.startActivity(goHomeintent);
        }
    }
}