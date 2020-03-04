package com.dsn.andy.bgmlauncher.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/16.
 */

public class Music implements Serializable{
    public String name;
   public String singer;
    public String url;
    public String imgUrl;
    public int position;

    public String path="";//本地
    public int local;

    public String time;

    public int love;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Music music = (Music) o;

        if (!name.equals(music.name)) return false;
        if(!path.equals("") && path.equals(music.path)) return true;
        return singer.equals(music.singer);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();

        result = 31 * result + singer.hashCode()+path.hashCode();
        return result;
    }
}
