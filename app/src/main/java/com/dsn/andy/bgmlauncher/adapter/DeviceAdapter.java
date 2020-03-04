package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Device;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class DeviceAdapter extends BaseAdapter {

    Context context;
    ArrayList<Device> list;

    public DeviceAdapter(Context context, ArrayList<Device> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Device getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_album2,null);
            vh = new ViewHolder();
            vh.img = (ImageView)convertView.findViewById(R.id.img);
            vh.name = (TextView)convertView.findViewById(R.id.txt);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        Device device = getItem(position);
        if(device.type == 1){
            vh.img.setBackgroundResource(R.drawable.ic_device_switch);
        }else if(device.type == 2){
            vh.img.setBackgroundResource(R.drawable.ic_device_curtain);
        }else if(device.type == 3){
            vh.img.setBackgroundResource(R.drawable.ic_device_scene);
        }else if(device.type == 4){
            vh.img.setBackgroundResource(R.drawable.ic_device_tv);
        }

        vh.name.setText(device.name);



        return convertView;
    }

    class ViewHolder{
        TextView name;
         ImageView img;
    }


}
