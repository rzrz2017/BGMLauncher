package com.dsn.andy.bgmlauncher.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.bean.Cook;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dell on 2017/11/29.
 */

public class CookDialog extends Dialog {

    Context context;

    private TextView title;
    private TextView ok;
    ViewPager pager;
    LinearLayout points;
    ArrayList<Cook> cooks;
    String titleStr;

    ArrayList<View> views = new ArrayList();
    ArrayList<ImageView> imgs = new ArrayList();

    public CookDialog( Context context) {
        super(context);
        this.context = context;
    }

    public CookDialog( Context context,  int themeResId, String titleStr, ArrayList<Cook> cooks) {
        super(context, themeResId);
        this.context = context;
        this.cooks = cooks;
        this.titleStr = titleStr;
    }

    public CookDialog( Context context, boolean cancelable,  OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cookbook);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        title = (TextView)findViewById(R.id.title);
        pager = (ViewPager)findViewById(R.id.viewpager);
        ok = (TextView)findViewById(R.id.ok);
        points = (LinearLayout)findViewById(R.id.points);




        for(int i=0;i<cooks.size();i++){
            Cook c = cooks.get(i);

            ImageView  point = new ImageView(context);
            point.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams paramTest = (LinearLayout.LayoutParams) point.getLayoutParams();
            paramTest.leftMargin = 50;
            paramTest.rightMargin = 50;
            point.setLayoutParams(paramTest);
//            point.setBackgroundResource(R.drawable.);
            imgs.add(point);

            points.addView(point);

            View layout = LayoutInflater.from(context).inflate(R.layout.layout_cookbook,null);
            ImageView img = (ImageView)layout.findViewById(R.id.img);
            TextView name = (TextView)layout.findViewById(R.id.name);
            TextView incre = (TextView)layout.findViewById(R.id.incred);
            TextView steps = (TextView)layout.findViewById(R.id.steps);

            Picasso.with(context).load(c.imgUrl).transform(new CircleTransform()).into(img);
            name.setText(c.title);
            incre.setText(c.ingredient);
            steps.setText(c.steps);

            views.add(layout);

        }

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return views.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(views.get(position));


                return views.get(position);
            }
        };


        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        title.setText(titleStr);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
