package com.dsn.andy.bgmlauncher.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dsn.andy.bgmlauncher.bean.Music;
import com.dsn.andy.bgmlauncher.util.Utils;

import java.util.ArrayList;

/**
 * Created by dell on 2017/10/2.
 */

public class MusicDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="dsn.db";//数据库名称
    private static final String TABLE_NAME = "music_history";
    private static final int SCHEMA_VERSION=1;//版本号,则是升级之后的,升级方法请看onUpgrade方法里面的判断

    Context context;

    public MusicDBHelper(Context context) {//构造函数,接收上下文作为参数,直接调用的父类的构造函数

        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//创建的是一个午餐订餐的列表,id,菜名,地址等等
        db.execSQL("CREATE TABLE music_history (_id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(20),singer varchar(10),time varchar(20),love integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {//升级判断,如果再升级就要再加两个判断,从1到3,从2到3
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+" ;");
        }
    }

    public boolean exists(Music music)
    {
        String sql = "select * from "+TABLE_NAME+" where name="+music.name+" and singer="+music.singer+";";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql,null);

        if(cursor != null && cursor.moveToFirst()){
            cursor.close();
            return true;
        }

        return false;

    }

    public long insert(Music music){
        ContentValues values = new ContentValues();
        values.put("name",music.name);
        values.put("singer",music.singer);
        values.put("time", Utils.getTimeStr(context));
        values.put("love",music.love);

        return this.getWritableDatabase().insert(TABLE_NAME,null,values);
    }

    public int update(Music music){
        ContentValues values = new ContentValues();
        values.put("time", Utils.getTimeStr(context));

        return this.getWritableDatabase().update(TABLE_NAME,values,"name=? and singer=?",new String[]{music.name,music.singer});
    }

    public ArrayList<Music> getAll(){
        ArrayList<Music> list = null;

        String sql = "select * from "+TABLE_NAME+" order by time desc;";

        Cursor cursor = this.getReadableDatabase().rawQuery(sql,null);

        if(cursor != null && cursor.moveToFirst()){
            int nameIndex = cursor.getColumnIndex("name");
            int singerIndex = cursor.getColumnIndex("singer");
            int timeIndex = cursor.getColumnIndex("time");
            int loveIndex = cursor.getColumnIndex("love");
            do{
                Music music = new Music();
                music.name = cursor.getString(nameIndex);
                music.singer = cursor.getString(singerIndex);
                music.time = cursor.getString(timeIndex);
                music.love = cursor.getInt(loveIndex);

                list.add(music);
            }while(cursor.moveToNext());

            cursor.close();
        }


        return list;
    }

}
