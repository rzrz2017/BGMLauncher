package com.dsn.andy.bgmlauncher.com485;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.music.COMDevice;
import com.dsn.andy.bgmlauncher.R;

import java.util.List;

/**
 * Created by dell on 2018/10/2.
 */

public class COMDeviceAdapter extends BaseAdapter {

    private Context context;
    private List<COMDevice> devices;

    public COMDeviceAdapter(Context context, List<COMDevice> devices){
        this.context = context;
        this.devices = devices;
    }


    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public COMDevice getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        COMDeviceAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_device,null);
            holder = new COMDeviceAdapter.ViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.txt = (TextView)convertView.findViewById(R.id.txt);

            convertView.setTag(holder);
        }else{
            holder = (COMDeviceAdapter.ViewHolder)convertView.getTag();
        }
        COMDevice device = getItem(position);
        holder.txt.setText(device.getDeviceName());
        holder.img.setBackgroundResource(COMConstants.getDeviceImgResID(device));

        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView txt;
    }




}
