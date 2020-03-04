package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Album;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class AlbumAdapter extends BaseAdapter {

    Context context;
    ArrayList<Album> list;

    public AlbumAdapter(Context context,ArrayList<Album> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Album getItem(int position) {
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

        Album am = getItem(position);
        vh.img.setBackgroundResource(am.resId);
        vh.name.setText(am.name);



        return convertView;
    }

    class ViewHolder{
        TextView name;
         ImageView img;
    }


}
