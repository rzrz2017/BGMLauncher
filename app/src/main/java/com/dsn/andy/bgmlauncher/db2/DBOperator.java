package com.dsn.andy.bgmlauncher.db2;

import android.content.Context;

import java.util.List;

/**
 * Created by dell on 2018/10/2.
 */

public class DBOperator<T> {

    private Context context;
    private static DaoManager daoManager;

    public  DBOperator(Context context){
        this.context = context;
        if(daoManager == null) {
            daoManager = DaoManager.getInstance();
            daoManager.initManager(context.getApplicationContext());
        }
    }

    public long insert(T t){
        return daoManager.getDaoSession().insert(t);
    }

    public boolean delete(T t){
        try{
            daoManager.getDaoSession().delete(t);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public boolean update(T t){
        try{
            daoManager.getDaoSession().update(t);
            return true;
        }catch (Exception e){

        }
        return false;
    }


    public List<T> query(T t,String where,String[] args){
        if(where == null) where = "";
        return (List<T>) daoManager.getDaoSession().queryRaw(t.getClass(),where,args);
    }

    public void deleteAll(T t,String where,String[] args){
        List<T> all = query(t,where,args);

        for(T t1:all)
        daoManager.getDaoSession().delete(t1);
    }



}
