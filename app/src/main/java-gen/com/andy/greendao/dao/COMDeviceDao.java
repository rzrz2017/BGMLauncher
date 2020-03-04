package com.andy.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.andy.music.COMDevice;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMDEVICE".
*/
public class COMDeviceDao extends AbstractDao<COMDevice, Long> {

    public static final String TABLENAME = "COMDEVICE";

    /**
     * Properties of entity COMDevice.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property DeviceName = new Property(1, String.class, "deviceName", false, "DEVICE_NAME");
        public final static Property DeviceType = new Property(2, Integer.class, "deviceType", false, "DEVICE_TYPE");
    };


    public COMDeviceDao(DaoConfig config) {
        super(config);
    }
    
    public COMDeviceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMDEVICE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DEVICE_NAME\" TEXT," + // 1: deviceName
                "\"DEVICE_TYPE\" INTEGER);"); // 2: deviceType
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMDEVICE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, COMDevice entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(2, deviceName);
        }
 
        Integer deviceType = entity.getDeviceType();
        if (deviceType != null) {
            stmt.bindLong(3, deviceType);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public COMDevice readEntity(Cursor cursor, int offset) {
        COMDevice entity = new COMDevice( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // deviceName
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2) // deviceType
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, COMDevice entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDeviceName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDeviceType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(COMDevice entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(COMDevice entity) {
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
