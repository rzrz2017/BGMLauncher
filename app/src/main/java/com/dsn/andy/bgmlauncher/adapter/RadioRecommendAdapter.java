package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Radio;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/29.
 */

public class RadioRecommendAdapter extends BaseAdapter {

    Context context;
    ArrayList<Radio> radios;


    public RadioRecommendAdapter(Context context, ArrayList<Radio> radios){
        this.context = context;
        this.radios = radios;
    }

    @Override
    public int getCount() {
        return radios.size();
    }

    @Override
    public Radio getItem(int position) {
        return radios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_radio_recommend,null);
            vh = new ViewHolder();
            vh.img = (ImageView)convertView.findViewById(R.id.img);
            vh.nameTxt =(TextView) convertView.findViewById(R.id.nameTxt);
            vh.typeTxt =(TextView) convertView.findViewById(R.id.typeTxt);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        Radio radio = getItem(position);

        vh.typeTxt.setText(radio.type);
        vh.nameTxt.setText(radio.name);
        vh.img.setBackgroundResource(radio.resId);

        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView nameTxt;
        TextView typeTxt;
    }

}
