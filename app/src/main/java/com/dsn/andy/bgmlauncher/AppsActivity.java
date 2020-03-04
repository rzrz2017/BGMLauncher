package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dsn.andy.bgmlauncher.adapter.AppAdapter;
import com.dsn.andy.bgmlauncher.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dell on 2017/9/22.
 */

public class AppsActivity extends Activity {

    RecyclerView recyclerView;

    ArrayList<AppInfo> appInfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        getAppInfos();

        AppAdapter adpater = new AppAdapter(this, appInfos);
        recyclerView.setAdapter(adpater);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.app_space);
        recyclerView.addItemDecoration(new AppsActivity.SpaceItemDecoration(spacingInPixels));
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


            outRect.top = space;

        }
    }


    public ArrayList<AppInfo> getAppInfos() {
        PackageManager pm = getApplication().getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);


        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);


        appInfos = new ArrayList<AppInfo>();
        /* 获取应用程序的名称，不是包名，而是清单文件中的labelname
            String str_name = packageInfo.applicationInfo.loadLabel(pm).toString();
            appInfo.setAppName(str_name);
         */
        for (ResolveInfo info : resolveInfos) {
            String appName = info.activityInfo.loadLabel(pm).toString();
            String packageName = info.activityInfo.packageName;
            Log.e("andy", packageName);
            Drawable drawable = info.activityInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName, drawable);
            if (packageName.equals(this.getPackageName())) continue;
            if (packageName.contains("contact") || packageName.contains("record")) continue;
            appInfos.add(appInfo);


        }
        return appInfos;
    }
}
