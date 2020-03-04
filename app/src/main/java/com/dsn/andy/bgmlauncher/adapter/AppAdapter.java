package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.AppInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AppAdapter extends
        RecyclerView.Adapter<AppAdapter.ViewHolder> {

    Context context;
    ArrayList<AppInfo> apps;

    public AppAdapter(Context context, ArrayList<AppInfo> tools) {
        this.context = context;
        this.apps = tools;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_app,
                viewGroup, false);
        AppAdapter.ViewHolder viewHolder = new AppAdapter.ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.img);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.txt);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final AppInfo app = apps.get(i);
        viewHolder.mImg.setBackground(app.getDrawable());
        viewHolder.mTxt.setText(app.getAppName());

        viewHolder.mImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startApp(app);
            }
        });
    }

    void startApp(AppInfo app){
        PackageManager packageManager = context.getPackageManager();
        Intent intent =packageManager.getLaunchIntentForPackage(app.getPackageName());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView mImg;
        TextView mTxt;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }




}
