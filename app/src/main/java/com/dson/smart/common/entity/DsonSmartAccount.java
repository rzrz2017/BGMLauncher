package com.dson.smart.common.entity;

/**
 * Created by dell on 2018/3/14.
 */


public class DsonSmartAccount {
    private String name;
    private String vendor;

    public DsonSmartAccount() {
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(!(o instanceof DsonSmartAccount)) {
            return false;
        } else {
            DsonSmartAccount that = (DsonSmartAccount)o;
            return this.getVendor() != null?this.getVendor().equals(that.getVendor()):that.getVendor() == null;
        }
    }

    public int hashCode() {
        return this.getVendor() != null?this.getVendor().hashCode():0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}

