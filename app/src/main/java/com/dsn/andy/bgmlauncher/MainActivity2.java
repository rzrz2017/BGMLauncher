package com.dsn.andy.bgmlauncher;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.andy.net.bean.PlayList;
import com.andy.net.socket.ServerSocketManager;
import com.andy.net.util.ActionRequestHandler;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.dsn.andy.bgmlauncher.bean2.Weather;
import com.dsn.andy.bgmlauncher.db2.DBUtils;
import com.dsn.andy.bgmlauncher.fragment2.DeviceListFragment;
import com.dsn.andy.bgmlauncher.fragment2.LoginFragment;
import com.dsn.andy.bgmlauncher.fragment2.MainFragment;
import com.dsn.andy.bgmlauncher.fragment2.MainPageAdapter;
import com.dsn.andy.bgmlauncher.fragment2.SceneListFragment;
import com.dsn.andy.bgmlauncher.fragment2.Smarthome485CommandFragment;
import com.dsn.andy.bgmlauncher.fragment2.Smarthome485Fragment;
import com.dsn.andy.bgmlauncher.home.AiuiReceiver;
import com.dsn.andy.bgmlauncher.home.DataBroadcastReceiver;
import com.dsn.andy.bgmlauncher.home.DsonConstants;
import com.dsn.andy.bgmlauncher.home.DsonUtils;
import com.dsn.andy.bgmlauncher.home.LoginBroadcastReceiver;
import com.dsn.andy.bgmlauncher.home.MyService;
import com.dsn.andy.bgmlauncher.home.SharedPrefrenceUtils;
import com.dsn.andy.bgmlauncher.home.SmarthomeHelper;
import com.dsn.andy.bgmlauncher.receiver.MusicReceiver;
import com.dsn.andy.bgmlauncher.serialport.PlayListener;
import com.dsn.andy.bgmlauncher.serialport.SerialPortActivity;
import com.dsn.andy.bgmlauncher.serialport.SerialportParser;
import com.dsn.andy.bgmlauncher.systemui.WifiIcons;
import com.dsn.andy.bgmlauncher.util.ServiceUtils;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.util.WeatherManager;
import com.dsn.andy.bgmlauncher.view.CommomDialog;
import com.dson.smart.common.entity.DsonSmartHostInfo;
import com.dson.support.dsonbase.DsonbaseContant;
import com.geniusgithub.mediarender.DeviceInfo;
import com.geniusgithub.mediarender.DeviceUpdateBrocastFactory;
import com.geniusgithub.mediarender.RenderApplication;
import com.geniusgithub.mediarender.center.MediaRenderProxy;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity2 extends SerialPortActivity implements DeviceUpdateBrocastFactory.IDevUpdateListener,
        LoginBroadcastReceiver.OnLoginListener, DataBroadcastReceiver.OnDataChangeListener {
    public static final boolean SN_DEBUG = true;

    public static final String ACTION_PLAYING = "action_playing_1";
    public static final String ACTION_PAUSE = "action_pause-1";
    public static final String ACTION_FIRMWARE_UPDATE = "android.intent.szhklt.DISCOVER_VER";
    public static final String ACTION_UPDATE = "action_update";
    public static final String PREFER_NAME = "com.dsn.setting";
    private final String DSLAUNCHER = "com.dsn.andy.bgmlauncher.LAUNCHER";
    private final String DSNOTLAUNCHER = "com.dsn.andy.bgmlauncher.NOTLAUNCHER";

    boolean isReady;
    String result;

    String TAG = "andy";

    //wifi
    WifiManager mWifiManager;
    boolean mWifiEnabled;
    int mWifiRssi, mWifiLevel;
    int mWifiIconId = 0;

    private MediaRenderProxy mRenderProxy;
    private RenderApplication mApplication;
    private DeviceUpdateBrocastFactory mBrocastFactory;

    AiuiReceiver aiuiReceiver;

    LoginBroadcastReceiver loginReceiver;

    MusicReceiver musicReceiver;

    /*
    新界面
     */
    ViewPager viewPager;
    MainFragment mainFragment;
    LoginFragment loginFragment;
    DeviceListFragment deviceListFragment;
    SceneListFragment sceneListFragment;
    Smarthome485CommandFragment smarthome485Fragment;

    MainPageAdapter adapter;

    Weather weather;


    @Override
    public void onInit(int code) {
        Log.e("andy", "mainactivity oninit " + code);
        if (code != DsonbaseContant.RESULT_SUCCESS) {
            DsonUtils.show(this, "初始化失败:" + code);
        } else {
//            DsonUtils.show(this, "初始化成功");

        }
    }

    @Override
    public void onGetHostInfo() {
        Log.e("andy", "onGetHostInfo");

        DsonSmartHostInfo hostInfo = DsonConstants.getInstance().getHostInfo();
        if (!DsonConstants.getInstance().hasLogin() && (hostInfo != null && hostInfo.isSupportLogin())) {

            String user = SharedPrefrenceUtils.getUsername(this);
            String pwd = SharedPrefrenceUtils.getPassword(this);

            Log.e("andy", "login user = " + user);
            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd)) { //需要登陆
                SmarthomeHelper.getInstance(this).login(user, pwd);
            }
        } else if (hostInfo != null && !hostInfo.isSupportLogin()) { //不需要远程登陆
            Log.e("andy", "host not login");
            DsonConstants.getInstance().setLogin(true);
            DsonUtils.show(this, "智能家居已启动");

            setFragmentsForHomeLogon();
        }
    }

    @Override
    public void onLogin(int code) {
        if (code == DsonbaseContant.RESULT_SUCCESS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DsonUtils.show(MainActivity2.this, "智能家居已启动");
                    DsonConstants.getInstance().setLogin(true);

                    setFragmentsForHomeLogon();
                }
            }, 1000);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity2.this, "智能家居账户登陆失败！", Toast.LENGTH_LONG).show();
                    ;
                }
            });
        }
    }


    private void initDLNAData() {
        mApplication = RenderApplication.getInstance();
        mRenderProxy = MediaRenderProxy.getInstance();
        mBrocastFactory = new DeviceUpdateBrocastFactory(this);
        updateDevInfo(mApplication.getDevInfo());
        mBrocastFactory.register(this);
    }

    @Override
    public void onUpdate() {
        updateDevInfo(mApplication.getDevInfo());
    }

    private void updateDevInfo(DeviceInfo object) {

    }

    private void unInitData() {
        mBrocastFactory.unregister();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static final String ACTION_PLAY_MUSIC = "com.dson.play_song";

    /*
    启动TCP UDP服务端，以便手机端局域网内控制音乐主机
     */
    private void initSocketServer() {
        ServerSocketManager.getInstance(this).initServer(new ActionRequestHandler(this) {

            @Override
            public void onPlayList(PlayList playList) {

                String extra = playList.getExtra();
                if (!TextUtils.isEmpty(extra)) {
                    Utils.send(MainActivity2.this, extra);
                    return;
                }

            }

            @Override
            public void onPlayMusic(String singer, String song, String from) {
                Intent it = new Intent(ACTION_PLAY_MUSIC);
                it.putExtra("type", 1);
                JSONObject json = new JSONObject();
                try {
                    json.put("singer", singer);
                    json.put("song", song);
                    json.put("player", "kw");
                    it.putExtra("data", json.toString());

                } catch (Exception ex) {

                } finally {
                    sendBroadcast(it);
                }

            }

            @Override
            public void onStartPlay() {
                sendPlayCmd(1);
            }

            @Override
            public void onStopPlay() {
                sendPlayCmd(0);
            }

            @Override
            public void onNext() {
                sendPlayCmd(3);
            }

            @Override
            public void onPrev() {
                sendPlayCmd(2);
            }

            @Override
            public void onReboot() {
                sendPlayCmd(6);
            }

            @Override
            public void onShutdown() {
                sendPlayCmd(7);
            }

            @Override
            public void onVolumeUp() {
                sendPlayCmd(5);
            }

            @Override
            public void onVolumeDown() {
                sendPlayCmd(4);
            }

            @Override
            public void log(String s) {

            }

            @Override
            public String getHostName() {
                Log.e("andy", Utils.getSerialNumber());
                String sn = Utils.getSerialNumber();
                if (TextUtils.isEmpty(sn)) {
                    return "BGM";
                } else {
                    return sn;
                }
            }
        });
    }

    /*
    给语音助手发送广播指令，以便控制播放
     */
    private void sendPlayCmd(int cmd) {
        Intent it = new Intent(ACTION_PLAY_MUSIC);
        it.putExtra("type", 2);
        it.putExtra("cmd", cmd);
        sendBroadcast(it);
    }

    /*
        给语音助手发送广播指令，以便控制播放
        带value参数扩展指令：例如控制音量具体大小
         */
    private void sendPlayCmd(int cmd, int value) {
        Intent it = new Intent(ACTION_PLAY_MUSIC);
        it.putExtra("type", 2);
        it.putExtra("cmd", cmd);
        it.putExtra("value", value);
        sendBroadcast(it);
    }

    private void initSmartHomeLoginReceiver() {
        loginReceiver = (LoginBroadcastReceiver) new LoginBroadcastReceiver(this).register();
        loginReceiver.setOnLiginListener(this);
    }

    /*
    新界面
     */
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        mainFragment = new MainFragment().setActivity(this);
        loginFragment = new LoginFragment().setActivity(this);
        deviceListFragment = new DeviceListFragment().setActivity(this);
        sceneListFragment = new SceneListFragment().setActivity(this);

        smarthome485Fragment = new Smarthome485CommandFragment().setActivity(this);
        setFragmentsForHomeLogout();
    }


    public void setFragmentsForHomeLogout() {
        if (adapter == null) {
            List<Fragment> list = new ArrayList<Fragment>();
            list.add(mainFragment);
            if (Utils.isSupportSmartHome(this)) list.add(loginFragment);
            Log.e("sss", "Utils.isSupportSmartHome485(this):" + Utils.isSupportSmartHome485(this));
            if (Utils.isSupportSmartHome485(this)) list.add(smarthome485Fragment);
            adapter = new MainPageAdapter(getSupportFragmentManager(), list);
        } else {
            adapter.clear();
            adapter.add(mainFragment);
            if (Utils.isSupportSmartHome(this)) adapter.add(loginFragment);
            if (Utils.isSupportSmartHome485(this)) adapter.add(smarthome485Fragment);
        }
        viewPager.setAdapter(adapter);

        if (homeReceiver != null) {
            homeReceiver.unregister();
            homeReceiver = null;
        }

        showWeather(weather);
    }


    DataBroadcastReceiver homeReceiver;

    private void setFragmentsForHomeLogon() {
        Log.e("andy", "set fragment fro home logon");
        adapter.clear();
        if (Utils.isSupportSmartHome(this)) {
            Log.e("ranzhen", "isSupportSmartHome");
            adapter.add(deviceListFragment = new DeviceListFragment().setActivity(this));
            adapter.add(mainFragment);
            adapter.add(sceneListFragment = new SceneListFragment().setActivity(this));
            viewPager.setCurrentItem(1);
        } else if (Utils.isSupportSmartHome485(this)) {
            Log.e("ranzhen", "isSupportSmartHome485");
            adapter.add(smarthome485Fragment = new Smarthome485CommandFragment().setActivity(this));
            viewPager.setCurrentItem(0);
        }

        viewPager.setAdapter(adapter);

        if (Utils.isSupportSmartHome(this)) {
            homeReceiver = (DataBroadcastReceiver) new DataBroadcastReceiver(this).register();
            homeReceiver.setOnDataChangeListener(this);
        }

        showWeather(weather);
    }

    /*
    以下方法为监听智能家居数据接口方法
     */
    @Override
    public void onDeviceChange() {
        if (deviceListFragment != null) {
            deviceListFragment.refresh();
        }

        Log.e("andy", "ON DEVICE CHANGE");
    }

    @Override
    public void onRoomChange() {
        if (deviceListFragment != null) {
            deviceListFragment.refresh();
        }

        Log.e("andy", "ON ROOM CHANGE");
    }

    @Override
    public void onSceneChange() {
        if (sceneListFragment != null) {
            sceneListFragment.refresh();
        }

        Log.e("andy", "ON SCENE CHANGE");
    }

    @Override
    public void onDeviceTypeChange() {
        if (deviceListFragment != null) {
            deviceListFragment.refresh();
        }
    }

    @Override
    public void onDeviceUpdate() {
        if (deviceListFragment != null) {
            deviceListFragment.onDeviceUpdate();
        }

        Log.e("andy", "ON DEVICE STATE CHANGE");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(this);


        musicReceiver = (MusicReceiver) new MusicReceiver(this,mOutputStream).register();

        initSmartHomeLoginReceiver();

        //启动MyService，供智能家居插件APP绑定
        startService(new Intent(this, MyService.class));

        clearCacheFile();
        initDLNAData();
        initViews();
        initWifi();
        register();
        startService(new Intent(this, UpdateService.class));


        //启动DLNA
        mRenderProxy.startEngine();

        //序列化操作
        doServialOpertaion();

        startVoiceAssistant();
    }

    private void startVoiceAssistant(){
        Intent intent = new Intent();
        intent.setPackage("com.iflytek.cyber.iot.show.core");
        intent.setAction("android.intent.action.START_HK_SERVICE");
        startService(intent);
    }

    private void doServialOpertaion() {
        handler.sendEmptyMessageDelayed(0, 1000 * 60);//每一分钟刷新时间
        //设置AUXIN参数
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.setAuxin(MainActivity2.this);
                }
            }, 1000);
        } catch (Exception e) {
        }


        //开启智能家居
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mWifiEnabled)
                    ServiceUtils.checkAndStartSmartSDK(MainActivity2.this);
            }
        }, 4000);

        //启动Socket服务
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initSocketServer();
            }
        }, 500);
    }

    /*
    删除收音测试SD卡缓存
     */
    void clearCacheFile() {
        new Thread() {
            public void run() {

                File dir = new File(Environment.getExternalStorageDirectory() + "/DragonFire/");
                if (dir != null && dir.exists()) {
                    Utils.deleteDir(dir);
                }
            }
        }.start();
    }

    void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ACTION_UPDATE);
        filter.addAction(ACTION_FIRMWARE_UPDATE);
        filter.addAction("com.szhklt.ds.MUSIC");
        this.registerReceiver(receiver, filter);

        aiuiReceiver = new AiuiReceiver();

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.intent.msg.AIUITEXT");
        this.registerReceiver(aiuiReceiver, filter1);
    }


    @Override
    public void onBackPressed() {
        return;
    }


    /*
    获取当前WIFI状态，并更新图标
     */
    void initWifi() {
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mWifiEnabled = (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);

        if (mWifiEnabled) {
            mWifiRssi = mWifiManager.getConnectionInfo().getRssi();
            mWifiLevel = WifiManager.calculateSignalLevel(
                    mWifiRssi, WifiIcons.WIFI_LEVEL_COUNT);
        }
        updateWifiIcons();
        refreshViews();

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("DSN===", "action===" + action);
            if (action.equals(WifiManager.RSSI_CHANGED_ACTION)
                    || action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)
                    ) {
                boolean last = mWifiEnabled;
                if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    mWifiEnabled = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                            WifiManager.WIFI_STATE_UNKNOWN) == WifiManager.WIFI_STATE_ENABLED;
                    if (!last && mWifiEnabled) {
                        ServiceUtils.checkAndStartSmartSDK(MainActivity2.this);
                    }

                }
                try {
                    updateWifiState(intent);
                    refreshViews();

                    //获取定位
                    if (mWifiEnabled && locationService != null && action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                        locationService.start();
                    }

                } catch (Exception ex) {
                    Utils.pw(ex.getMessage());
                }
            } else if (action.equals(ACTION_UPDATE)) {
                showUpdateDialog();
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour < 8 || hour > 18) {
                    return;
                }
            } else if (action.equals(ACTION_FIRMWARE_UPDATE)) {
                //showUpdateDialog(2);
            } else if (intent.getAction().equals("com.szhklt.ds.MUSIC")) {
                result = intent.getStringExtra("count");
                Log.e("andy", "action,result=" + result);
                try {
                    JSONObject json = new JSONObject(result);
                    String img = json.getString("imageurl");
                    String name = json.getString("name");
                    boolean isPlaying = json.getBoolean("ispaly");

                    //1.在对应的fragment里去更新UI
                    //updatePlaying(name, img, isPlaying);
                    mainFragment.updatePlaying(name, img, isPlaying);
                } catch (Exception e) {

                }
            }
        }
    };

    CommomDialog updateDialog;

    /**
     * 升级提示
     */
    private void showUpdateDialog() {
        //弹出提示框
        if (updateDialog != null && updateDialog.isShowing()) {
            return;
        }
        updateDialog = new CommomDialog(this, R.style.dialog, "软件有新版本可升级啦！", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    Intent it = new Intent(MainActivity2.this, UpdateActivity.class);
                    startActivity(it);
                    dialog.dismiss();
                }
            }
        })
                .setTitle("提示");
        updateDialog.show();
    }

//    CommomDialog wificonnectDialog;

    /**
     * wifi开启提示
     */
    public void showWificonnectDialog() {
        //弹出提示框
//        if (wificonnectDialog != null && wificonnectDialog.isShowing()) {
//            return;
//        }
//        wificonnectDialog = new CommomDialog(this, R.style.dialog, "您的wifi尚连接,请连接wifi进行体验!", new CommomDialog.OnCloseListener() {
//            @Override
//            public void onClick(Dialog dialog, boolean confirm) {
//                if (confirm) {
//                    Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                    startActivity(it);
//                    dialog.dismiss();
//                }
//            }
//        }).setTitle("提示").setNegativeButton("暂不连接").setPositiveButton("去连接");
//        wificonnectDialog.show();
    }

    int mLastWifiIconId;

    void refreshViews() {
        if (mWifiEnabled) {
            //弹出提示框
//            if (wificonnectDialog != null && wificonnectDialog.isShowing()) {
//                wificonnectDialog.dismiss();
//            }

        } else {
            showWificonnectDialog();
        }
        // the wifi icon on phones
        if (mLastWifiIconId != mWifiIconId && mWifiIconId != 0) {
            mLastWifiIconId = mWifiIconId;
            //2.在对应的fragment里去更新UI
            //wifiImg.setImageResource(mWifiIconId);
            mainFragment.setWifiImg(mWifiIconId);
        }
    }

    private void updateWifiState(Intent intent) {
        final String action = intent.getAction();
        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            mWifiEnabled = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN) == WifiManager.WIFI_STATE_ENABLED;

        } else if (action.equals(WifiManager.RSSI_CHANGED_ACTION)) {
            mWifiRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200);
            mWifiLevel = WifiManager.calculateSignalLevel(
                    mWifiRssi, WifiIcons.WIFI_LEVEL_COUNT);
        }
        updateWifiIcons();
    }

    private void updateWifiIcons() {
        if (mWifiEnabled) {
//            Log.e("andy", "wifiLevel=" + mWifiLevel);
            if (mWifiLevel < 0) {
                mWifiLevel = 0;
            } else if (mWifiLevel >= WifiIcons.WIFI_SIGNAL_STRENGTH[0].length) {
                mWifiLevel = WifiIcons.WIFI_SIGNAL_STRENGTH[0].length - 1;
            }
//            Log.e("andy", "wifiLevel2=" + mWifiLevel + ",len=" + WifiIcons.WIFI_SIGNAL_STRENGTH[0].length);
            mWifiIconId = WifiIcons.WIFI_SIGNAL_STRENGTH[0][mWifiLevel];
        } else {
            mWifiIconId = R.drawable.stat_sys_wifi_signal_null;

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //3.在对应的fragment里去更新UI
            // time.setText(Utils.getTime(MainActivity2.this));
            mainFragment.setTimeDate();
            handler.sendEmptyMessageDelayed(0, 1000 * 60);

            Calendar calendar = Calendar.getInstance();
            int minute = calendar.get(Calendar.MINUTE);
            if (minute == 0) {
                locationService.start();
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (homeReceiver != null) {
            homeReceiver.unregister();
            homeReceiver = null;
        }

        ServerSocketManager.getInstance(this).exitServer();

        handler.removeMessages(0);
        mRenderProxy.stopEngine();
        unInitData();
        this.unregisterReceiver(receiver);
        this.unregisterReceiver(aiuiReceiver);
        loginReceiver.unregister();
        musicReceiver.unregister();
        DBUtils utils = DBUtils.getInstance(this);
        utils.close();
        stopService(new Intent(this, UpdateService.class));
    }


    void broascast(String action) {
        sendBroadcast(new Intent(action));
    }

    @Override
    protected void onResume() {
        super.onResume();
        broascast(DSLAUNCHER);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        broascast(DSNOTLAUNCHER);
    }


    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        Log.e("www","onDataReceived()");
        Log.e("www","size:"+size);
        Log.e("www","buffer:"+bytesToHexFun1(buffer));
        if (buffer != null && size > 0) {
            wakeup();
        }
        SerialportParser.getInstance(this).init(new PlayListener() {
            @Override
            public void onPlayOrPause(boolean play) {
                sendPlayCmd(play ? 1 : 0);
            }

            @Override
            public void onNextOrPrev(boolean next) {
                sendPlayCmd(next ? 3 : 2);
                respondMsg(next ? new byte[]{0x23}:new byte[]{0x22});
            }

            @Override
            public void onVolumeUpOrDown(boolean up) {
                sendPlayCmd(up ? 5 : 4);
            }

            @Override
            public void onShutdown() {
                sendPlayCmd(7);
                respondMsg(new byte[]{0x27});
            }

            @Override
            public void onReboot() {
                sendPlayCmd(6);
                respondMsg(new byte[]{0x28});
            }

            @Override
            public void onVolumeSet(byte percent) {
                sendPlayCmd(8, percent);
            }

            @Override
            public void onPlayMode(int mode) {
                sendPlayCmd(9, mode);
                if("0x01".equals(mode)){//单曲播放
                    respondMsg(new byte[]{0x01});
                }else if("0x02".equals(mode)){//顺序播放
                    respondMsg(new byte[]{0x02});
                }else if("0x03".equals(mode)){//随机播放
                    respondMsg(new byte[]{0x03});
                }else if("0x04".equals(mode)){//循环播放
                    respondMsg(new byte[]{0x04});
                }
            }

            @Override
            public void onUp() {

            }

            @Override
            public void onDown() {

            }

            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {
            }

            @Override
            public void onExtSrcOrLocSrc(byte rat) {
                if("0x2e".equals(rat)){
                    sendPlayCmd(11);
                    respondMsg(new byte[]{rat});
                }else if("0x2f".equals(rat)){
                    sendPlayCmd(12);
                    respondMsg(new byte[]{rat});
                }else if("0x30".equals(rat)){//蓝牙
                    sendPlayCmd(13);
                    respondMsg(new byte[]{rat});
                }
            }

            @Override
            public void onRead(byte[] buffer, int size) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    sb.append(Utils.toHexString(buffer[i]) + " ");
                }
                Log.e("andy", "COM READ:" + sb.toString());
                Utils.pw(Utils.getTimeStr(MainActivity2.this) + ":" + sb.toString());
            }
        }).parse(buffer, size);
    }

    /*
    电源管理，保证睡眠状态下CPU运转
     */

    /*
  唤醒睡眠,点亮屏幕，唤醒CPU
   */
    private void wakeup() {
        //获取电源管理器对象
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        @SuppressLint("InvalidWakeLockTag") final PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wl.release();
            }
        }, 1000);
    }

    /**
     * 向485回复信息
     * @param bytes
     */
    private void respondMsg(byte[] bytes){
        try {
            mOutputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    百度地图
     */

    LocationService locationService;
    private boolean hasRequestLocation;//保证只在onStart里问询一次定位

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((DSNApplication) getApplication()).locationService;
        // 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        // 注册监听
        int type = getIntent().getIntExtra("from", 0);
        Log.e("andy", "from=" + type);
        if (type == 0) {
            locationService.setLocationOption(locationService
                    .getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }

        if (mWifiEnabled && !hasRequestLocation) {
            locationService.start();
            hasRequestLocation = true;
        }

        if (smarthome485Fragment != null) {
            smarthome485Fragment.initData();
        }

    }

    public void requestWeather() {
        if (locationService != null) {
            locationService.start();
        }

    }

    /*****
     *
     *      定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location
                    && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);

                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                final String city = location.getCity();
                sb.append(city);

                if (city != null) {
                    WeatherManager.request(location.getCity(), new WeatherManager.GetWeatherCallback() {
                        @Override
                        public void onGetWeather(final String type, final String temp, final String wind) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (weather == null) {
                                        weather = new Weather();
                                    }
                                    weather.setTemp(temp);
                                    weather.setType(type);
                                    weather.setWind(wind);
                                    weather.setCity(city);

                                    showWeather(weather);
                                }
                            });
                        }
                    });
                }

                if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\nerror=-111: ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\nerror=-222: ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\nerror=-333: ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                Log.e("andy", "sb=" + sb.toString());
                locationService.stop();
            }

        }
    };

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); // 注销掉监听
        locationService.stop(); // 停止定位服务
        super.onStop();
    }


    private void showWeather(Weather weather) {

        if (mainFragment != null && weather != null) {
            mainFragment.showWeather(weather);
        }
    }

    public static String bytesToHexFun1(byte[] bytes){
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for(byte b: bytes){
            if(b < 0){
                a = 256 + b;
            }else{
                a = b;
            }
            buf[index++] = HEX_CHAR[a/16];
            buf[index++] = HEX_CHAR[a%16];
        }
        return new String(buf);
    }
    private static final char[] HEX_CHAR = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

}
