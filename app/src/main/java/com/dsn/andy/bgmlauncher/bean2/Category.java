package com.dsn.andy.bgmlauncher.bean2;

import org.json.JSONObject;

/**
 * Created by dell on 2017/10/17.
 */

public class Category {

    public int id;
    public String name;
    public int section_id;
    public int sequence;
    public String type;

    public static Category parse(JSONObject obj){
        Category category = null;
        try{
            int id = obj.getInt("id");
            String name = obj.getString("name");
            int section_id = obj.getInt("section_id");
            int sequence = obj.getInt("sequence");
            String type = obj.getString("type");

            category = new Category();
            category.id = id;
            category.name = name;
            category.section_id = section_id;
            category.sequence = sequence;
            category.type = type;
        }catch (Exception e){

        }

        return category;
    }


}
