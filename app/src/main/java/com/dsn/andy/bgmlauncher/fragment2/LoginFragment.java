package com.dsn.andy.bgmlauncher.fragment2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.MainActivity2;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.home.AndroidUtils;
import com.dsn.andy.bgmlauncher.home.DsonUtils;
import com.dsn.andy.bgmlauncher.home.ServiceHelper;
import com.dsn.andy.bgmlauncher.home.SharedPrefrenceUtils;
import com.dsn.andy.bgmlauncher.home.SmarthomeHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/9/21.
 */

public class LoginFragment extends PageFragment {

    View layout;
    @Bind(R.id.etUser)
    EditText etUser;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.btnLogin)
    Button btnLogin;



    public LoginFragment() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public LoginFragment setActivity(MainActivity2 activity){
        this.activity = activity;

        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_login, null);

        if (!AndroidUtils.isInstallApp(getContext(), AndroidUtils.SDK_PACKAGE_NAME)) {
            Toast.makeText(getContext(), "请先安装智能家居插件APP", Toast.LENGTH_SHORT).show();
        }

        Log.e("andy", "fragment oncreateview :loginFragment");
        ButterKnife.bind(this, layout);

        String u = SharedPrefrenceUtils.getUsername(getActivity());
        String p = SharedPrefrenceUtils.getPassword(getActivity());
        if(u != null){
            etUser.setText(u);
        }

        if(p != null){
            etPwd.setText(p);
        }

        etUser.clearFocus();
        etPwd.clearFocus();



        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        SmarthomeHelper smarthome = SmarthomeHelper.getInstance(getActivity());
        if(!ServiceHelper.getInstance(getActivity()).isInit()) return;
        final String user = etUser.getText().toString();
        final String pwd = etPwd.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
            DsonUtils.show(getActivity(), "输入不能为空");
            return;
        }

        smarthome.login(user,pwd);

        SharedPrefrenceUtils.setUsername(getActivity(),user);
        SharedPrefrenceUtils.setPassword(getActivity(),pwd);


    }
}
