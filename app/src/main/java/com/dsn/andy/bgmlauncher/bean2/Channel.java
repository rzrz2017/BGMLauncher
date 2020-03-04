package com.dsn.andy.bgmlauncher.bean2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dell on 2017/10/17.
 */

public class Channel implements Serializable{


    public int category_id;
    public String description;//频道介绍
    public int id;
    public String latest_program;
    public String playcount; //播放次数

    public String podcasters_desc;//作者介绍
    public int podcasters_fan_num;//粉丝数量
    public String podcasters_img_url;//头像
    public String podcasters_name;//作者名称

    public int program_count; //节目数量
    public String thumb_large;//频道图片
    public String thumb_medium;

    public String title;//频道名称
    public String type;
    public String update_time;//更新时间
    public String weburl;

    public static Channel parse(JSONObject json) {
        Channel c = null;
        try {
            int categroy_id = json.getInt("category_id");
            String description = json.getString("description");
            int id = json.getInt("id");
            String latest_program = json.getString("latest_program");
            String playcount = json.getString("playcount");
            String podcasters_desc = null;
            int podcasters_fan_num =0;
            String podcasters_img_url = null;
            String podcasters_name =null;
            try {
                JSONArray podcasters_array = json.getJSONArray("podcasters");
                JSONObject podcasters = podcasters_array.getJSONObject(0);
                 podcasters_desc = podcasters.getString("desc");
                 podcasters_fan_num = podcasters.getInt("fan_num");
                 podcasters_img_url = podcasters.getString("img_url");
                 podcasters_name = podcasters.getString("name");
            }catch (Exception e){

            }

            int program_count = json.getInt("program_count");

            JSONObject thumb = json.getJSONObject("thumbs");
            String thumb_large = thumb.getString("400_thumb");
            String thumb_medium = thumb.getString("200_thumb");
            String title = json.getString("title");
            String type = json.getString("type");
            String update_time = json.getString("update_time");
           // String weburl = json.getString("weburl");

            c = new Channel();
            c.category_id = categroy_id;
            c.description = description;
            c.id = id;
            c.latest_program = latest_program;
            c.playcount = playcount;
            c.podcasters_desc = podcasters_desc;
            c.podcasters_fan_num = podcasters_fan_num;
            c.podcasters_img_url = podcasters_img_url;
            c.podcasters_name = podcasters_name;

            c.program_count = program_count;
            c.thumb_large = thumb_large;
            c.thumb_medium = thumb_medium;
            c.title = title;
            c.type = type;
            c.update_time = update_time;
           // c.weburl = weburl;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }


}
