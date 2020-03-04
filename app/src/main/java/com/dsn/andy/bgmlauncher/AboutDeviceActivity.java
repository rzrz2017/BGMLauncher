package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.View;

import com.dsn.andy.bgmlauncher.adapter.AboutDeviceAdapter;
import com.dsn.andy.bgmlauncher.bean.AboutDevices;
import com.dsn.andy.bgmlauncher.util.Utils;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/2/2.
 */

public class AboutDeviceActivity extends Activity {

SurfaceView g;
   RecyclerView recyclerView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_device);

        initView();

    }

    private void initView(){



        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        ArrayList<AboutDevices> list=new ArrayList<AboutDevices>();

        int[] bgImg={R.drawable.about_dev_bg,R.drawable.about_dev_bg,R.drawable.about_dev_bg,R.drawable.about_dev_bg,R.drawable.about_dev_bg,R.drawable.about_dev_bg};

        int []img={R.drawable.ic_device_name,R.drawable.ic_device_number,R.drawable.device_problem_selector,R.drawable.device_agreement_selector,R.drawable.device_explain_selector,R.drawable.device_download_selector};
        String [] device={"\n设备名称\nDS_A6","\n设备号\n"+ Utils.getSerialNumber(),"\n常见问题","\n用户协议和版权协议","\n使用说明"};


        for (int i=0;i<device.length;i++){
            AboutDevices a=new AboutDevices();
           a.imgBg=R.drawable.about_dev_bg;
            a.imgResId=img[i];
            a.name=device[i];
            list.add(a);
        }
        AboutDeviceAdapter adapter=new AboutDeviceAdapter(this,list);
        recyclerView.setAdapter(adapter);
       // int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
       // recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }
}
