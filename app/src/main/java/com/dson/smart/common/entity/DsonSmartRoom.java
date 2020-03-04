package com.dson.smart.common.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/4/28.
 */

public class DsonSmartRoom implements Parcelable {

    String floorId;
    String roomId;
    String floorName;
    String roomName;

    String pic;
    String vendor;

    public List<DsonSmartDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<DsonSmartDevice> devices) {
        this.devices = devices;
    }

    List<DsonSmartDevice> devices = new ArrayList();

    public void addDevice(DsonSmartDevice device){
        devices.add(device);
    }

    public void remove(DsonSmartDevice device){
        devices.remove(device);
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }



    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public static final Creator<DsonSmartRoom> CREATOR = new Creator() {
        public DsonSmartRoom createFromParcel(Parcel source) {
            return new DsonSmartRoom(source);
        }

        public DsonSmartRoom[] newArray(int size) {
            return new DsonSmartRoom[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.floorId);
        dest.writeString(this.roomId);
        dest.writeString(this.floorName);
        dest.writeString(this.roomName);
        dest.writeString(this.pic);
        dest.writeString(this.vendor);
    }

    public DsonSmartRoom(){

    }

    protected DsonSmartRoom(Parcel in) {
        this.floorId = in.readString();
        this.roomId = in.readString();
        this.floorName = in.readString();
        this.roomName =in.readString();
        this.pic = in.readString();
        this.vendor = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DsonSmartRoom that = (DsonSmartRoom) o;

        if (!floorId.equals(that.floorId)) return false;
        return roomId.equals(that.roomId);

    }

    @Override
    public int hashCode() {
        int result = floorId.hashCode();
        result = 31 * result + roomId.hashCode();
        return result;
    }
}
