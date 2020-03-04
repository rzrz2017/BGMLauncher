package com.dsn.andy.bgmlauncher.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.AuxinActivity;
import com.dsn.andy.bgmlauncher.CommonSettingActivity;
import com.dsn.andy.bgmlauncher.DLNAActivity;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.SpeechHelpActivity;
import com.dsn.andy.bgmlauncher.bean.Tool;
import com.dsn.andy.bgmlauncher.com485.SmartHome485Activity;
import com.dsn.andy.bgmlauncher.util.Utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/16.
 */

public class ToolAdapter  extends
        RecyclerView.Adapter<ToolAdapter.ViewHolder> {

    Context context;
    ArrayList<Tool> tools;

    Handler handler ;

    public ToolAdapter(Context context, ArrayList<Tool> tools) {
       this.context = context;
        this.tools = tools;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_tool,
                viewGroup, false);
        ToolAdapter.ViewHolder viewHolder = new ToolAdapter.ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.img);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.txt);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
       Tool tool = tools.get(i);
        viewHolder.mImg.setBackgroundResource(tool.imgResId);
        viewHolder.mTxt.setText(tool.name);

        viewHolder.mImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchTool(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tools.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView mImg;
        TextView mTxt;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    private void switchTool(int i)
    {
        switch (i+1)
        {
//            case 0://智能家居
//                Intent smartHomeIntent = new Intent(context, SmartHome485Activity.class);
//                context.startActivity(smartHomeIntent);
//                break;
            case 1://网络
                Intent wifiSettingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(wifiSettingsIntent);
                break;
            case 2://通用
                Intent commonIntent = new Intent(context, CommonSettingActivity.class);
                context.startActivity(commonIntent);
                break;
            case 3://清理缓存
                doClear();
                break;
            case 4://显示
                Intent displaySettingsIntent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                context.startActivity(displaySettingsIntent);
                break;
            case 5://语音助手
               startVoiceAssistant();
                break;
            case 6://语音操作
                Intent speechIntent = new Intent(context,SpeechHelpActivity.class);
                context.startActivity(speechIntent);
                break;
            case 7://DLNA
                Intent dlnaIntent = new Intent(context,DLNAActivity.class);
                context.startActivity(dlnaIntent);
                break;
            case 8://功放通道
                Intent auxinIntent = new Intent(context,AuxinActivity.class);
                context.startActivity(auxinIntent);
                break;
        }
    }

    void doClear(){
        new Thread(){
            public void run(){
                clearSDMemory();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"缓存已经清理",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }

    void clearSDMemory(){
        File dir = new File("/mnt/sdcard/dsn");
        if(Utils.deleteDir(dir)){
        }

        File sdcard = new File("/mnt/sdcard");
        File[] files = sdcard.listFiles();
        for(File f:files){
            if(f.getName().startsWith("CAE") || f.getName().equals("DragonFire") ){
                Utils.deleteDir(f);
            }
        }
    }

    public void startVoiceAssistant(){
        try {
            Intent intent =new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.szhklt.VoiceAssistant", "com.szhklt.activity.MainActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(context, "请稍候.......", Toast.LENGTH_SHORT).show();
        }
    }
}
