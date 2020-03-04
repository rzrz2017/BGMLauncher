package com.dsn.andy.bgmlauncher.home;

import android.content.Context;

import com.dson.smart.common.entity.DsonSmartAccount;
import com.dson.smart.common.entity.DsonSmartDevice;
import com.dson.smart.common.entity.DsonSmartDeviceType;
import com.dson.smart.common.entity.DsonSmartDevices;
import com.dson.smart.common.entity.DsonSmartHostInfo;
import com.dson.smart.common.entity.DsonSmartRooms;
import com.dson.smart.common.entity.DsonSmartScenes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/4/8.
 */

public class DsonConstants {
    private static DsonConstants instance;

    private boolean hasLogin;
    private DsonSmartHostInfo hostInfo;
    private DsonSmartAccount account;

    private DsonSmartDevices devices;
    private DsonSmartRooms rooms;
    private DsonSmartScenes scenes;
    private List<DsonSmartDeviceType> types;

    private DsonConstants() {

    }

    public List<DsonSmartDevice> getDevice(DsonSmartDeviceType type) {
        final String str = String.valueOf(type.getType());
        List<DsonSmartDevice> list = new ArrayList();
        for (DsonSmartDevice d : devices.getDevices()) {
            if (d.getDeviceType().equals(str)) {
                list.add(d);
            }
        }

        return list;
    }

    public List<DsonSmartDeviceType> getTypes() {
        return types;
    }

    public void setTypes(List<DsonSmartDeviceType> types) {
        this.types = types;
    }

    public DsonSmartAccount getAccount() {
        return account;
    }

    public void setAccount(DsonSmartAccount account) {
        this.account = account;
    }

    public DsonSmartHostInfo getHostInfo() {
        return hostInfo;
    }

    public void setHostInfo(DsonSmartHostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    public void setLogin(boolean isLogin) {
        hasLogin = isLogin;
    }

    public boolean hasLogin() {
        return hasLogin;
    }

    public DsonSmartDevices getDevices() {
        return devices;
    }

    public void setDevices(DsonSmartDevices devices) {
        this.devices = devices;
    }

    public DsonSmartRooms getRooms() {
        return rooms;
    }

    public void setRooms(DsonSmartRooms rooms) {
        this.rooms = rooms;
    }

    public DsonSmartScenes getScenes() {
        return scenes;
    }

    public void setScenes(DsonSmartScenes scenes) {
        this.scenes = scenes;
    }


    public static DsonConstants getInstance() {
        if (instance == null) {
            instance = new DsonConstants();
        }

        return instance;
    }

    public void exit(Context context) {
        setLogin(false);
        if(devices != null) {
            devices.getDevices().clear();
            devices = null;
            rooms.getRooms().clear();
            rooms = null;
            scenes.getScenes().clear();
            scenes = null;
            types.clear();
            types = null;
            account = null;
        }
//        SharedPrefrenceUtils.setUsername(context,"");
//        SharedPrefrenceUtils.setPassword(context,"");
        SmarthomeHelper.getInstance(context).exit();
    }


}
