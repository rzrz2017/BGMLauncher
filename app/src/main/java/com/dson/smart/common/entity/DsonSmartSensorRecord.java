package com.dson.smart.common.entity;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartSensorRecord {
    private String vender;
    private String statusRecordId;
    private String uid;
    private String deviceId;
    private int sequence;
    private int value1;
    private int value2;
    private int value3;
    private int value4;
    private String createTime;
    private String updateTime;
    private int delFlag;
    private String text;

    public DsonSmartSensorRecord() {
    }

    public String getVendor() {
        return this.vender;
    }

    /** @deprecated */
    public String getVender() {
        return this.vender;
    }

    public void setVendor(String vendor) {
        this.vender = vendor;
    }

    /** @deprecated */
    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getStatusRecordId() {
        return this.statusRecordId;
    }

    public void setStatusRecordId(String statusRecordId) {
        this.statusRecordId = statusRecordId;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getValue1() {
        return this.value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return this.value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getValue3() {
        return this.value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    public int getValue4() {
        return this.value4;
    }

    public void setValue4(int value4) {
        this.value4 = value4;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

