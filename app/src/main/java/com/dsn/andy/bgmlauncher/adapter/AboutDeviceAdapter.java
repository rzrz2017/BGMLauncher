package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dsn.andy.bgmlauncher.HelpActivity;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.AboutDevices;
import com.dsn.andy.bgmlauncher.view.CenterTextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/2.
 */


public class AboutDeviceAdapter extends  RecyclerView.Adapter<AboutDeviceAdapter.ViewHolder>{

    Context context;
    ArrayList<AboutDevices>list;
    Handler handler ;

    public  AboutDeviceAdapter(Context context,ArrayList<AboutDevices>list){
        this.context = context;
        this.list = list;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public AboutDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_aboutdevice,
                viewGroup, false);
        AboutDeviceAdapter.ViewHolder viewHolder = new AboutDeviceAdapter.ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.img);
        viewHolder.mTxt = (CenterTextView) view.findViewById(R.id.txt);


        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        /*Tool tool = tools.get(i);*/
        AboutDevices dev=list.get(position);

        viewHolder.mImg.setBackgroundResource(dev.imgResId);
        viewHolder.mTxt.setText(dev.name);

        viewHolder.mImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchTool(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView mImg;
        CenterTextView mTxt;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    private void switchTool(int i)
    {
        switch (i)
        {
            case 0://设备名称
                break;
            case 1://设备编号
              break;
            case 2://常见问题
                    Intent problemIntent1 = new Intent();
                    //Intent传递参数
                    problemIntent1.putExtra("showType", "1");
                    problemIntent1.setClass(context, HelpActivity.class);
                    context.startActivity(problemIntent1);

                break;
            case 3://用户协议和版权协议
                Intent problemIntent2= new Intent();
                //Intent传递参数
                problemIntent2.putExtra("showType", "2");
                problemIntent2.setClass(context,  HelpActivity.class);
                context.startActivity(problemIntent2);
                break;
            case 4://使用说明
                Intent problemIntent3= new Intent();
                //Intent传递参数
                problemIntent3.putExtra("showType", "3");
                problemIntent3.setClass(context,  HelpActivity.class);
                context.startActivity(problemIntent3);
                break;
            case 5://二维码下载
                Intent problemIntent4= new Intent();
                //Intent传递参数
                problemIntent4.putExtra("showType", "4");
                problemIntent4.setClass(context,  HelpActivity.class);
                context.startActivity(problemIntent4);
                break;
        }
    }

}