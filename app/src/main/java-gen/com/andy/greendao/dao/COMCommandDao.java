package com.andy.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.andy.music.COMCommand;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMCOMMAND".
*/
public class COMCommandDao extends AbstractDao<COMCommand, Long> {

    public static final String TABLENAME = "COMCOMMAND";

    /**
     * Properties of entity COMCommand.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CommandName = new Property(1, String.class, "commandName", false, "COMMAND_NAME");
        public final static Property CommandBytes = new Property(2, byte[].class, "commandBytes", false, "COMMAND_BYTES");
        public final static Property DeviceId = new Property(3, Long.class, "deviceId", false, "DEVICE_ID");
    };


    public COMCommandDao(DaoConfig config) {
        super(config);
    }
    
    public COMCommandDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMCOMMAND\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"COMMAND_NAME\" TEXT," + // 1: commandName
                "\"COMMAND_BYTES\" BLOB," + // 2: commandBytes
                "\"DEVICE_ID\" INTEGER);"); // 3: deviceId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMCOMMAND\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, COMCommand entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String commandName = entity.getCommandName();
        if (commandName != null) {
            stmt.bindString(2, commandName);
        }
 
        byte[] commandBytes = entity.getCommandBytes();
        if (commandBytes != null) {
            stmt.bindBlob(3, commandBytes);
        }
 
        Long deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindLong(4, deviceId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public COMCommand readEntity(Cursor cursor, int offset) {
        COMCommand entity = new COMCommand( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // commandName
            cursor.isNull(offset + 2) ? null : cursor.getBlob(offset + 2), // commandBytes
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // deviceId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, COMCommand entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCommandName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCommandBytes(cursor.isNull(offset + 2) ? null : cursor.getBlob(offset + 2));
        entity.setDeviceId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(COMCommand entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(COMCommand entity) {
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
