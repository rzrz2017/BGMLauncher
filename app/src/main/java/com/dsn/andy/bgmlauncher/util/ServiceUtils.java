package com.dsn.andy.bgmlauncher.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.home.AndroidUtils;
import com.dsn.andy.bgmlauncher.home.ServiceHelper;
import com.dsn.andy.bgmlauncher.home.SmarthomeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/5/4.
 */

public class ServiceUtils {

    public static final String SMART_SDK_PACKAGE = "com.andy.dson.dsonsmart.open";
    public static final String SMART_SDK_ACTIVITY = "MainActivity";
    public static final String SMART_SDK_SERVICE = "CustomSmartService";


    public static void checkAndStartSmartSDK(Context context){
        Log.e("andy","check and start smart sdk");
        if(!Utils.isSupportSmartHome(context)) return;
        if(AndroidUtils.isInstallApp(context,AndroidUtils.SDK_PACKAGE_NAME) &&
                !ServiceHelper.getInstance(context).isInit()){
            SmarthomeHelper.getInstance(context.getApplicationContext()).init();
        }
    }



    public static void startApp(Context context,String packageName,String activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName(packageName, activity));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(context.getApplicationContext(), "请稍候！", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 判断服务是否开启
     *
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }


    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public static void startService(Context context){
        if(!isServiceRunning(context,"com.andy.dson.dsonsmart.open.CustomSmartService")){
            final Intent intent1 = new Intent();
            intent1.setAction("com.andy.dson.bind_service");
            Intent intent2 = new Intent(createExplicitFromImplicitIntent(context, intent1));
            context.startService(intent2);
        }
    }



}
