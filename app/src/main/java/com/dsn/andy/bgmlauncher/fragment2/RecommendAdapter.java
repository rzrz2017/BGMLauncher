package com.dsn.andy.bgmlauncher.fragment2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.MusicListActivity;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Album;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/16.
 */

public class RecommendAdapter extends
        RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    Context context;
    ArrayList<Album> albums;

    Handler handler;

    public RecommendAdapter(Context context, ArrayList<Album> albums) {
        this.context = context;
        this.albums = albums;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_tool,
                viewGroup, false);
        RecommendAdapter.ViewHolder viewHolder = new RecommendAdapter.ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.img);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.txt);
        viewHolder.mTxt.setVisibility(View.GONE);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final Album item = albums.get(i);
        viewHolder.mImg.setBackgroundResource(item.resId);

        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, MusicListActivity.class);
                it.putExtra("album", item);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        TextView mTxt;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
