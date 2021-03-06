package com.andy.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.andy.music.Music_1;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MUSIC_1".
*/
public class Music_1Dao extends AbstractDao<Music_1, Long> {

    public static final String TABLENAME = "MUSIC_1";

    /**
     * Properties of entity Music_1.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Singer = new Property(2, String.class, "singer", false, "SINGER");
        public final static Property Time = new Property(3, String.class, "time", false, "TIME");
        public final static Property Is_love = new Property(4, Boolean.class, "is_love", false, "IS_LOVE");
        public final static Property Url = new Property(5, String.class, "url", false, "URL");
        public final static Property ImgUrl = new Property(6, String.class, "imgUrl", false, "IMG_URL");
        public final static Property Local = new Property(7, Boolean.class, "local", false, "LOCAL");
        public final static Property Path = new Property(8, String.class, "path", false, "PATH");
    };


    public Music_1Dao(DaoConfig config) {
        super(config);
    }
    
    public Music_1Dao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MUSIC_1\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"SINGER\" TEXT," + // 2: singer
                "\"TIME\" TEXT," + // 3: time
                "\"IS_LOVE\" INTEGER," + // 4: is_love
                "\"URL\" TEXT," + // 5: url
                "\"IMG_URL\" TEXT," + // 6: imgUrl
                "\"LOCAL\" INTEGER," + // 7: local
                "\"PATH\" TEXT);"); // 8: path
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MUSIC_1\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Music_1 entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String singer = entity.getSinger();
        if (singer != null) {
            stmt.bindString(3, singer);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(4, time);
        }
 
        Boolean is_love = entity.getIs_love();
        if (is_love != null) {
            stmt.bindLong(5, is_love ? 1L: 0L);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(6, url);
        }
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(7, imgUrl);
        }
 
        Boolean local = entity.getLocal();
        if (local != null) {
            stmt.bindLong(8, local ? 1L: 0L);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(9, path);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Music_1 readEntity(Cursor cursor, int offset) {
        Music_1 entity = new Music_1( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // singer
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0, // is_love
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // url
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // imgUrl
            cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0, // local
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // path
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Music_1 entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSinger(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIs_love(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
        entity.setUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setImgUrl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLocal(cursor.isNull(offset + 7) ? null : cursor.getShort(offset + 7) != 0);
        entity.setPath(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Music_1 entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Music_1 entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
