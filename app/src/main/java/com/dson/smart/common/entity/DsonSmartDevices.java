package com.dson.smart.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartDevices {
    private String vendor;
    private List<DsonSmartDevice> devices = new ArrayList();

    public DsonSmartDevices() {
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<DsonSmartDevice> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DsonSmartDevice> devices) {
        this.devices = devices;
    }

    public void addDevice(DsonSmartDevice device) {
        this.devices.add(device);
    }

    public String toString() {
        return "dsonsmartDevices{vendor=\'" + this.vendor + '\'' + ", devices=" + this.devices + '}';
    }
}

