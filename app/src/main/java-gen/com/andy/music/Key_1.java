package com.andy.music;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "KEY_1".
 */
public class Key_1 {

    private Long id;
    private String key;
    private String time;

    public Key_1() {
    }

    public Key_1(Long id) {
        this.id = id;
    }

    public Key_1(Long id, String key, String time) {
        this.id = id;
        this.key = key;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
