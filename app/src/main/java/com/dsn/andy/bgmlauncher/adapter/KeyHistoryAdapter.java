package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.music.Key_1;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.db2.DBUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class KeyHistoryAdapter extends BaseAdapter {
    Context context;
    List<Key_1> keys;

    public KeyHistoryAdapter(Context context, List<Key_1> keys){
        this.context = context;
        this.keys = keys;
    }

    @Override
    public int getCount() {
        return keys.size();
    }

    @Override
    public Key_1 getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_key_history,null);
            vh = new ViewHolder();
            vh.keyTxt =(TextView) convertView.findViewById(R.id.key_txt);
            vh.deleteImg = (ImageView)convertView.findViewById(R.id.delete);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        final Key_1 key = getItem(position);

        vh.keyTxt.setText(key.getKey());

        vh.deleteImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBUtils d = DBUtils.getInstance(context);
                d.deleteKey(key);
                keys.remove(key);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView keyTxt;
        ImageView deleteImg;
    }
}
