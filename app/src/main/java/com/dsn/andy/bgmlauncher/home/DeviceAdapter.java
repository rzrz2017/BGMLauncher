package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceOrder;

import java.util.List;

/**
 * Created by dell on 2018/3/29.
 */

public class DeviceAdapter extends BaseAdapter {

    Context context;
    List<DsonSmartDevice> devices;

    public DeviceAdapter(Context context,List<DsonSmartDevice> devices){
        this.context = context;
        this.devices = devices;
    }

    public void setData(List<DsonSmartDevice> devices){
        this.devices = devices;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public DsonSmartDevice getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_device,null);
            holder = new ViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.txt = (TextView)convertView.findViewById(R.id.txt);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        DsonSmartDevice device = getItem(position);
        holder.txt.setText(device.getDeviceName());
        holder.img.setBackgroundResource(ResUtils.getDeviceImg(device));

        DsonSmartCtrlCmd cmd = device.getDsonSmartCtrlCmd();
        if(cmd != null) {
            String order = device.getDsonSmartCtrlCmd().getOrder();
            if (order != null) {
                if (!order.contains(DsonSmartDeviceOrder.CLOSE)
                        || !("0").equals(cmd.getValue1())
                        ) {
                    holder.txt.setTextColor(context.getResources().getColor(R.color.default_color));
                } else {
                    holder.txt.setTextColor(Color.GRAY);
                }
            }
        }else{
            holder.txt.setTextColor(Color.GRAY);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView img;
         TextView txt;
    }
}
