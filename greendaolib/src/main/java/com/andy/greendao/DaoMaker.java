package com.andy.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoMaker {
    public static void main(String[] args) {

        Schema schema = new Schema(13, "com.andy.music");
        addMusic(schema);

        addProgram(schema);

        addKeySearch(schema);

        /*
        485 智能家居
         */
        addCOMDevice(schema);
        addCOMMand(schema);

        schema.setDefaultJavaPackageDao("com.andy.greendao.dao");
        try {

            new DaoGenerator().generateAll(schema, "G:\\dson\\BGMLauncher-SMARTHOME-COMMON-UDP\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    485串口 智能家居设备
     */
    public static void addCOMDevice(Schema schema){
        Entity entity = schema.addEntity("COMDevice");

        entity.addIdProperty();
        entity.addStringProperty("deviceName");
        entity.addIntProperty("deviceType");
    }

    /*
    485串口命令
     */

    public static void addCOMMand(Schema schema){
        Entity entity = schema.addEntity("COMCommand");
        entity.addIdProperty();
        entity.addStringProperty("commandName");
        entity.addByteArrayProperty("commandBytes");
        entity.addLongProperty("deviceId");
    }



    /**
     *
     *
     * @param schema
     */
    public static void addMusic(Schema schema) {

        Entity entity = schema.addEntity("Music_1");

        entity.addIdProperty();

        entity.addStringProperty("name");

        entity.addStringProperty("singer");

        entity.addStringProperty("time");

        entity.addBooleanProperty("is_love");

        entity.addStringProperty("url");
        entity.addStringProperty("imgUrl");
        entity.addBooleanProperty("local");
        entity.addStringProperty("path");
    }

    public static void addProgram(Schema schema){

        Entity entity = schema.addEntity("Program_1");
        entity.addIdProperty();
        entity.addStringProperty("title");
        entity.addStringProperty("url");
        entity.addStringProperty("img_url");
        entity.addStringProperty("time");

        entity.addBooleanProperty("is_love");

    }

    public static void addKeySearch(Schema schema){
        Entity entity = schema.addEntity("Key_1");
        entity.addIdProperty();
        entity.addStringProperty("key");
        entity.addStringProperty("time");
    }

}
