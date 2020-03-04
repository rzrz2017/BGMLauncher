package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Key;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class KeyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Key> keys;

    public KeyAdapter(Context context, ArrayList<Key> keys){
        this.context = context;
        this.keys = keys;
    }

    @Override
    public int getCount() {
        return keys.size();
    }

    @Override
    public Key getItem(int position) {
        return keys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_key,null);
            vh = new ViewHolder();
            vh.keyTxt =(TextView) convertView.findViewById(R.id.key_txt);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        Key key = getItem(position);

        vh.keyTxt.setText(key.key);

        if(key.key.length()>1){
            vh.keyTxt.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.key_size_small));
        }else{
            vh.keyTxt.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.key_size_big));
        }

        return convertView;
    }

    class ViewHolder{
        TextView keyTxt;
    }
}
