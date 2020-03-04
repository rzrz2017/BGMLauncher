package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.geniusgithub.mediarender.RenderApplication;
import com.geniusgithub.mediarender.center.MediaRenderProxy;
import com.geniusgithub.mediarender.util.DlnaUtils;

/**
 * Created by dell on 2018/1/2.
 */

public class DLNAActivity extends Activity {
    EditText edit;
    Button editBtn,btnHelp;
    ImageView clear;
    LinearLayout helpLinearLayout;
    RenderApplication mApplication;
    MediaRenderProxy mRenderProxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlna);

        edit = (EditText) findViewById(R.id.name);
        editBtn = (Button) findViewById(R.id.edit);
        clear = (ImageView) findViewById(R.id.clear);
        btnHelp=(Button) findViewById(R.id.help);
        helpLinearLayout=(LinearLayout)findViewById(R.id.ll_show);
        helpLinearLayout.setVisibility(View.INVISIBLE);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setText("");
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change();
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helpLinearLayout.getVisibility()==View.VISIBLE){
                    btnHelp.setText("打开推送帮助");
                    helpLinearLayout.setVisibility(View.INVISIBLE);
                }else{
                    btnHelp.setText("关闭推送帮助");
                    helpLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        initData();


    }

    private void initData() {
        mApplication = RenderApplication.getInstance();
        mRenderProxy = MediaRenderProxy.getInstance();

        String dev_name = DlnaUtils.getDevName(this);
        edit.setText(dev_name);


    }

    private void change() {
        String str = edit.getText().toString();
        if (str == null || str.length() < 5) {
            Toast.makeText(this, "名称太短", Toast.LENGTH_SHORT).show();
            return;
        }

        DlnaUtils.setDevName(this, str);
        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();

    }
}
