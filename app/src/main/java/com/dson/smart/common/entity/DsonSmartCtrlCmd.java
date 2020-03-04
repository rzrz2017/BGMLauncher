package com.dson.smart.common.entity;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartCtrlCmd {
    private String vendor;
    private String uid;
    private String sceneNO;
    private String deviceId;
    private String order;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String groupData;
    int delayTime;
    private String sceneBindId;

    public DsonSmartCtrlCmd() {
    }

    public String getSceneBindId() {
        return this.sceneBindId;
    }

    public void setSceneBindId(String sceneBindId) {
        this.sceneBindId = sceneBindId;
    }

    public int getDelayTime() {
        return this.delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getValue1() {
        return this.value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return this.value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return this.value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return this.value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getGroupData() {
        return this.groupData;
    }

    public void setGroupData(String groupData) {
        this.groupData = groupData;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSceneNO() {
        return this.sceneNO;
    }

    public void setSceneNO(String sceneNO) {
        this.sceneNO = sceneNO;
    }

    public void setCtrlCmd(DsonSmartCtrlCmd newCmd) {
        this.sceneNO = newCmd.getSceneNO();
        this.delayTime = newCmd.getDelayTime();
        this.deviceId = newCmd.getDeviceId();
        this.order = newCmd.getOrder();
        this.sceneBindId = newCmd.getSceneBindId();
        this.uid = newCmd.getUid();
        this.vendor = newCmd.getVendor();
        this.value1 = newCmd.getValue1();
        this.value2 = newCmd.getValue2();
        this.value3 = newCmd.getValue3();
        this.value4 = newCmd.getValue4();
    }

    public String toString() {
        return "packageInfo{vendor=\'" + this.vendor + '\'' + ", uid=\'" + this.uid + '\'' + ", sceneNO=\'" + this.sceneNO + '\'' + ", deviceId=\'" + this.deviceId + '\'' + ", order=\'" + this.order + '\'' + ", value1=\'" + this.value1 + '\'' + ", value2=\'" + this.value2 + '\'' + ", value3=\'" + this.value3 + '\'' + ", value4=\'" + this.value4 + '\'' + ", delayTime=" + this.delayTime + ", sceneBindId=\'" + this.sceneBindId + '\'' + '}';
    }
}

