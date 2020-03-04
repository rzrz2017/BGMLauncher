package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by dell on 2018/4/17.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected  Context context;
    List<T> data;


    public CommonAdapter(Context context, List<T> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView);
    }

    public abstract View getView(int position,View convertView);


}
