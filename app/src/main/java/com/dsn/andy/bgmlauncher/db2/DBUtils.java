package com.dsn.andy.bgmlauncher.db2;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.andy.greendao.dao.COMCommandDao;
import com.andy.greendao.dao.Key_1Dao;
import com.andy.greendao.dao.Music_1Dao;
import com.andy.greendao.dao.Program_1Dao;
import com.andy.music.COMCommand;
import com.andy.music.COMDevice;
import com.andy.music.Key_1;
import com.andy.music.Music_1;
import com.andy.music.Program_1;
import com.dsn.andy.bgmlauncher.util.Utils;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 完成对某一张表的具体操作
 * Created by LGL on 2016/7/2.
 */
public class DBUtils {

    //TAG
    private static final String TAG = "andy";

    private static DBUtils instance;

    private DaoManager daoManager;

    private DBUtils(Context context){
        daoManager = DaoManager.getInstance();
        daoManager.initManager(context);
    }

    public synchronized static DBUtils getInstance(Context context) {
        if(instance == null) {
            instance = new DBUtils(context);
        }

        return instance;
    }

    public void close(){
        daoManager.closeConnection();
    }

    /**
     * 对数据库中music表的插入操作
     *
     * @param music
     * @return
     */
    public boolean insertMusic_1(Music_1 music) {
        if(isExist(music)){
             return true;
        }
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(music) != -1 ? true : false;
        return flag;
    }

    public boolean insertKeySearch(Key_1 key){
        if(isExistKey(key)){
            updateKey_1(key);
            return true;
        }
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(key) != -1 ? true : false;
        return flag;
    }



    public boolean insertProgram_1(Program_1 program){
        if(isExistProgram(program)){
            return true;
        }
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(program) != -1 ? true : false;
        return flag;
    }

    /**
     * 批量插入
     *
     * @param musics
     * @return
     */
    public boolean inserMultMusic_1(final List<Music_1> musics) {
        //标识
        boolean flag = false;
        try {
            //插入操作耗时
            daoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Music_1 music : musics) {
                        daoManager.getDaoSession().insertOrReplace(music);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改
     *
     * @param music
     * @return
     */
    public boolean updateMusic_1(Music_1 music) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(music);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean updateKey_1(Key_1 key) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(key);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean updateProgram_1(Program_1 program) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(program);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param music
     * @return
     */
    public boolean deleteMusic_1(Music_1 music) {
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(music);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }

    public boolean clearMusic(){
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().deleteAll(Music_1.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean clearFM(){
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().deleteAll(Program_1.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteKey(Key_1 key) {
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(key);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }

    /**
     * 查询单条
     *
     * @param key
     * @return
     */
    public Music_1 listOneMusic_1(long key) {
        return daoManager.getDaoSession().load(Music_1.class, key);
    }

    /**
     * 全部查询
     *
     * @return
     */
    public List<Music_1> listAll() {
        return daoManager.getDaoSession().loadAll(Music_1.class);
    }

    public List<Key_1> listAllKeySearch() {
        String where = "order by "+ Key_1Dao.Properties.Time.columnName+" desc";
        //使用sql进行查询
        List<Key_1> list = daoManager.getDaoSession().queryRaw(Key_1.class,where,new String[]{});
        Log.i(TAG, list + "");

        return list;
    }

    /**
     * 原生查询
     */
    public void queryNative() {
        //查询条件
        String where = "where name like ? and _id > ?";
        //使用sql进行查询
        List<Music_1> list = daoManager.getDaoSession().queryRaw(Music_1.class, where,
                new String[]{"%l%", "6"});
        Log.i(TAG, list + "");
    }

    public boolean isExistKey(Key_1 key){
        String where = "where KEY=?";
        //使用sql进行查询
        List<Key_1> list = daoManager.getDaoSession().queryRaw(Key_1.class, where,
                new String[]{key.getKey()});

        if(list != null && list.size()>0){
            key.setId(list.get(0).getId());
            return true;
        }else {
            return false;
        }
    }


    /*
    是否已存在
     */
    public boolean isLove(Music_1 music){
        if(!TextUtils.isEmpty(music.getSinger())){
            String where = "where NAME=? and SINGER=?";
            //使用sql进行查询
            List<Music_1> list = daoManager.getDaoSession().queryRaw(Music_1.class, where,
                    new String[]{music.getName(),music.getSinger()});

            if(list != null && list.size()>0){
                music.setId(list.get(0).getId());
                return list.get(0).getIs_love();
            }else {
                return false;
            }
        }else if(!TextUtils.isEmpty(music.getPath())){

            String where = "where PATH=?";
            //使用sql进行查询
            List<Music_1> list = daoManager.getDaoSession().queryRaw(Music_1.class, where,
                    new String[]{music.getPath()});

            if(list != null && list.size()>0){
                music.setId(list.get(0).getId());
                return list.get(0).getIs_love();
            }else{
                return false;
            }

        }

        return false;
    }

    /*
    是否已存在
     */
    public boolean isExist(Music_1 music){
        if(!TextUtils.isEmpty(music.getSinger())){
            String where = "where NAME=? and SINGER=?";
            //使用sql进行查询
            List<Music_1> list = daoManager.getDaoSession().queryRaw(Music_1.class, where,
                    new String[]{music.getName(),music.getSinger()});

            if(list != null && list.size()>0){
                music.setId(list.get(0).getId());
                return true;
            }else {
                return false;
            }
        }else if(!TextUtils.isEmpty(music.getPath())){

             String where = "where PATH=?";
            //使用sql进行查询
            List<Music_1> list = daoManager.getDaoSession().queryRaw(Music_1.class, where,
                    new String[]{music.getPath()});

            if(list != null && list.size()>0){
                music.setId(list.get(0).getId());
                return true;
            }else{
                return false;
            }

        }

        return false;
    }

    public boolean isLove(Program_1 program){

        String where = "where URL=?";
        //使用sql进行查询
        List<Program_1> list = daoManager.getDaoSession().queryRaw(Program_1.class, where,
                new String[]{program.getUrl()});

        if(list != null && list.size()>0){
           Program_1 p = list.get(0);
            program.setId(p.getId());
            Utils.pw("size="+list.size()+",url="+p.getUrl()+",islove="+p.getIs_love());
            return p.getIs_love().booleanValue();
        }else{
            return false;
        }
    }

    public boolean isExistProgram(Program_1 program){

        String where = "where URL=?";
        //使用sql进行查询
        List<Program_1> list = daoManager.getDaoSession().queryRaw(Program_1.class, where,
                new String[]{program.getUrl()});

        if(list != null && list.size()>0){
            program.setId(list.get(0).getId());
            return true;
        }else{
            return false;
        }
    }



    /**
     * 查询歌曲收藏
     */
    public List<Music_1> queryLove() {
        //查询条件
        String where = "where IS_LOVE=1 order by "+ Music_1Dao.Properties.Time.columnName+" desc";
        //使用sql进行查询
        List<Music_1> list = daoManager.getDaoSession().queryRaw(Music_1.class,where,new String[]{});
        Log.i(TAG, list + "");

        return list;
    }

    /**
     * 查询最近播放
     */
    public List<Music_1> queryHistory() {
        //查询条件
        String where = "order by "+ Music_1Dao.Properties.Time.columnName+" desc";
        //使用sql进行查询
        List<Music_1> list = daoManager.getDaoSession().queryRaw(Music_1.class,where,new String[]{});
        Log.i(TAG, list + "");

        return list;
    }

    /**
     * 查询最近播放
     */
    public List<Program_1> queryHistoryProgram() {
        //查询条件
        String where = "order by "+ Program_1Dao.Properties.Time.columnName+" desc";
        //使用sql进行查询
        List<Program_1> list = daoManager.getDaoSession().queryRaw(Program_1.class,where,new String[]{});
        Log.i(TAG, list + "");

        return list;
    }

    /**
     * 查询最近播放
     */
    public List<Program_1> queryLoveProgram() {
        //查询条件
        String where ="where "+ Program_1Dao.Properties.Is_love.columnName+"=1 order by "+ Program_1Dao.Properties.Time.columnName+" desc";
        //使用sql进行查询
        List<Program_1> list = daoManager.getDaoSession().queryRaw(Program_1.class,where,new String[]{});
        Log.i(TAG, list + "");

        return list;
    }



    /**
     * QueryBuilder查询大于
     */
    public void queryBuilder() {
        //查询构建器
        QueryBuilder<Music_1> queryBuilder = daoManager.getDaoSession().queryBuilder(Music_1.class);
        //查询年龄大于19的北京
//        List<Music_1> list = queryBuilder.where(Music_1Dao.Properties.Age.ge(19)).where(Music_1Dao.Properties.Address.like("北京")).list();
//        Log.i(TAG, list + "");
    }


    /*
    485智能家居操作

     */

    /*
    获取485智能家居设备列表
     */
    public List<COMDevice> queryAllCOMDevice(){
        return daoManager.getDaoSession().queryRaw(COMDevice.class,null);
    }

    /*
    获取某个设备下的命令列表
     */
    public List<COMCommand> queryCommand(COMDevice device){
        if(device == null) return null;
        long deviceId = device.getId();

        String where = "where "+COMCommandDao.Properties.DeviceId.columnName+"="+deviceId;
        return daoManager.getDaoSession().queryRaw(COMCommand.class,where);
    }

    /*
    插入一条485智能家居设备记录
    返回新记录的ID
     */

    public long insertCOMDevice(COMDevice device){
        return daoManager.getDaoSession().insert(device);
    }

    /*
    修改设备
     */
    public boolean updateCOMDevice(COMDevice device){
        try {
            daoManager.getDaoSession().update(device);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    /*
    删除设备
     */
    public boolean deleteCOMDevice(COMDevice device){
        try {
            daoManager.getDaoSession().delete(device);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    /*
    插入一条485智能家居设备命令的记录
    返回新纪录的ID
     */
    public long insertCOMCommand(COMCommand command){
        return daoManager.getDaoSession().insert(command);
    }

    /*
    修改设备命令
     */
    public boolean updateCOMCommand(COMCommand command){
        try{
            daoManager.getDaoSession().update(command);
            return true;
        }catch (Exception e){

        }
        return false;
    }


    /*
    删除设备命令
     */
    public boolean deleteCOMCommand(COMCommand command){
        try{
            daoManager.getDaoSession().delete(command);
            return true;
        }catch (Exception e){

        }
        return false;
    }












}
