package com.dson.smart.common.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dell on 2018/3/14.
 */

public class DsonSmartSceneBind implements Parcelable {
    private String sceneNo;
    private List<DsonSmartCtrlCmd> cmdList = new ArrayList();
    private HashMap<String, DsonSmartCtrlCmd> cmdMap = new HashMap();
    public static final Creator<DsonSmartSceneBind> CREATOR = new Creator() {
        public DsonSmartSceneBind createFromParcel(Parcel source) {
            return new DsonSmartSceneBind(source);
        }

        public DsonSmartSceneBind[] newArray(int size) {
            return new DsonSmartSceneBind[size];
        }
    };

    public DsonSmartSceneBind() {
    }

    public String getSceneNo() {
        return this.sceneNo;
    }

    public List<DsonSmartCtrlCmd> getCmdList() {
        return this.cmdList;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public void setCmdList(List<DsonSmartCtrlCmd> cmdList) {
        this.cmdList = cmdList;
        Iterator var2 = cmdList.iterator();

        while(var2.hasNext()) {
            DsonSmartCtrlCmd cmd = (DsonSmartCtrlCmd)var2.next();
            this.cmdMap.put(cmd.getDeviceId(), cmd);
        }

    }

    public void removeCmd(DsonSmartCtrlCmd cmd) {
        if(this.cmdList.contains(cmd)) {
            this.cmdList.remove(cmd);
        }

    }

    public void addCmd(DsonSmartCtrlCmd cmd) {
        DsonSmartCtrlCmd localCmd = (DsonSmartCtrlCmd)this.cmdMap.get(cmd.getDeviceId());
        if(localCmd == null) {
            this.cmdMap.put(cmd.getDeviceId(), cmd);
            this.cmdList.add(cmd);
        }

    }

    public void updateCmd(DsonSmartCtrlCmd cmd) {
        DsonSmartCtrlCmd localCmd = (DsonSmartCtrlCmd)this.cmdMap.get(cmd.getDeviceId());
        if(localCmd != null) {
            localCmd.setCtrlCmd(cmd);
        } else {
            this.cmdMap.put(cmd.getDeviceId(), cmd);
            this.cmdList.add(cmd);
        }

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sceneNo);
        dest.writeList(this.cmdList);
        dest.writeMap(this.cmdMap);
    }

    protected DsonSmartSceneBind(Parcel in) {
        this.sceneNo = in.readString();
        this.cmdList = new ArrayList();
        in.readList(this.cmdList, this.getClass().getClassLoader());
        this.cmdMap = in.readHashMap(HashMap.class.getClassLoader());
    }
}

