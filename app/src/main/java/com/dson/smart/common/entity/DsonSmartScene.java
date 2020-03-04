package com.dson.smart.common.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartScene implements Parcelable {
    String sceneId;
    String sceneName;
    String sceneNo;
    String groupId;
    String roomId;
    String pic;
    private String vendor;
    public static final Creator<DsonSmartScene> CREATOR = new Creator() {
        public DsonSmartScene createFromParcel(Parcel source) {
            return new DsonSmartScene(source);
        }

        public DsonSmartScene[] newArray(int size) {
            return new DsonSmartScene[size];
        }
    };

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /** @deprecated */
    @Deprecated
    public String getSceneId() {
        return this.sceneId;
    }

    /** @deprecated */
    @Deprecated
    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneName() {
        return this.sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getSceneNo() {
        return this.sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public String toString() {
        return "JdsmartScene{sceneId=\'" + this.sceneId + '\'' + ", sceneName=\'" + this.sceneName + '\'' + ", sceneNo=\'" + this.sceneNo + '\'' + ", groupId=\'" + this.groupId + '\'' + ", roomId=\'" + this.roomId + '\'' + ", pic=\'" + this.pic + '\'' + ", vendor=\'" + this.vendor + '\'' + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sceneId);
        dest.writeString(this.sceneName);
        dest.writeString(this.sceneNo);
        dest.writeString(this.groupId);
        dest.writeString(this.roomId);
        dest.writeString(this.pic);
        dest.writeString(this.vendor);
    }

    public DsonSmartScene() {
    }

    protected DsonSmartScene(Parcel in) {
        this.sceneId = in.readString();
        this.sceneName = in.readString();
        this.sceneNo = in.readString();
        this.groupId = in.readString();
        this.roomId = in.readString();
        this.pic = in.readString();
        this.vendor = in.readString();
    }
}

