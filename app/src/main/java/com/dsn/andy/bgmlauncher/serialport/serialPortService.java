package com.dsn.andy.bgmlauncher.serialport;

import android.app.Service;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.OpenLogUtil;
import com.dsn.andy.bgmlauncher.DSNApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

public class serialPortService extends Service {
    private static final String TAG = "serialPortService";
    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;

    private Handler handler;

    public serialPortService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        Log.e(TAG,"onCreate()");
        super.onCreate();
        DSNApplication application = (DSNApplication)getApplication();
        try {
            mSerialPort = application.getSerialPort();
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy()");
        if(mReadThread != null){
            mReadThread.interrupt();
        }
        closeSerialPort();
        mSerialPort = null;
        super.onDestroy();
    }

    /**
     * 关闭串口
     */
    private void closeSerialPort() {
        if(mSerialPort != null){
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    private class ReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()){
                Log.e(TAG,"进入ReadThread线程死循环");
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if(mInputStream == null){
                        return;
                    }
                    Log.e(TAG,"read()函数上一行");
                    size = mInputStream.read(buffer);
                    Log.e(TAG,"read()函数下一行");
                    Log.e(TAG,"size:"+size);
                    if(size > 0){
                        Data485Dispose(buffer,size);
                    }
                } catch (IOException e) {
                    Log.e(TAG,"IO出错：e："+e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    private void Data485Dispose(final byte[] buffer,final int size){
        Log.e(TAG,"Data485Dispose()");
        Log.e("485",new String(buffer, 0, size));
        Log.e(TAG,new String(buffer, 0, size));
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(),new String(buffer, 0, size),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
