package com.dsn.andy.bgmlauncher.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dson.smart.common.entity.DsonSmartRoom;

import java.util.List;

/**
 * Created by dell on 2018/3/28.
 */

public class AreaAdapter extends BaseAdapter {

    Context context;
    List<DsonSmartRoom> data ;
    int selection;

    public AreaAdapter(Context context, List<DsonSmartRoom> areaData){
        this.context = context;
        this.data = areaData;
    }

    public void setData(List<DsonSmartRoom> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setCurrent(int p){
        selection = p;
        notifyDataSetChanged();;
    }

    public int getCurPosition(){
        return selection;
    }

    public int getItemPosition(DsonSmartRoom room){
        for (int i=0;i<data.size();i++){
            DsonSmartRoom r  = data.get(i);
            if(r.getRoomId().equals(room.getRoomId())){
                return i;
            }
        }

        return -1;
    }

    public DsonSmartRoom getCurrentItem(){
        return data.get(selection);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DsonSmartRoom getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_fm_category,null);
            holder = new ViewHolder();
            holder.nameBtn = (TextView)convertView.findViewById(R.id.name);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }


        DsonSmartRoom area = getItem(position);

        holder.nameBtn.setText(area.getRoomName());

        Log.e("andy","area="+area.getRoomName());


        if(selection == position){
            holder.nameBtn.setBackgroundResource(R.drawable.bg_type_select);
        }else{
            holder.nameBtn.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }



        return convertView;
    }

    class ViewHolder {
        TextView nameBtn;
    }

}
