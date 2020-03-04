package com.dson.smart.common.entity;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartHostInfo {
    private boolean supportLogin;
    private String loginPrompt;
    private boolean enableSceneEdit = true;

    private String updateUrl;
    private int versionCode;

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }



    public DsonSmartHostInfo() {
    }

    public boolean getEnableSceneEdit() {
        return this.enableSceneEdit;
    }

    public void setEnableSceneEdit(boolean enable) {
        this.enableSceneEdit = enable;
    }

    public boolean isSupportLogin() {
        return this.supportLogin;
    }

    public String getLoginPrompt() {
        return this.loginPrompt;
    }

    public void setSupportLogin(boolean mSupportLogin) {
        this.supportLogin = mSupportLogin;
    }

    public void setLoginPrompt(String mLoginPrompt) {
        this.loginPrompt = mLoginPrompt;
    }
}

