package com.dson.smart.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/4/28.
 */

public class DsonSmartRooms {


    private String vendor;
    private List<DsonSmartRoom> rooms = new ArrayList();

    public DsonSmartRooms() {
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<DsonSmartRoom> getRooms() {
        return this.rooms;
    }

    public void setRooms(List<DsonSmartRoom> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(DsonSmartRoom room) {
        this.rooms.add(room);
    }
    
}
