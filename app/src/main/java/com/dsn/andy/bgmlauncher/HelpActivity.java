package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CommomDialog;

/**
 * Created by Administrator on 2018/3/29.
 */

public class HelpActivity extends Activity {

    WebView wvHelp;
    LinearLayout llHelp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        wvHelp = (WebView) findViewById(R.id.wv_help);
        llHelp = (LinearLayout) findViewById(R.id.ll_help);
        Intent intent=getIntent();
        String str=intent.getStringExtra("showType");
        int type = Integer.parseInt(str);
        if (type != 4) {
            if (!Utils.isWifiConnected(this)) {
//                CommomDialog wificonnectDialog = new CommomDialog(this, R.style.dialog, "您的wifi尚连接,请连接wifi进行体验!", new CommomDialog.OnCloseListener() {
//                    @Override
//                    public void onClick(Dialog dialog, boolean confirm) {
//                        if (confirm) {
//                            Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                            startActivity(it);
//                            dialog.dismiss();
//                        }
//                    }
//                }).setTitle("提示").setNegativeButton("暂不连接").setPositiveButton("去连接");
//                wificonnectDialog.show();
            } else {
                switch (type) {
                    case 1://常见问题
                        wvHelp.setVisibility(View.VISIBLE);
                        llHelp.setVisibility(View.GONE);
                        wvHelp.loadUrl("http://112.74.187.196/helpFile/help.jsp");
                        break;
                    case 2://版权和用户协议
                        wvHelp.setVisibility(View.VISIBLE);
                        llHelp.setVisibility(View.GONE);
                        wvHelp.loadUrl("http://112.74.187.196/helpFile/copyright.jsp");
                        break;
                    case 3://使用说明
                        wvHelp.setVisibility(View.VISIBLE);
                        llHelp.setVisibility(View.GONE);
                        wvHelp.loadUrl("http://112.74.187.196/helpFile/expilan.jsp");
                        break;
                }

            }
        }else{
            llHelp.setVisibility(View.VISIBLE);
            wvHelp.setVisibility(View.GONE);
        }
    }
}
