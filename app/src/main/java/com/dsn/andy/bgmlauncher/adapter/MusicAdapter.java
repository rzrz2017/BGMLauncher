package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Music;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MusicAdapter extends
        RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    public static interface OnItemClickListener {
        public void onItemClick(Music music);
    }

    MusicAdapter.OnItemClickListener listener;

    int curPosition = -1;

    public void setOnItemClickListener(MusicAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    Context context;
    ArrayList<Music> data;

    public MusicAdapter(Context context, ArrayList<Music> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_music_list,
                viewGroup, false);
        MusicAdapter.ViewHolder viewHolder = new MusicAdapter.ViewHolder(view);

        viewHolder.nameTxt = (TextView) view
                .findViewById(R.id.name);
        viewHolder.singerTxt = (TextView) view
                .findViewById(R.id.singer);
        viewHolder.layout = (RelativeLayout) view.findViewById(R.id.layout);
        viewHolder.layout.setClickable(true);
        viewHolder.curImg = (ImageView) view
                .findViewById(R.id.cur);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MusicAdapter.ViewHolder viewHolder, final int i) {

        Music music = data.get(i);

        viewHolder.nameTxt.setText(i+"."+music.name);
        viewHolder.singerTxt.setText(music.singer);

        viewHolder.itemView.setTag(i);

        if (i == curPosition) {
            viewHolder.curImg.setVisibility(View.VISIBLE);
            viewHolder.nameTxt.setTextColor(0xff17aa9e);
            viewHolder.singerTxt.setTextColor(0x5517aa9e);
        } else {
            viewHolder.curImg.setVisibility(View.GONE);
            viewHolder.nameTxt.setTextColor(context.getResources().getColor(R.color.default_color));
            viewHolder.singerTxt.setTextColor(context.getResources().getColor(R.color.default_color2));
        }

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curPosition = i;
                notifyDataSetChanged();
                Music music = data.get(i);
                if (listener != null) {
                    listener.onItemClick(music);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        RelativeLayout layout;
        ImageView curImg;
        TextView singerTxt;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setCurPosition(int position){
        curPosition = position;
        notifyDataSetChanged();
    }

}
