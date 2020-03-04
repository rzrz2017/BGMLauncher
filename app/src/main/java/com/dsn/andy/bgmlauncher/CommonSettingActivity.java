package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.com485.SmartHome485Activity;
import com.dsn.andy.bgmlauncher.home.AndroidUtils;
import com.dsn.andy.bgmlauncher.home.DsonConstants;
import com.dsn.andy.bgmlauncher.home.DsonUtils;
import com.dsn.andy.bgmlauncher.home.SmarthomeSettingActivity;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CommomDialog;

/**
 * Created by dell on 2017/10/12.
 */

public class CommonSettingActivity extends Activity implements View.OnClickListener{

    LinearLayout abountLayout;
    LinearLayout updateLayout;
    LinearLayout smarthomeLayout;
    LinearLayout advancedsettingLayout;
    LinearLayout awakenLayout;
    LinearLayout smEnableLayout;
    TextView txtSmEnable;

    LinearLayout sm485EnableLayout;
    TextView txtSm485Enable;

    LinearLayout sm485SettingLayout;

    TextView txtAwake;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_setting);
        initViews();

    }

    void initViews(){
        abountLayout = (LinearLayout) findViewById(R.id.set_abount);
        updateLayout = (LinearLayout) findViewById(R.id.set_update);
        smarthomeLayout = (LinearLayout) findViewById(R.id.set_smarthome);
        advancedsettingLayout=(LinearLayout)findViewById(R.id.set_advanced_setting);
        awakenLayout=(LinearLayout)findViewById(R.id.set_awaken);
        txtAwake = (TextView)findViewById(R.id.txt_awaken);
        smEnableLayout = (LinearLayout)findViewById(R.id.set_smarthome_enable);
        txtSmEnable = (TextView)findViewById(R.id.txt_smarthome_enable);

        if(Utils.isSupportSmartHome(this)){
            txtSmEnable.setText("支持");
        }else{
            txtSmEnable.setText("不支持");
        }

        sm485EnableLayout = (LinearLayout)findViewById(R.id.set_smarthome485_enable);
        txtSm485Enable = (TextView)findViewById(R.id.txt_smarthome485_enable);

        if(Utils.isSupportSmartHome485(this)){
            txtSm485Enable.setText("支持");
        }else{
            txtSm485Enable.setText("不支持");
        }

        sm485SettingLayout = (LinearLayout)findViewById(R.id.set_smarthome485_setting);



        TextView sn = (TextView) findViewById(R.id.sn);

        //sn.setText(Utils.getSerialNumber());

        abountLayout.setOnClickListener(this);
        smEnableLayout.setOnClickListener(this);
        sm485EnableLayout.setOnClickListener(this);
        updateLayout.setOnClickListener(this);
        smarthomeLayout.setOnClickListener(this);
        awakenLayout.setOnClickListener(this);
        advancedsettingLayout.setOnClickListener(this);
        sm485SettingLayout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.set_abount:
                startActivity(new Intent(this,AboutDeviceActivity.class));
                break;
            case R.id.set_smarthome:
                if(!AndroidUtils.isInstallApp(this,AndroidUtils.SDK_PACKAGE_NAME)){
                    Toast.makeText(this,"请先安装智能家居插件APP",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!DsonConstants.getInstance().hasLogin()){
                    DsonUtils.show(this,"请先启动智能家居");
                    return;
                }
                startActivity(new Intent(this, SmarthomeSettingActivity.class));
                break;
            case R.id.set_update:
                if(!Utils.isWifiConnected(this)){
//                    CommomDialog wificonnectDialog = new CommomDialog(this, R.style.dialog, "您的wifi尚连接,请连接wifi进行体验!", new CommomDialog.OnCloseListener() {
//                        @Override
//                        public void onClick(Dialog dialog, boolean confirm) {
//                            if (confirm) {
//                                Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                                startActivity(it);
//                                dialog.dismiss();
//                            }
//                        }
//                    }).setTitle("提示").setNegativeButton("暂不连接").setPositiveButton("去连接");
//                    wificonnectDialog.show();
                }else {
                    startActivity(new Intent(this, UpdateActivity.class));
                }
                break;
            case R.id.set_advanced_setting:
                Intent intent =  new Intent(this,AppsActivity.class);
                startActivity(intent);
                break;
            case R.id.set_awaken:
               // startActivity(new Intent(this,AwakenActivity.class));
                new CommomDialog(this,R.style.dialog,"请设置是否语音唤醒",new CommomDialog.OnCloseListener(){
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            txtAwake.setText("允许唤醒");
                        }else{
                            txtAwake.setText("禁止唤醒");
                        }
                    }
                }).setPositiveButton("允许唤醒").setNegativeButton("禁止唤醒").show();
                break;

            case R.id.set_smarthome_enable:
                // startActivity(new Intent(this,AwakenActivity.class));
                new CommomDialog(this,R.style.dialog,"请设置是否支持智能家居",new CommomDialog.OnCloseListener(){
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            txtSmEnable.setText("支持");
                            Utils.setSupportSmartHome(CommonSettingActivity.this,true);

                            Toast.makeText(CommonSettingActivity.this,"请重启生效",Toast.LENGTH_LONG).show();

                            txtSm485Enable.setText("不支持");
                            Utils.setSupportSmartHome485(CommonSettingActivity.this,false);
                        }else{
                            txtSmEnable.setText("不支持");
                            Utils.setSupportSmartHome(CommonSettingActivity.this,false);
                        }
                    }
                }).setPositiveButton("支持").setNegativeButton("不支持").show();
                break;
            case R.id.set_smarthome485_enable:
                // startActivity(new Intent(this,AwakenActivity.class));
                new CommomDialog(this,R.style.dialog,"请设置是否485支持智能家居",new CommomDialog.OnCloseListener(){
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            txtSm485Enable.setText("支持");
                            Utils.setSupportSmartHome485(CommonSettingActivity.this,true);

                            Toast.makeText(CommonSettingActivity.this,"请重启生效",Toast.LENGTH_LONG).show();

                            txtSmEnable.setText("不支持");
                            Utils.setSupportSmartHome(CommonSettingActivity.this,false);
                        }else{
                            txtSm485Enable.setText("不支持");
                            Utils.setSupportSmartHome485(CommonSettingActivity.this,false);
                        }
                    }
                }).setPositiveButton("支持").setNegativeButton("不支持").show();
                break;

            case R.id.set_smarthome485_setting:
                if(!Utils.isSupportSmartHome485(CommonSettingActivity.this)){
                    Utils.toast(CommonSettingActivity.this,"请先打开485智能家居支持");
                    return;
                }
                startActivity(new Intent(this, SmartHome485Activity.class));
                break;
        }
    }
}