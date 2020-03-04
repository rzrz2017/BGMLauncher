package com.dsn.andy.bgmlauncher.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean2.Category;

import java.util.ArrayList;

/**
 * Created by dell on 2017/10/19.
 */

public class CategoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<Category> categoryArrayList;

    private int selectIndex =-1;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList){
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Category getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelection(int position){
        this.selectIndex = position;
        notifyDataSetChanged();
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

        Category category = getItem(position);
        holder.nameBtn.setText(category.name);

        if (selectIndex == position){
            holder.nameBtn.setBackgroundResource(R.drawable.bg_type_select);
        }else{
            holder.nameBtn.setBackground(new ColorDrawable(0));
        }


        return convertView;
    }

    class ViewHolder {
        TextView nameBtn;
    }
}
