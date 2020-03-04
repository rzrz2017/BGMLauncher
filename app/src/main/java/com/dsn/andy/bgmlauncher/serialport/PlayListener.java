package com.dsn.andy.bgmlauncher.serialport;

/**
 * Created by dell on 2018/8/9.
 */

public interface PlayListener {


    void onPlayOrPause(boolean play);
    void onNextOrPrev(boolean next);
    void onVolumeUpOrDown(boolean up);

    void onVolumeSet(byte percent);


    void onShutdown();
    void onReboot();
    void onPlayMode(int mode);

    void onUp();
    void onDown();
    void onLeft();
    void onRight();


    void onRead(byte[] buffer,int size);

    /**
     *
     * @param rat 56:外部音源  57:本地音源
     */
    void onExtSrcOrLocSrc(byte rat);

}
