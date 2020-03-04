package com.dsn.andy.bgmlauncher.com485;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.music.COMCommand;
import com.andy.music.COMDevice;
import com.dsn.andy.bgmlauncher.R;

import java.util.List;

/**
 * Created by dell on 2018/10/2.
 */

public class COMCommandImgAdapter extends BaseAdapter {

    private Context context;
    private List<COMCommand> commands;

    public COMCommandImgAdapter(Context context, List<COMCommand> commands){
        this.context = context;
        this.commands = commands;
    }


    @Override
    public int getCount() {
        return commands.size();
    }

    @Override
    public COMCommand getItem(int position) {
        return commands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        COMCommandImgAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_device,null);
            holder = new COMCommandImgAdapter.ViewHolder();
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.txt = (TextView)convertView.findViewById(R.id.txt);

            convertView.setTag(holder);
        }else{
            holder = (COMCommandImgAdapter.ViewHolder)convertView.getTag();
        }
        COMCommand comCommand = getItem(position);
        holder.txt.setText(comCommand.getCommandName());

        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView txt;
    }




}
