package com.dson.smart.common.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartDevice {
    String deviceType;
    String deviceSubType;
    String deviceId;
    String deviceId2;
    String deviceName;
    String zoneId;
    String roomId;
    String vendor;
    String model;
    String sn;
    String pic;
    int online;
    boolean selected;
    String dsonDeviceName;
    String dsonFloorName;
    String dsonRoomName;
    String dsonIRkeyList;
    String dsonAlert;
    private DsonSmartCtrlCmd dsonsmartCtrlCmd;

    public DsonSmartDevice() {
    }

    @JSONField(
            name = "dsonsmartCtrlCmd"
    )
    public DsonSmartCtrlCmd getDsonSmartCtrlCmd() {
        return this.dsonsmartCtrlCmd;
    }
    /** @deprecated */
    @JSONField(
            name = "dsonsmartCtrlCmd"
    )
    public DsonSmartCtrlCmd getDsonsmartCtrlCmd() {
        return this.dsonsmartCtrlCmd;
    }
    public DsonSmartCtrlCmd buildSmartCtrlCmd() {
        if(this.dsonsmartCtrlCmd == null) {
            this.dsonsmartCtrlCmd = new DsonSmartCtrlCmd();
            this.dsonsmartCtrlCmd.setDeviceId(this.getDeviceId());
            this.dsonsmartCtrlCmd.setVendor(this.getVendor());
        }

        return this.dsonsmartCtrlCmd;
    }

    public void setDsonSmartCtrlCmd(DsonSmartCtrlCmd dsonsmartCtrlCmd) {
        this.dsonsmartCtrlCmd = dsonsmartCtrlCmd;
    }

    /** @deprecated */
    public void setDsonsmartCtrlCmd(DsonSmartCtrlCmd dsonsmartCtrlCmd) {
        this.dsonsmartCtrlCmd = dsonsmartCtrlCmd;
    }

    public String getDeviceSubType() {
        return this.deviceSubType;
    }

    public void setDeviceSubType(String deviceSubType) {
        this.deviceSubType = deviceSubType;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getOnline() {
        return this.online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDeviceId2() {
        return this.deviceId2;
    }

    public void setDeviceId2(String deviceId2) {
        this.deviceId2 = deviceId2;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getZoneId() {
        return this.zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setDsonSmartDevice(DsonSmartDevice dev) {
        this.setDeviceId(dev.getDeviceId());
        this.setDeviceId2(dev.getDeviceId2());
        this.setVendor(dev.getVendor());
        this.setDeviceName(dev.getDeviceName());
        this.setDeviceType(dev.getDeviceType());
        this.setDeviceSubType(dev.getDeviceSubType());
        this.setModel(dev.getModel());
        this.setSn(dev.getSn());
        this.setZoneId(dev.getZoneId());
        this.setRoomId(dev.getRoomId());
        this.setDsonSmartCtrlCmd(dev.getDsonSmartCtrlCmd());
        this.setPic(dev.getPic());
        this.setOnline(dev.getOnline());
        this.setDsonDeviceName(dev.getDsonDeviceName());
        this.setDsonFloorName(dev.getDsonFloorName());
        this.setDsonRoomName(dev.getDsonRoomName());
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(!(o instanceof DsonSmartDevice)) {
            return false;
        } else {
            DsonSmartDevice that = (DsonSmartDevice)o;
            if(this.getDeviceId() != null) {
                if(this.getDeviceId().equals(that.getDeviceId())) {
                    return this.getVendor() != null?this.getVendor().equals(that.getVendor()):that.getVendor() == null;
                }
            } else if(that.getDeviceId() == null) {
                return this.getVendor() != null?this.getVendor().equals(that.getVendor()):that.getVendor() == null;
            }

            return false;
        }
    }

    public int hashCode() {
        int result = this.getDeviceId() != null?this.getDeviceId().hashCode():0;
        result = 31 * result + (this.getVendor() != null?this.getVendor().hashCode():0);
        return result;
    }

    public String getDsonDeviceName() {
        return this.dsonDeviceName;
    }

    public String getDsonFloorName() {
        return this.dsonFloorName;
    }

    public String getDsonRoomName() {
        return this.dsonRoomName;
    }

    public void setDsonDeviceName(String dsonDeviceName) {
        this.dsonDeviceName = dsonDeviceName;
    }

    public void setDsonFloorName(String dsonFloorName) {
        this.dsonFloorName = dsonFloorName;
    }

    public void setDsonRoomName(String dsonRoomName) {
        this.dsonRoomName = dsonRoomName;
    }

    public String getDsonAlert() {
        return this.dsonAlert;
    }

    public void setDsonAlert(String dsonAlert) {
        this.dsonAlert = dsonAlert;
    }

    public String getDsonIRkeyList() {
        return this.dsonIRkeyList;
    }

    public void setDsonIRkeyList(String dsonIRkeyList) {
        this.dsonIRkeyList = dsonIRkeyList;
    }

    public String toString() {
        return "DsonsmartDevice{deviceType=\'" + this.deviceType + '\'' + ", deviceSubType=\'" + this.deviceSubType + '\'' + ", deviceId=\'" + this.deviceId + '\'' + ", deviceId2=\'" + this.deviceId2 + '\'' + ", deviceName=\'" + this.deviceName + '\'' + ", zoneId=\'" + this.zoneId + '\'' + ", roomId=\'" + this.roomId + '\'' + ", vendor=\'" + this.vendor + '\'' + ", model=\'" + this.model + '\'' + ", sn=\'" + this.sn + '\'' + ", pic=\'" + this.pic + '\'' + ", online=" + this.online + ", dsonDeviceName=" + this.dsonDeviceName + ", dsonFloorName=" + this.dsonFloorName + ", dsonRoomName=" + this.dsonRoomName + ", dsonAlert=" + this.dsonAlert + ", dsonIRkeyList=" + this.dsonIRkeyList + ", dsonsmartCtrlCmd=" + this.dsonsmartCtrlCmd + '}';
    }
}

