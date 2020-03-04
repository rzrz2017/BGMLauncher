package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dson.smart.common.entity.DsonSmartScene;

import java.util.List;

/**
 * Created by dell on 2018/3/29.
 */

public class SceneAdapter extends BaseAdapter {

    Context context;
    List<DsonSmartScene> scenes;

    public SceneAdapter(Context context, List<DsonSmartScene> scenes){
        this.context = context;
        this.scenes = scenes;
    }

    public void setData(List<DsonSmartScene> scenes){
        this.scenes = scenes;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return scenes.size();
    }

    @Override
    public DsonSmartScene getItem(int position) {
        return scenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_device,null);
            holder = new ViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.txt = (TextView)convertView.findViewById(R.id.txt);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        DsonSmartScene scene = getItem(position);

        holder.img.setBackgroundResource(R.drawable.ic_dson_device_ac_on);
        holder.txt.setText(scene.getSceneName());

        return convertView;
    }

    class ViewHolder {
        ImageView img;
         TextView txt;
    }
}
