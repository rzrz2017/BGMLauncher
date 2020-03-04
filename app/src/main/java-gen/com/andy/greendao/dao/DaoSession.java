package com.andy.greendao.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.andy.music.Music_1;
import com.andy.music.Program_1;
import com.andy.music.Key_1;
import com.andy.music.COMDevice;
import com.andy.music.COMCommand;

import com.andy.greendao.dao.Music_1Dao;
import com.andy.greendao.dao.Program_1Dao;
import com.andy.greendao.dao.Key_1Dao;
import com.andy.greendao.dao.COMDeviceDao;
import com.andy.greendao.dao.COMCommandDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig music_1DaoConfig;
    private final DaoConfig program_1DaoConfig;
    private final DaoConfig key_1DaoConfig;
    private final DaoConfig cOMDeviceDaoConfig;
    private final DaoConfig cOMCommandDaoConfig;

    private final Music_1Dao music_1Dao;
    private final Program_1Dao program_1Dao;
    private final Key_1Dao key_1Dao;
    private final COMDeviceDao cOMDeviceDao;
    private final COMCommandDao cOMCommandDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        music_1DaoConfig = daoConfigMap.get(Music_1Dao.class).clone();
        music_1DaoConfig.initIdentityScope(type);

        program_1DaoConfig = daoConfigMap.get(Program_1Dao.class).clone();
        program_1DaoConfig.initIdentityScope(type);

        key_1DaoConfig = daoConfigMap.get(Key_1Dao.class).clone();
        key_1DaoConfig.initIdentityScope(type);

        cOMDeviceDaoConfig = daoConfigMap.get(COMDeviceDao.class).clone();
        cOMDeviceDaoConfig.initIdentityScope(type);

        cOMCommandDaoConfig = daoConfigMap.get(COMCommandDao.class).clone();
        cOMCommandDaoConfig.initIdentityScope(type);

        music_1Dao = new Music_1Dao(music_1DaoConfig, this);
        program_1Dao = new Program_1Dao(program_1DaoConfig, this);
        key_1Dao = new Key_1Dao(key_1DaoConfig, this);
        cOMDeviceDao = new COMDeviceDao(cOMDeviceDaoConfig, this);
        cOMCommandDao = new COMCommandDao(cOMCommandDaoConfig, this);

        registerDao(Music_1.class, music_1Dao);
        registerDao(Program_1.class, program_1Dao);
        registerDao(Key_1.class, key_1Dao);
        registerDao(COMDevice.class, cOMDeviceDao);
        registerDao(COMCommand.class, cOMCommandDao);
    }
    
    public void clear() {
        music_1DaoConfig.getIdentityScope().clear();
        program_1DaoConfig.getIdentityScope().clear();
        key_1DaoConfig.getIdentityScope().clear();
        cOMDeviceDaoConfig.getIdentityScope().clear();
        cOMCommandDaoConfig.getIdentityScope().clear();
    }

    public Music_1Dao getMusic_1Dao() {
        return music_1Dao;
    }

    public Program_1Dao getProgram_1Dao() {
        return program_1Dao;
    }

    public Key_1Dao getKey_1Dao() {
        return key_1Dao;
    }

    public COMDeviceDao getCOMDeviceDao() {
        return cOMDeviceDao;
    }

    public COMCommandDao getCOMCommandDao() {
        return cOMCommandDao;
    }

}
