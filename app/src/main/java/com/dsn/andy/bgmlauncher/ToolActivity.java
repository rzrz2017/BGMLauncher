package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.dsn.andy.bgmlauncher.adapter.ToolAdapter;
import com.dsn.andy.bgmlauncher.bean.Tool;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/8/16.
 */

public class ToolActivity extends Activity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        initView();

    }

    private void initView(){
        ImageView iv = (ImageView)findViewById(R.id.exit);
        iv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        ArrayList<Tool> data = new ArrayList<Tool>();
        int[] imgs = {R.drawable.tool_internet_selector, R.drawable.tool_common_selector, R.drawable.tool_store_selector,
                R.drawable.tool_display_selector, R.drawable.tool_speech_selector,
                R.drawable.tool_introduce_selector, R.drawable.tool_dlna_selector, R.drawable.tool_auxin_selector

        };
        String[] tools = getResources().getStringArray(R.array.array_tools);

        for(int i=0;i<tools.length;i++){
            Tool t = new Tool();
            t.imgResId = imgs[i];
            t.name = tools[i];
            data.add(t);
        }
        ToolAdapter adapter = new ToolAdapter(this,data);
        recyclerView.setAdapter(adapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
    }


    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                outRect.top = space;
        }
    }
}
