package com.dsn.andy.bgmlauncher.auxin;

/**
 * Created by dell on 2018/1/12.
 */

public class MyJNI {

    static{
        System.loadLibrary("auxin");
    }

    /*
    mode:'i','o'
     */
    public static native int setAuxin(char mode);
    public static native int getAuxin();
}
