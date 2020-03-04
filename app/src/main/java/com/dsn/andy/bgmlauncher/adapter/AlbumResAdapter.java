package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Album;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/14.
 */

public class AlbumResAdapter extends BaseAdapter {

    Context context;
    ArrayList<Album> albums;

    public AlbumResAdapter(Context context, ArrayList<Album> albums){
        this.context = context;
        this.albums = albums;
    }

    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Album getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return albums.get(position).resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_album,null);
            holder = new ViewHolder();
            holder.album =(ImageView) convertView.findViewById(R.id.album);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.album.setBackgroundResource(albums.get(position).resId);

        return convertView;
    }

    class ViewHolder {
        ImageView album;
    }
}
