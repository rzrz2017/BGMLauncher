package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.SpeechHelp;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 */

public class SpeechHelpAdapter extends BaseAdapter {
    Context mContext;
    List<SpeechHelp> list;
    public SpeechHelpAdapter(Context mContext, List<SpeechHelp> list){
        this.mContext = mContext;
        this.list = list;


    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.adapter_speechhelp,null);
            vh=new ViewHolder();
            vh.icon=(ImageView)convertView.findViewById(R.id.iv_icon);
            vh.answer=(TextView)convertView.findViewById(R.id.tv_answer);
            vh.prompt=(TextView)convertView.findViewById(R.id.tv_prompt);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }
        SpeechHelp speechHelp=list.get(position);
        vh.icon.setBackgroundResource(speechHelp.icon);
        vh.answer.setText(speechHelp.answer);
        vh.prompt.setText(speechHelp.prompt);
        return convertView;
    }

    class ViewHolder{
        TextView prompt;
        ImageView icon;
        TextView answer;

    }

}
