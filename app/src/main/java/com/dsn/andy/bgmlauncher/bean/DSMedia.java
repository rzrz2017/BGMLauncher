package com.dsn.andy.bgmlauncher.bean;

/**
 * Created by Administrator on 2018/4/4.
 */

public class DSMedia {
    private int postion;
    private String name,auther,url,imgurl,detile;

    public DSMedia(){

    }
    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDetile() {
        return detile;
    }

    public void setDetile(String detile) {
        this.detile = detile;
    }
}
