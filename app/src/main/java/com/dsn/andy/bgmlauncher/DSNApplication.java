package com.dsn.andy.bgmlauncher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.dsn.andy.bgmlauncher.home.SharedPrefrenceUtils;
import com.dsn.andy.bgmlauncher.serialport.SerialPortFinder;
import com.dsn.andy.bgmlauncher.serialport.serialPortService;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.geniusgithub.mediarender.RenderApplication;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;


/**
 * Created by dell on 2017/9/22.
 */

public class DSNApplication extends RenderApplication {

    public static DSNApplication app;

    static int playingFrom; //1-普通音乐 2-讯飞音乐 3-电台,4-酷我

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;

    public LocationService locationService;

    @Override
    public void onCreate() {
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        StringBuffer param = new StringBuffer();
        param.append("appid="+getString(R.string.app_id));
        param.append(",");
       // param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
        param.append("server_url=http://dz-szhklt.xf-yun.com/msp.do");

       // SpeechUtility.createUtility(this, param.toString());


        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);


        /*
        百度地图
         */
        locationService = new LocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());

        /*
        第一次默认支持无线智能家居，不支持485
         */
        String smEnable = SharedPrefrenceUtils.readSp(this,"sm_enable");
        if(smEnable == null){
            Utils.setSupportSmartHome(this,true);
            Utils.setSupportSmartHome485(this,false);
        }


        super.onCreate();

        //启动串口读取线程
        Intent intent = new Intent(this,serialPortService.class);
        startService(intent);

        app = this;
    }


    public static DSNApplication getInstance(){
        return app;
    }




    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
			/* Read serial port parameters */
            SharedPreferences sp = getSharedPreferences("android_serialport_api.sample_preferences", MODE_PRIVATE);
            String path = sp.getString("DEVICE", "/dev/ttyS3");
            int baudrate = Integer.decode(sp.getString("BAUDRATE", "9600"));

			/* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

			/* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
            Log.e("andy","COM serialport create,path="+path+",baudrate:"+baudrate);
        }
        return mSerialPort;
    }

    /*
    是否支持485智能家居
     */
    public boolean isSupport485(){
        try {
            getSerialPort();
        }catch (IOException ex){

        }

        return (mSerialPort!=null);
    }

    public boolean sendSerialPortData(byte[] buffer){
        try{
            mSerialPort = getSerialPort();
            OutputStream mOutputStream = mSerialPort.getOutputStream();
            mOutputStream.write(buffer);
            Log.e("andy","485 send command over "+ Utils.toHexString(buffer));
            return true;
        }catch (IOException e){
            return false;
        }
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

}
