package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Music;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class SearchMusicAdapter extends BaseAdapter {

    Context context;
    ArrayList<Music> musics;
    public SearchMusicAdapter(Context context, ArrayList<Music> musics){
        this.context = context;
        this.musics = musics;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Music getItem(int position) {
        return musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh ;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_search_music,null);
            vh = new ViewHolder();
            vh.music = (TextView)convertView.findViewById(R.id.music_txt);
            vh.singer =(TextView) convertView.findViewById(R.id.music_singer);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        Music music = musics.get(position);
        vh.music.setText((position+1)+"."+music.name);
        vh.singer.setText(music.singer);


        return convertView;
    }

    class ViewHolder{
        TextView music;
        TextView singer;
    }
}
