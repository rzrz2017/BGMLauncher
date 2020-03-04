package com.dson.smart.common.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/3/14.
 */

public class DsonSmartScenes {
    private String vendor;
    private List<DsonSmartScene> scenes = new ArrayList();

    public DsonSmartScenes() {
    }

    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<DsonSmartScene> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<DsonSmartScene> scenes) {
        this.scenes = scenes;
    }

    public void addScene(DsonSmartScene scene) {
        this.scenes.add(scene);
    }
}