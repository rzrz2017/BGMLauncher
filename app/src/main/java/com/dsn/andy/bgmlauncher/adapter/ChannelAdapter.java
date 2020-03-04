package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean2.Channel;
import com.dsn.andy.bgmlauncher.view.RoundTransform;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ChannelAdapter extends BaseAdapter {

    Context context;
    ArrayList<Channel> list;
    Picasso picasso;

    public ChannelAdapter(Context context, ArrayList<Channel> list){
        this.context = context;
        this.list = list;

        File file1= new File("/mnt/sdcard/dsn");
        if (!file1.exists()) {
            file1.mkdirs();
        }

        File file= new File("/mnt/sdcard/dsn/cache");
        if (!file.exists()) {
            file.mkdirs();
        }

        long maxSize = Runtime.getRuntime().maxMemory() / 4;//设置图片缓存大小为运行时缓存的八分之一
        OkHttpClient client = new OkHttpClient()
                .setCache(new Cache(file, maxSize))
                ;

         picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(client))
                .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Channel getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_channel,null);
            vh = new ViewHolder();
            vh.img = (ImageView)convertView.findViewById(R.id.img);
            vh.name = (TextView)convertView.findViewById(R.id.txt);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        Channel channel = getItem(position);
        picasso.load(channel.thumb_medium).transform(new RoundTransform()).into(vh.img);
        vh.name.setText(channel.title);



        return convertView;
    }

    class ViewHolder{
        TextView name;
         ImageView img;
    }


}
