package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Music;

import java.util.ArrayList;

/**
 * Created by dell on 2017/11/28.
 */

public class MusicAdapter2 extends BaseAdapter {
    Context context;
    ArrayList<Music> data;
    int curPosition=-1;

    public MusicAdapter2(Context context, ArrayList<Music> data){
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Music getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.adapter_music_list,
                    null, false);
            viewHolder = new ViewHolder();

            viewHolder.nameTxt = (TextView) convertView
                    .findViewById(R.id.name);
            viewHolder.singerTxt = (TextView) convertView
                    .findViewById(R.id.singer);
            viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            viewHolder.curImg = (ImageView) convertView
                    .findViewById(R.id.cur);

            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder) convertView.getTag();
        }


        Music music = getItem(position);

        viewHolder.nameTxt.setText((position+1)+"."+music.name);
        viewHolder.singerTxt.setText(music.singer);


        if (position == curPosition) {
            viewHolder.curImg.setVisibility(View.VISIBLE);
//            viewHolder.nameTxt.setTextColor(0xff17aa9e);
//            viewHolder.singerTxt.setTextColor(0x5517aa9e);
        } else {
            viewHolder.curImg.setVisibility(View.GONE);
//            viewHolder.nameTxt.setTextColor(context.getResources().getColor(R.color.default_color));
//            viewHolder.singerTxt.setTextColor(context.getResources().getColor(R.color.default_color2));
        }



        return convertView;
    }

    public void setCurPosition(int position){
        curPosition = position;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView nameTxt;
        RelativeLayout layout;
        ImageView curImg;
        TextView singerTxt;
    }
}
