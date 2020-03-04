package com.dsn.andy.bgmlauncher.com485;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andy.music.COMCommand;
import com.dsn.andy.bgmlauncher.R;

import java.util.List;

/**
 * Created by dell on 2018/10/3.
 */

public class COMCommandAdapter extends BaseAdapter {

    private List<COMCommand> data;
    private Context context;

    public COMCommandAdapter(Context context,List<COMCommand> data){
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public COMCommand getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_command,null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        COMCommand command = getItem(position);
        holder.name.setText(command.getCommandName());


        return convertView;
    }

    class ViewHolder{
        TextView name;
    }
}
