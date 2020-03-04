package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dson.smart.common.entity.DsonSmartDeviceType;

import java.util.List;

/**
 * Created by dell on 2018/3/31.
 */

public class TypeAdapter extends BaseAdapter {

    Context mContext;
    List<DsonSmartDeviceType> types;


    private int curPosition;

    public TypeAdapter(Context context,List<DsonSmartDeviceType> types){
        this.mContext = context;
        this.types = types;
    }

    public void setCurPosition(int position){
        curPosition = position;
        notifyDataSetChanged();
    }

    public DsonSmartDeviceType getCurrent(){
        return types.get(curPosition);
    }

    public int getCurPosition(){
        return curPosition;
    }

    public int getItemPosition(DsonSmartDeviceType t){
        for(int i=0;i<types.size();i++){
            DsonSmartDeviceType tt = types.get(i);
            if(tt.getType() == t.getType()){
                return i;
            }
        }
        return -1;
    }

    public void setData(List<DsonSmartDeviceType> data){
        this.types = data;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public DsonSmartDeviceType getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_device_type,null);
            holder = new ViewHolder();
            holder.txt = (TextView)convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        DsonSmartDeviceType type = getItem(position);
        holder.txt.setText(DsonSmartDeviceType.getName(type.getType()));

        if(position == curPosition){
            holder.txt.setBackgroundResource(R.drawable.bg_type_select);
        }else{
            holder.txt.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }


        return convertView;
    }

    class ViewHolder{
        TextView txt;
    }
}
