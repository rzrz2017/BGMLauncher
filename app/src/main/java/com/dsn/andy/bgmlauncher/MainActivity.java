package com.dsn.andy.bgmlauncher;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.net.bean.PlayList;
import com.andy.net.socket.ServerSocketManager;
import com.andy.net.util.ActionRequestHandler;
import com.dsn.andy.bgmlauncher.bean.Album;
import com.dsn.andy.bgmlauncher.db2.DBUtils;
import com.dsn.andy.bgmlauncher.home.AiuiReceiver;
import com.dsn.andy.bgmlauncher.home.DsonConstants;
import com.dsn.andy.bgmlauncher.home.DsonUtils;
import com.dsn.andy.bgmlauncher.home.LoginBroadcastReceiver;
import com.dsn.andy.bgmlauncher.home.MyService;
import com.dsn.andy.bgmlauncher.home.SharedPrefrenceUtils;
import com.dsn.andy.bgmlauncher.home.SmarthomeHelper;
import com.dsn.andy.bgmlauncher.receiver.MusicReceiver;
import com.dsn.andy.bgmlauncher.recycleview.GalleryAdapter;
import com.dsn.andy.bgmlauncher.recycleview.MyRecyclerView;
import com.dsn.andy.bgmlauncher.serialport.PlayListener;
import com.dsn.andy.bgmlauncher.serialport.SerialPortActivity;
import com.dsn.andy.bgmlauncher.serialport.SerialportParser;
import com.dsn.andy.bgmlauncher.systemui.WifiIcons;
import com.dsn.andy.bgmlauncher.util.DataUtils;
import com.dsn.andy.bgmlauncher.util.FileUtil;
import com.dsn.andy.bgmlauncher.util.ServiceUtils;
import com.dsn.andy.bgmlauncher.util.SpaceItemDecoration;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CircleTransform;
import com.dsn.andy.bgmlauncher.view.CommomDialog;
import com.dsn.andy.bgmlauncher.view.MarqueeTextView;
import com.dson.smart.common.entity.DsonSmartHostInfo;
import com.dson.support.dsonbase.DsonbaseContant;
import com.geniusgithub.mediarender.DeviceInfo;
import com.geniusgithub.mediarender.DeviceUpdateBrocastFactory;
import com.geniusgithub.mediarender.RenderApplication;
import com.geniusgithub.mediarender.center.MediaRenderProxy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends SerialPortActivity implements View.OnClickListener, DeviceUpdateBrocastFactory.IDevUpdateListener, LoginBroadcastReceiver.OnLoginListener {
    public static final boolean SN_DEBUG = true;

    public static final String ACTION_PLAYING = "action_playing_1";
    public static final String ACTION_PAUSE = "action_pause-1";
    public static final String ACTION_FIRMWARE_UPDATE = "android.intent.szhklt.DISCOVER_VER";
    public static final String ACTION_UPDATE = "action_update";
    public static final String PREFER_NAME = "com.dsn.setting";
    private final String DSLAUNCHER = "com.dsn.andy.bgmlauncher.LAUNCHER";
    private final String DSNOTLAUNCHER = "com.dsn.andy.bgmlauncher.NOTLAUNCHER";
    MyRecyclerView recyclerView;
    TextView time;
    Button searchBtn;
    Button accountBtn;
    Button kwBtn;
    ImageView wifiImg;
    ImageView playingImg;
    MarqueeTextView playingSong;
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


    @Override
    public void onInit(int code) {
        Log.e("andy","mainactivity oninit "+code);
        if (code != DsonbaseContant.RESULT_SUCCESS) {
            DsonUtils.show(this, "初始化失败:" + code);
        } else {
//            DsonUtils.show(this, "初始化成功");

        }
    }

    @Override
    public void onGetHostInfo() {
        Log.e("andy","onGetHostInfo");

        DsonSmartHostInfo hostInfo = DsonConstants.getInstance().getHostInfo();
        if (!DsonConstants.getInstance().hasLogin() && (hostInfo != null && hostInfo.isSupportLogin())) {
            String user = SharedPrefrenceUtils.getUsername(this);
            String pwd = SharedPrefrenceUtils.getPassword(this);
            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pwd)) {
                SmarthomeHelper.getInstance(this).login(user, pwd);
            }
        } else if (hostInfo != null && !hostInfo.isSupportLogin()) {
            Log.e("andy","host not login");
            DsonConstants.getInstance().setLogin(true);
            DsonUtils.show(MainActivity.this, "智能家居已启动");
        }
    }

    @Override
    public void onLogin(int code) {
        if (code == DsonbaseContant.RESULT_SUCCESS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DsonUtils.show(MainActivity.this, "智能家居已启动");
                    DsonConstants.getInstance().setLogin(true);
                }
            }, 1000);
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

    private static final String ACTION_PLAY_MUSIC = "com.dson.play_song";

    private void initSocketServer() {
        ServerSocketManager.getInstance(this).initServer(new ActionRequestHandler(this) {

            @Override
            public void onPlayList(PlayList playList) {

                String extra = playList.getExtra();
                if (!TextUtils.isEmpty(extra)) {
                    Utils.send(MainActivity.this, extra);
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
                if(TextUtils.isEmpty(sn)){
                    return "BGM";
                }else{
                    return sn;
                }
            }
        });
    }

    private void sendPlayCmd(int cmd) {
        Intent it = new Intent(ACTION_PLAY_MUSIC);
        it.putExtra("type", 2);
        it.putExtra("cmd", cmd);
        sendBroadcast(it);
    }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }

    private void doServialOpertaion() {
        //设置AUXIN参数
        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.setAuxin(MainActivity.this);
                }
            }, 1000);
        } catch (Exception e) {
        }


        //开启智能家居
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mWifiEnabled)
                ServiceUtils.checkAndStartSmartSDK(MainActivity.this);
            }
        }, 2000);

        //启动Socket服务
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initSocketServer();
            }
        }, 500);
    }

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


    private String getImageName(String imgUrl) {
        String name = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.lastIndexOf("?") == -1 ? imgUrl.length() : imgUrl.lastIndexOf("?"));
        return name;
    }

    private void setImageState(Bitmap bm, boolean isPlaying) {
        playingImg.setImageBitmap(new CircleTransform().transform(bm));

        if (isPlaying) {
            playing();
        } else {
            pause();
        }
    }

    void loadImg(final String imgUrl, final boolean isPlaying) {
        String imageName = getImageName(imgUrl);
        File file = new File("/mnt/sdcard/dsn/",
                imageName);
        if (file.exists() && file.length() > 1000 * 10) {

            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            setImageState(bm, isPlaying);

            return;
        } else if (file.exists() && file.length() == 0) {
            file.delete();
        }
        //Target
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String imageName = getImageName(imgUrl);
                File file = FileUtil
                        .getDCIMFile(FileUtil.PATH_PHOTOGRAPH, imageName);
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, ostream);

                    Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                    setImageState(bm, isPlaying);

                } catch (Exception e) {

                } finally {
                    try {
                        if (ostream != null) {
                            ostream.close();
                        }
                    } catch (Exception e) {

                    }

                }


            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("andy", "on bitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("andy", "onPrepredlaod");

            }
        };

        //Picasso下载
        Picasso.with(this).load(imgUrl).into(target);
        playingImg.setTag(target);
    }


    private boolean isValidImgUrl(String imgUrl) {
        if (imgUrl == null || TextUtils.isEmpty(imgUrl) || imgUrl.contains("{size}") || !imgUrl.startsWith("http")) {
            return false;
        }
        return true;
    }

    private void updatePlaying(String name, String img, boolean playing) {
        playingSong.setText(name);
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_playing);

        setImageState(bm, playing);

        if (isValidImgUrl(img)) {
            loadImg(img, playing);
        }

    }

    private void pause() {
        if (rotate != null) {
            rotate.cancel();
            rotate = null;
        }

        playingSong.setScroll(false);

    }

    private void playing() {
        if (rotate != null) {
            rotate.cancel();
            rotate = null;
        }

        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setFillAfter(true);
        rotate.setRepeatCount(Integer.MAX_VALUE);               //设置重复次数
        rotate.setRepeatMode(Animation.RESTART);    //重新从头执行
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(1000 * 20);

        playingImg.startAnimation(rotate);

        playingSong.setScroll(true);
    }

    RotateAnimation rotate;

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
                if(action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
                    boolean last = mWifiEnabled;
                    mWifiEnabled = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                            WifiManager.WIFI_STATE_UNKNOWN) == WifiManager.WIFI_STATE_ENABLED;
                    if(!last && mWifiEnabled){
                        ServiceUtils.checkAndStartSmartSDK(MainActivity.this);
                    }

                }
                try {
                    updateWifiState(intent);
                    refreshViews();
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

                    updatePlaying(name, img, isPlaying);
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
                    Intent it = new Intent(MainActivity.this, UpdateActivity.class);
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
            wifiImg.setImageResource(mWifiIconId);
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

    Handler
            handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            time.setText(Utils.getTime(MainActivity.this));
            handler.sendEmptyMessageDelayed(0, 1000 * 60);
        }
    };

    private void initViews() {
        playingImg = (ImageView) findViewById(R.id.playing_img);

        playingSong = (MarqueeTextView) findViewById(R.id.song);
        playingSong.setTextColor(getResources().getColor(R.color.default_color));
        playingSong.setTextSize(getResources().getDimensionPixelSize(R.dimen.nav_text_size));
        playingSong.setTextSpeed(1.0F);

        time = (TextView) findViewById(R.id.time);
        time.setText(Utils.getTime(this));
        handler.sendEmptyMessageDelayed(0, 1000 * 60);


        searchBtn = (Button) findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(this);

        accountBtn = (Button) findViewById(R.id.btn_me);
        accountBtn.setOnClickListener(this);

        kwBtn = (Button) findViewById(R.id.btn_kw);
        kwBtn.setOnClickListener(this);

        wifiImg = (ImageView) findViewById(R.id.wifi_img);


        recyclerView = (MyRecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        final List<Album> data = DataUtils.makeData();
        GalleryAdapter adapter = new GalleryAdapter(this, data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        //RecycleView 增加边距
        int spacingInPixels = this.getResources().getDimensionPixelOffset(R.dimen.home_recycelerview_margin);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        adapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Album album = data.get(position % data.size());
                Intent it = new Intent(MainActivity.this, MusicListActivity.class);
                it.putExtra("album", album);
                startActivity(it);
            }
        });

        Button tool = (Button) findViewById(R.id.btn_tool);
        if (tool != null) {
            tool.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ToolActivity.class));
                }
            });
        }

        Button online = (Button) findViewById(R.id.btn_internet);
        if (online != null) {
            online.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, OnlineMusicActivity.class));
                }
            });
        }

        Button radio = (Button) findViewById(R.id.btn_radio);
        if (radio != null) {
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isWifiConnected(MainActivity.this)) {
                        startActivity(new Intent(MainActivity.this, FMRadioActivity.class));
                    } else {
                        showWificonnectDialog();
                    }

                }
            });
        }


        Button more = (Button) findViewById(R.id.btn_smart_home);
        if (more != null) {
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, AppsActivity.class));

                }
            });
        }

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_playing);
        playingImg.setImageBitmap(new CircleTransform().transform(bm));
        playingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.szhklt.service.MainService");
                intent.putExtra("count", "DSintoMUSIC");
                sendBroadcast(intent);
                Log.d(TAG, "DSintoMUSIC");
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                onSearch();
                break;
            case R.id.btn_me:
                onAccount();
                break;
            case R.id.btn_kw:
                startKwMusic();
                break;
        }
    }

    private void onSearch() {
        Intent it = new Intent(this, SearchActivity.class);
        startActivity(it);
    }

    private void onAccount() {
        Intent it = new Intent(this, AccountActivity.class);
        startActivity(it);
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

    private void startKwMusic() {
        String kwPackageName = "cn.kuwo.kwmusiccar";
        Utils.startAPP(this, kwPackageName);
    }


    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        SerialportParser.getInstance(this).init(new PlayListener() {
            @Override
            public void onPlayOrPause(boolean play) {
                sendPlayCmd(play ? 1 : 0);
            }

            @Override
            public void onNextOrPrev(boolean next) {
                sendPlayCmd(next ? 3 : 2);
            }

            @Override
            public void onVolumeUpOrDown(boolean up) {
                sendPlayCmd(up ? 5 : 4);
            }

            @Override
            public void onShutdown() {
                sendPlayCmd(7);
            }

            @Override
            public void onReboot() {
                sendPlayCmd(6);
            }

            @Override
            public void onVolumeSet(byte percent) {
                sendPlayCmd(8, percent);
            }

            @Override
            public void onPlayMode(int mode) {
                sendPlayCmd(9, mode);
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
            public void onRead(byte[] buffer, int size) {

            }

            @Override
            public void onExtSrcOrLocSrc(byte rat) {

            }
        }).parse(buffer, size);
    }
}
