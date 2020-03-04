package com.dsn.andy.bgmlauncher.bean2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dell on 2017/10/17.
 */

public class Program implements Serializable{


     public String description;
    public int duration;
    public int id;

    public int mediainfo_id;
    public String mediainfo_file_path; //bitrate 62

    public String title;
    public String type;
    public String update_time;
    public String weburl;
    public String imgUrl;
    public String author;

    public static Program parse(JSONObject obj){
        Program program = null;
        try{
            String description = obj.getString("description");
            int id = obj.getInt("id");

            JSONObject mediainfo = obj.getJSONObject("mediainfo");
            int mediainfo_id = mediainfo.getInt("id");
            JSONArray bitrates_url = mediainfo.getJSONArray("bitrates_url");
            JSONObject bit62  = bitrates_url.getJSONObject(0);
            String file_path = bit62.getString("file_path");

            String title = obj.getString("title");
            String type = obj.getString("type");
            String update_time = obj.getString("update_time");
           // String weburl = obj.getString("weburl");
            int duration = obj.getInt("duration");

            program = new Program();
            program.description = description;
            program.id = id;
            program.mediainfo_id = mediainfo_id;
            program.title = title;
            program.type = type;
            program.update_time = update_time;
           // program.weburl = weburl;
            program.mediainfo_file_path = file_path;
            program.duration = duration;

        }catch (Exception e){
            e.printStackTrace();
        }

        return program;
    }


}
