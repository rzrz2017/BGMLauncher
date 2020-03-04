package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean2.Program;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class ProgramAdapter extends BaseAdapter {

    Context context;
    ArrayList<Program> list;

    public ProgramAdapter(Context context, ArrayList<Program> list){
        this.context = context;
        this.list = list;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Program getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_program,null);
            vh = new ViewHolder();
            vh.duration = (TextView)convertView.findViewById(R.id.duration);
            vh.title = (TextView)convertView.findViewById(R.id.title);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        Program program = getItem(position);
        vh.title.setText((position+1)+"."+program.title);
        vh.duration.setText(toTime(program.duration));



        return convertView;
    }

    public static String toTime(int time) {
        String str = "";
        int seconds = time;
        int m = seconds / 60;
        int s = seconds % 60;
        if (m == 0) {
            str = "00:";
        } else if(m<10){
            str = "0" + m + ":";
        }else{
            str = m+":";
        }

        if (s < 10) {
            str += "0" + s;
        } else {
            str += s + "";
        }

        return str;
    }

    class ViewHolder{
        TextView title;
         TextView duration;
    }


}
