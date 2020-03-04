package com.dson.smart.common.virtualhost;

import android.content.Context;

import com.dson.smart.common.entity.DsonSmartAccount;
import com.dson.smart.common.entity.DsonSmartCtrlCmd;
import com.dson.smart.common.entity.DsonSmartDevices;
import com.dson.smart.common.entity.DsonSmartHostInfo;
import com.dson.smart.common.entity.DsonSmartScene;
import com.dson.support.dsonbase.DsonbaseCallback;

import java.util.List;

/**
 * Created by dell on 2018/3/14.
 */


public interface IDsonSmartHost {
    void init(Context var1);

    DsonSmartHostInfo getHostInfo();

    void login(String var1, String var2, DsonbaseCallback var3);

    DsonSmartAccount getAccount();

    void logout(DsonbaseCallback var1);

    void searchAndBindHost(boolean var1, DsonbaseCallback var2);

    void unbindHost(DsonbaseCallback var1);

    void getScenes(DsonbaseCallback var1);

    void createScene(DsonSmartScene var1, DsonbaseCallback var2);

    void deleteScene(DsonSmartScene var1, DsonbaseCallback var2);

    void updateScene(DsonSmartScene var1, DsonbaseCallback var2);

    void createSceneBind(List<DsonSmartCtrlCmd> var1, DsonbaseCallback var2);

    void deleteSceneBind(List<DsonSmartCtrlCmd> var1, DsonbaseCallback var2);

    void updateSceneBind(List<DsonSmartCtrlCmd> var1, DsonbaseCallback var2);

    void getSceneBind(DsonSmartScene var1, DsonbaseCallback var2);

    void getAllDevices(DsonbaseCallback var1);

    void getAllDeviceType(DsonbaseCallback var1);

    DsonSmartDevices getDevicesByType(int var1);

    void getDeviceDetail(String var1, DsonbaseCallback var2);

    void controlDevice(DsonSmartCtrlCmd var1, DsonbaseCallback var2);

    void controlScene(DsonSmartScene var1, DsonbaseCallback var2);

    void getSensorRecord(String var1, int var2, int var3, DsonbaseCallback var4);

    void registerDeviceChange(DsonbaseCallback var1);
}

