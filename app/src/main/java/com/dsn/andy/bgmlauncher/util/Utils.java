package com.dsn.andy.bgmlauncher.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.auxin.MyJNI;
import com.dsn.andy.bgmlauncher.home.SharedPrefrenceUtils;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/8/21.
 */

public class Utils {

    public static String AUXIN_KEY = "auxin";
    public static  final int SHOW_SCREEN=1000001;
    public static  final int DISMISS_SCREEN=1000002;


    public static  String toHexString(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }

    public static String toHexString(byte[] buffer){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<buffer.length;i++){
            sb.append(Utils.toHexString(buffer[i]));
        }
        return sb.toString();
    }

    public static byte[] toByteBuffer(String hexStr) throws Exception{
        if(hexStr.length() % 2 != 0){
            throw new Exception("16进制字符串长度必须为2的整数倍");
        }

        int len = hexStr.length()/2;
        byte[] buffer = new byte[len];

        for (int i=0;i<hexStr.length();i+=2)
        {
            String byteStr = hexStr.substring(i,i+2);

            try {
                byte b = (byte) (Integer.parseInt(byteStr, 16) & 0xff);
                buffer[i/2] = b;
            }catch (Exception e){
                throw new Exception("不合法的16进制字符串");
            }


        }

        return buffer;

    }

    public static void toast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void execLinuxCommand(String cmd){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process localProcess = runtime.exec(cmd);
            OutputStream localOutputStream = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
            localDataOutputStream.writeBytes(cmd);
            localDataOutputStream.flush();
        } catch (IOException e) {

        }
    }

    public static void setAuxin(Context context){
        execLinuxCommand("chmod 777 /dev/*");

        int key = getInt(context,AUXIN_KEY);//从SharedPreferences里读取音源参数

        if(key == 1){
            MyJNI.setAuxin('i');
        }else if(key == 0){
            MyJNI.setAuxin('o');
        }


    }

    public static void  send(Context context,String text) {
        Intent intent = new Intent();
        intent.putExtra("count", text);
        intent.setAction("com.szhklt.service.MainService");
        context.sendBroadcast(intent);
    }


public static void putInt(Context context,String key,int value){
    SharedPreferences sharedPreferences = context.getSharedPreferences("dsn", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(key, value);
    editor.commit();//同步提交到磁盘文件，因而会出现阻塞等的现象，如果要确保提交成功，尽量使用commit
}

public static int getInt(Context context,String key){
    SharedPreferences sharedPreferences = context.getSharedPreferences("dsn", MODE_PRIVATE);
    return sharedPreferences.getInt(key,0);
}

public static void startAPP(Context context,String packageName){
    try {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }catch (Exception ex){

    }
}



    public static  int getVersion(Context context) throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        return  packInfo.versionCode;
    }
    /*
    获取当前日期 星期
     */
    public static String getDateStr(Context context){
        Calendar calendar = Calendar.getInstance();
        StringBuffer timeSb = new StringBuffer();


        timeSb.append(make2(calendar.get(Calendar.MONTH)+1));
        timeSb.append("月");
        timeSb.append(make2(calendar.get(Calendar.DAY_OF_MONTH)));
        timeSb.append("日 ");


        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String[] weekStr = {"周日","周一","周二","周三","周四","周五","周六"};
        timeSb.append(" "+weekStr[week-1]);

        return timeSb.toString();
    }

    public static String getTimeStr(Context context){
        Calendar calendar = Calendar.getInstance();
        StringBuffer timeSb = new StringBuffer();

        timeSb.append(calendar.get(Calendar.YEAR));
        timeSb.append("/");
        timeSb.append(make2(calendar.get(Calendar.MONTH)+1));
        timeSb.append("/");
        timeSb.append(make2(calendar.get(Calendar.DAY_OF_MONTH)));

        timeSb.append(" ");


        timeSb.append(make2(calendar.get(Calendar.HOUR_OF_DAY))+":"+make2(calendar.get(Calendar.MINUTE)));


        return timeSb.toString();

    }

    /*
    设置是否支持智能家居
     */
    public static void setSupportSmartHome(Context context,boolean support){
        SharedPrefrenceUtils.writeSp(context,"sm_enable",""+support);
    }


    /*
    是否支持智能家居
     */
    public static  boolean isSupportSmartHome(Context context){
        String smEnable = SharedPrefrenceUtils.readSp(context,"sm_enable");
        if(smEnable==null){
            smEnable = "true";
        }
        if("true".equals(smEnable)) {
            return true;
        }

        return false;
    }

    /*
    设置是否支持485智能家居
     */
    public static void setSupportSmartHome485(Context context,boolean support){
        SharedPrefrenceUtils.writeSp(context,"sm485_enable",""+support);
    }


    /*
    是否支持485智能家居
     */
    public static  boolean isSupportSmartHome485(Context context){
        String sm485Enable = SharedPrefrenceUtils.readSp(context,"sm485_enable");
        if(sm485Enable==null){
            sm485Enable = "true";
        }
        if("true".equals(sm485Enable)) {
            return true;
        }

        return false;
    }


    public static String getTime(Context context){
        Calendar calendar = Calendar.getInstance();
//        LunarCalendar lunarCalendar = new LunarCalendar(calendar);
        StringBuffer timeSb = new StringBuffer();

//        timeSb.append(calendar.get(Calendar.YEAR));
//        timeSb.append("/");
//        timeSb.append(calendar.get(Calendar.MONTH)+1);
//        timeSb.append("/");
//        timeSb.append(calendar.get(Calendar.DAY_OF_MONTH));
//
//        timeSb.append(" 农历");
//        timeSb.append(lunarCalendar.getLunarMonth()+"月");
//        timeSb.append(lunarCalendar.getLunarDate()+" ");

        timeSb.append(make2(calendar.get(Calendar.HOUR_OF_DAY))+":"+make2(calendar.get(Calendar.MINUTE)));


        return timeSb.toString();

    }

    static String make2(int i){
        if(i<10) return "0"+i;
        else {
            return i+"";
        }
    }

    public static boolean isAudioFile(File file){
        String name = file.getName().toLowerCase();
        if(name.endsWith("mp3")
              ||  name.endsWith("wav")
                || name.endsWith("ogg")
                || name.endsWith("ape")
                || name.endsWith("flac")
                ){
            return true;
        }

        return false;
    }


    public static void pw(String s)
    {
        try {
            File dir = new File("/mnt/sdcard/dsn");
            if(!dir.exists()){
                dir.mkdirs();
            }


            File f = new File("/mnt/sdcard/dsn/dsn_log.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            //第二个参数意义是说是否以append方式添加内容
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            String info = s+"\r\n";
            bw.write(info);

            bw.flush();
            bw.close();
        }catch (Exception e){

        }
    }

    public static void setMusic(Context context,boolean mute){
//        final AudioManager audioManager=(AudioManager)context.getSystemService(Service.AUDIO_SERVICE);
//        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, mute);
    }

    /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    public  static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
//递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void clearSpeechMemory(){

        File sdcard = new File("/mnt/sdcard");
        File[] files = sdcard.listFiles();
        for(File f:files){
            if(f.getName().startsWith("CAE")  ){
                Utils.deleteDir(f);
            }
        }

    }

    /**
     * 获取本机SN序列号demo
     *
     * @return
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     *判断某一程序是否运行
     *
     *
     * @param context 上下文
     * @param packageName  包名
     * @return  true 表达正在运行    false  表达未运行
     */
    public static boolean isAppRunning(Context context,String packageName){
        ActivityManager am=(ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list=am.getRunningTasks(100);
        if(list.size()<=0){
           return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    //获取指定包名的uid
    public static int getPackageUid(Context context,String packageName){
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(packageName, 0);
            if(ai!=null){
            return ai.uid;
            }
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return -1;
    }


    /**
     * 判断某一 uid 的程序是否有正在运行的进程，即是否存活
     *
     * @param context     上下文
     * @param uid 已安装应用的 uid
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isProcessRunning(Context context, int uid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() > 0) {
            for (ActivityManager.RunningServiceInfo appProcess : runningServiceInfos) {
                if (uid == appProcess.uid) {
                    return true;
                }
            }
        }
        return false;

    }
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 对网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isOpenNetwork(Context ctx) {
        ConnectivityManager connManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

 /*   private ArrayList<DSMedia> mediaslist;
    public  void search(final Music music) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mobilecdn.kugou.com/")
                .build();

        SearchMusicListService service = retrofit.create(SearchMusicListService.class);
        Call<ResponseBody> call = service.getMusicList(1, music.name, 1, 30);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                Utils.pw("response=" + response.isSuccess());
                Song ss = null;
                try {
                    String str = response.body().string();
                    JSONObject obj = new JSONObject(str);
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray info = data.getJSONArray("info");
                    for (int i = 0; i < info.length(); i++) {
                        JSONObject s = info.getJSONObject(i);
                        Song song = Song.parse(s);
                        if (song.filename.contains(music.name)
                                && song.filename.contains(music.singer)
                                ) {
                            ss = song;

                            break;
                        }
                    }
                    if(ss == null){
                        for (int i = 0; i < info.length(); i++) {
                            JSONObject s = info.getJSONObject(i);
                            Song song = Song.parse(s);
                            if (song.filename.contains(music.name)
                                    ) {
                                ss = song;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                }
                if (ss != null)
                    getSongInfo(ss);
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
    Music music;
    void getSongInfo(final Song song) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://m.kugou.com")
                .build();
        GetSongInfoService service = retrofit.create(GetSongInfoService.class);
        Call<ResponseBody> call = service.getMusicInfo(song.hash, "playInfo");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    final String str = response.body().string();
                    song.fillSongInfo(new JSONObject(str));
                    Utils.pw(song.url);
                    DSMedia m = new DSMedia();
                    m.setname(song.songName);
                    m.setauther(song.singername);
                    m.seturl(song.url);
                    m.setimgurl(song.imgUrl);
                    m.setdetile("");
                    mediaslist.add(m);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("andy", "get song info," + e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
*/
}
