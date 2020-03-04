package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/2/2.
 */

public class ExplainActivity extends Activity {


  ListView listView;
    TextView text;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        ImageView iv = (ImageView)findViewById(R.id.exit);
        iv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
        listView=(ListView)findViewById(R.id.list);
        text=(TextView) findViewById(R.id.textView);

        //使用Intent对象得到FirstActivity传递来的参数
        Intent intent = getIntent();
        String value = intent.getStringExtra("showType");
        switch (Integer.parseInt(value)){
            case 1:
                text.setText("常见问题");
                break;
            case 2:
                text.setText("用户协议和版权协议");
                break;
            case 3:
                text.setText("使用说明常见问题");
                break;
        }




    }

}
