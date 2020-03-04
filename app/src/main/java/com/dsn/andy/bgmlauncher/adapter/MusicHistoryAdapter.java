package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andy.music.Music_1;
import com.andy.music.Program_1;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.LocalMusic;
import com.dsn.andy.bgmlauncher.bean.Music;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class MusicHistoryAdapter<T> extends BaseAdapter {

    Context context;
    List<T> list;

    int curPosition = -1;

   public void setCurPosition(int p){
        curPosition = p;
        notifyDataSetChanged();
    }

    public MusicHistoryAdapter(Context context, List<T> list){
        this.context = context;
        this.list = list;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_history,null);
            vh = new ViewHolder();
            vh.name = (TextView)convertView.findViewById(R.id.name);
            vh.singer = (TextView)convertView.findViewById(R.id.singer);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        T t = getItem(position);
        if(t instanceof Music_1) {
            Music_1 music  = (Music_1)t;
            vh.name.setText((position + 1) + "." + music.getName());
            vh.singer.setText(music.getSinger());
        }else if(t instanceof Program_1){
            Program_1 program_1 = (Program_1)t;
            vh.name.setText((position+1)+"."+program_1.getTitle());
            vh.singer.setVisibility(View.GONE);
        }else if(t instanceof LocalMusic){
            LocalMusic music = (LocalMusic)t;
            vh.name.setText((position+1)+"."+music.name);
            vh.singer.setText(music.path);
        }else if(t instanceof Music){
            Music music = (Music)t;
            vh.name.setText((position+1)+"."+music.name);
            vh.singer.setText(music.singer);
        }

        if(curPosition == position){
            vh.name.setTextColor(Color.WHITE);
            vh.singer.setTextColor(Color.WHITE);
        }else {
            vh.name.setTextColor(context.getResources().getColor(R.color.default_color));
            vh.singer.setTextColor(context.getResources().getColor(R.color.default_color2));
        }



        return convertView;
    }

    class ViewHolder{
        TextView name;
         TextView singer;
    }


}
