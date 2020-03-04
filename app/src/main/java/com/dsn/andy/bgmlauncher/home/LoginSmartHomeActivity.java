package com.dsn.andy.bgmlauncher.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dsn.andy.bgmlauncher.R;
import com.dson.support.dsonbase.DsonbaseContant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/3/26.
 */

public class LoginSmartHomeActivity extends Activity implements LoginBroadcastReceiver.OnLoginListener{


    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;

    SmarthomeHelper smarthome;
    LoginBroadcastReceiver receiver;

    Handler handler = new Handler();


    private static LoginSmartHomeActivity instance;

    public static LoginSmartHomeActivity getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_smarthome);
        ButterKnife.bind(this);
        instance = this;

        receiver = (LoginBroadcastReceiver) new LoginBroadcastReceiver(this).register();
        receiver.setOnLiginListener(this);

        if(DsonConstants.getInstance().hasLogin()){
            startActivity(new Intent(this,SmartHomeActivity.class));
            finish();
            return;
        }

        smarthome = SmarthomeHelper.getInstance(getApplicationContext());
        if(!ServiceHelper.getInstance(this).isInit()) {
            login.setEnabled(false);
            smarthome.init();
        }else{
            login.setEnabled(true);
        }

        String u = SharedPrefrenceUtils.getUsername(this);
        String p = SharedPrefrenceUtils.getPassword(this);
        if(u != null){
            username.setText(u);
        }

        if(p != null){
            password.setText(p);
        }

        username.clearFocus();
        password.clearFocus();



    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        if(!ServiceHelper.getInstance(this).isInit()) return;
        final String user = username.getText().toString();
        final String pwd = password.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
            DsonUtils.show(this, "输入不能为空");
            return;
        }

        smarthome.login(user,pwd);

        login.setEnabled(false);

    }

    @Override
    public void onInit(int code) {
        if(code != DsonbaseContant.RESULT_SUCCESS) {
            DsonUtils.show(this,"初始化失败:"+code );

        }else {
            login.setEnabled(true);
        }
    }

    @Override
    public void onGetHostInfo() {

    }

    @Override
    public void onLogin(final int code) {
        login.setEnabled(true);
        if(code == DsonbaseContant.RESULT_SUCCESS) {
            DsonUtils.show(this,"登录成功" );
            handler.post(new Runnable() {
                @Override
                public void run() {
                    SharedPrefrenceUtils.setUsername(LoginSmartHomeActivity.this,username.getText().toString());
                    SharedPrefrenceUtils.setPassword(LoginSmartHomeActivity.this,password.getText().toString());
                    gotoMain();
                }
            });
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                   DsonUtils.show(LoginSmartHomeActivity.this,"登陆失败："+code);
                }
            });
        }
    }

    void gotoMain(){
        startActivity(new Intent(LoginSmartHomeActivity.this, SmartHomeActivity.class));
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.unregister();

        instance = null;
    }
}
