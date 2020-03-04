package com.dson.smart.common.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartSensorRecordGroup {
    private List<DsonSmartSensorRecord> mDsonSmartSensorRecords;
    private String groupName;

    public DsonSmartSensorRecordGroup() {
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JSONField(
            name = "mDsonSmartSensorRecords"
    )
    public List<DsonSmartSensorRecord> getDsonSmartSensorRecords() {
        return this.mDsonSmartSensorRecords;
    }

    /** @deprecated */
    @JSONField(
            name = "mDsonSmartSensorRecords"
    )
    public List<DsonSmartSensorRecord> getmDsonSmartSensorRecords() {
        return this.mDsonSmartSensorRecords;
    }

    public void setDsonSmartSensorRecords(List<DsonSmartSensorRecord> mDsonSmartSensorRecords) {
        this.mDsonSmartSensorRecords = mDsonSmartSensorRecords;
    }

    /** @deprecated */
    public void setmDsonSmartSensorRecords(List<DsonSmartSensorRecord> mDsonSmartSensorRecords) {
        this.mDsonSmartSensorRecords = mDsonSmartSensorRecords;
    }
}

