package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.music.Music_1;
import com.dsn.andy.bgmlauncher.adapter.MusicHistoryAdapter;
import com.dsn.andy.bgmlauncher.bean.Album;
import com.dsn.andy.bgmlauncher.bean.Music;
import com.dsn.andy.bgmlauncher.db2.DBUtils;
import com.dsn.andy.bgmlauncher.http.GetSongInfoService;
import com.dsn.andy.bgmlauncher.http.SearchMusicListService;
import com.dsn.andy.bgmlauncher.http.Song;
import com.dsn.andy.bgmlauncher.util.FileUtil;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CircleTransform;
import com.dsn.andy.bgmlauncher.view.CommomDialog;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.dsn.andy.bgmlauncher.R.id.play;

/**
 * Created by Administrator on 2017/8/18.
 */

public class PlayActivity extends Activity implements View.OnClickListener {

    public static final String ACTION_NEXT = "action_next_music";
    public static final String ACTION_PREV = "action_prev_music";

    public static PlayActivity instance;
    private final int Duration = 600;  // 动画时长

    private   AudioState audioState = AudioState.STATE_STOP;   //音乐播放器状态

    private  ImageView btnPre, btnPlay, btnNext, storeImg,playListImg;
    private ImageView cdBox;

    private  boolean flag = false;  //标记，控制唱片旋转

    public static Music music;
    public static Album album;
    CommomDialog wificonnectDialog;
    SeekBar seekBar;

    TextView positionTxt;
    TextView durationTxt;

    int duration;

    boolean isSame = false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        if (!Utils.isWifiConnected(this)) {
            //弹出提示框
//            if (wificonnectDialog != null && wificonnectDialog.isShowing()) {
//                return;
//            }
//            wificonnectDialog = new CommomDialog(this, R.style.dialog, "您的wifi尚连接,请连接wifi进行体验!", new CommomDialog.OnCloseListener() {
//                @Override
//                public void onClick(Dialog dialog, boolean confirm) {
//                    if (confirm) {
//                        Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                        sendBroadcast(new Intent("com.dsn.andy.bgmlauncher.NOTLAUNCHER"));
//                        startActivity(it);
//                        dialog.dismiss();
//                    }
//                }
//            }).setTitle("提示").setNegativeButton("暂不连接").setPositiveButton("去连接");
//            wificonnectDialog.show();
        }

        instance = this;
        Music m = (Music) getIntent().getSerializableExtra("music");
        album = (Album) getIntent().getSerializableExtra("album");
        if (m == null || album == null) {
            finish();
            return;
        }
        if (music == null || !music.equals(m)) {
            music = m;
            if (music.url != null) {
                Music_1 m1 = new Music_1();
                m1.setName(music.name);
                m1.setSinger(music.singer);
                m1.setIs_love(false);
                m1.setTime(Utils.getTime(PlayActivity.this));
                m1.setUrl(music.url);
                m1.setImgUrl(music.imgUrl);
                m1.setPath(music.path);
                m1.setLocal(music.local > 0);


                addToHistory(m1);
                initView();
                play(music.url);
                setAudioState(AudioState.STATE_PLAYING);

                notifyPlaying();

            } else if (!TextUtils.isEmpty(music.path)) {
                Music_1 m1 = new Music_1();
                m1.setName(music.name);
                m1.setSinger(music.singer);
                m1.setIs_love(false);
                m1.setTime(Utils.getTime(PlayActivity.this));
                m1.setUrl(music.url);
                m1.setImgUrl(music.imgUrl);
                m1.setPath(music.path);
                m1.setLocal(music.local > 0);


                addToHistory(m1);
                initView();
                play(music.path);
                setAudioState(AudioState.STATE_PLAYING);

                notifyPlaying();
            } else {
                search(music);
            }
        } else {
            isSame = true;
        }
        initView();

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(PlayService.ACTION_UPDATE_PROGERSS);
        filter1.addAction(PlayService.ACTION_PREPARED);
        filter1.addAction(PlayService.ACTION_COMPLETED);
        filter1.addAction(PlayService.ACTION_PLAYING);
        filter1.addAction(PlayService.ACTION_PAUSE);
        filter1.addAction(PlayService.ACTION_ERROR);
        filter1.addAction(ACTION_NEXT);
        filter1.addAction(ACTION_PREV);
        filter1.addAction("android.intent.msg.SPEECHCOMPLETE");
        filter1.addAction("android.intent.msg.WAKEUP");
        filter1.addAction("android.intent.msg.TEMPPAUSE");
        filter1.addAction("android.intent.msg.NEXT");
        filter1.addAction("android.intent.msg.BACK");
        filter1.addAction("android.intent.msg.PRE");
        filter1.addAction("android.intent.msg.PAUSE");
        filter1.addAction("android.intent.msg.PLAY");
        this.registerReceiver(receiver, filter1);

        loadImg();

    }

    public static String toTime(int time) {
        String str = "";
        int seconds = time / 1000;
        int m = seconds / 60;
        int s = seconds % 60;
        if (m == 0) {
            str = "00:";
        } else {
            str = "0" + m + ":";
        }

        if (s < 10) {
            str += "0" + s;
        } else {
            str += s + "";
        }

        return str;
    }
    boolean mIsPeople=false;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (PlayService.ACTION_UPDATE_PROGERSS.equals(action)) {
                int progress = intent.getIntExtra("progress", -1);
                seekBar.setProgress(progress);
                duration = intent.getIntExtra("duration", -1);
                if (duration == -1) {
                    return;
                }
                int position = intent.getIntExtra("position", -1);;
                positionTxt.setText(toTime(position));

                durationTxt.setText(toTime(duration));

            } else if (PlayService.ACTION_PREPARED.equals(action)) {
                showtip(action);
                flag = true;
                audioState = AudioState.STATE_PLAYING;
                setAudioState(AudioState.STATE_PLAYING);
            } else if (PlayService.ACTION_COMPLETED.equals(action)) {
                flag = false;
                audioNext();
            } else if (PlayService.ACTION_PLAYING.equals(action)) {

                audioState = AudioState.STATE_PLAYING;
                flag = true;
                setAudioState(AudioState.STATE_PLAYING);
            }else if ("android.intent.msg.PLAY".equals(action)) {

                if (audioState == AudioState.STATE_STOP) {
                    prepareMusic();
                    setAudioState(AudioState.STATE_PLAYING);
                } else if (audioState == AudioState.STATE_PAUSE) {
                    start();
                    startImg();
                    setAudioState(AudioState.STATE_PLAYING);
                }
            } else if (PlayService.ACTION_PAUSE.equals(action)) {
                audioState = AudioState.STATE_PAUSE;
                setAudioState(AudioState.STATE_PAUSE);
                flag = false;
                notifyPause();
            } else if ("android.intent.msg.PAUSE".equals(action)) {
                mIsPeople=true;
                audioState = AudioState.STATE_PAUSE;
                setAudioState(AudioState.STATE_PAUSE);
                flag = false;
                notifyPause();
            }else if (ACTION_NEXT.equals(action)||"android.intent.msg.NEXT".equals(action)) {
                flag = false;
                Log.d("DSN===",action);
                pauseImg();
                audioNext();
                setAudioState(AudioState.STATE_PAUSE);
            } else if (ACTION_PREV.equals(action)||"android.intent.msg.PRE".equals(action)) {
                audioPrevious();
            } else if ("android.intent.msg.WAKEUP".equals(action)) {
               Log.d("DSN===",action);
                //唤醒
                pause();
                pauseImg();
                setAudioState(AudioState.STATE_PAUSE);
            } else if ("android.intent.msg.BACK".equals(action)) {
                Log.d("DSN===",action);
                mIsPeople=true;
                pause();
               finish();
  ;
               //startActivity(new Intent(PlayActivity.this,MainActivity.class));
            } else if ("android.intent.msg.TEMPPAUSE".equals(action)) {
                Log.d("DSN===",action);
                pause();
                pauseImg();
                setAudioState(AudioState.STATE_PAUSE);
                //开始说话暂停音乐
            }else if("android.intent.msg.SPEECHCOMPLETE".equals(action)){
                Log.d("DSN===",action);
                if(mIsPeople){

                }else{
                    if (audioState == AudioState.STATE_STOP) {
                        prepareMusic();
                        setAudioState(AudioState.STATE_PLAYING);
                    } else if (audioState == AudioState.STATE_PAUSE) {
                        start();
                        startImg();
                        setAudioState(AudioState.STATE_PLAYING);
                    }
                }
            }
        }
    };

    void showtip(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(PlayActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getSongInfo(final Song song) {
        Log.e("andy", "get song info");
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
                    Log.e("andy", "songinfo=" + str);
                    song.fillSongInfo(new JSONObject(str));
                    Utils.pw(song.url);
                    music.url = song.url;
                    music.imgUrl = song.imgUrl;
                    Log.e("andy", "img=" + music.imgUrl);

                    play(music.url);
                    notifyPlaying();
                    setAudioState(AudioState.STATE_PLAYING);


                    loadImg();

                    Music_1 m = new Music_1();
                    m.setName(song.songName);
                    m.setSinger(song.singername);
                    m.setIs_love(false);
                    m.setTime(Utils.getTime(PlayActivity.this));
                    m.setPath(music.path);
                    m.setLocal(music.local > 0);
                    m.setImgUrl(music.imgUrl);

                    addToHistory(m);

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


    void addToHistory(Music_1 music) {
        DBUtils dbUtils = DBUtils.getInstance(this);
        boolean isSuccess = dbUtils.insertMusic_1(music);
        Log.e("andy", "isSuccess=" + isSuccess);
//        dbUtils.close();
    }


    void search(final Music music) {
        Log.e("andy", "search music");
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
                    showtip(str);
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
                            Log.e("andy", "find ss");
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
                                Log.e("andy", "find ss");
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
                Log.e("andy", "onfailure=" + t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    TextView nameTxt;
    public static File dcimFile;
    Animation rotate;

    private boolean isLove(Music music){
        DBUtils utils = DBUtils.getInstance(this);
        Music_1 music_1 = new Music_1();
        music_1.setSinger(music.singer);
        music_1.setName(music.name);

        return utils.isLove(music_1);
    }

    private void initView() {
        positionTxt = (TextView) findViewById(R.id.position);
        durationTxt = (TextView) findViewById(R.id.duration);

        nameTxt = (TextView) findViewById(R.id.name);
        nameTxt.setText(music.name);

        TextView albumTxt = (TextView) findViewById(R.id.album);
        albumTxt.setText(album.name);


        cdBox = (ImageView) findViewById(R.id.sound_img);
        btnNext = (ImageView) findViewById(R.id.next);
        btnPre = (ImageView) findViewById(R.id.prev);
        btnPlay = (ImageView) findViewById(play);
        storeImg = (ImageView) findViewById(R.id.store);
        playListImg = (ImageView)findViewById(R.id.playlist);

        btnPlay.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        storeImg.setOnClickListener(this);
        playListImg.setOnClickListener(this);


        storeImg.setBackgroundResource(isLove(music)?R.drawable.ic_store_select:R.drawable.ic_store);

        seekBar = (SeekBar) findViewById(R.id.my_progress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                seekTo(progress);
            }
        });


        if (PlayService.mMediaPlayer != null) {
            if (PlayService.mMediaPlayer.isPlaying()) {
                setAudioState(AudioState.STATE_PLAYING);
            } else {
                setAudioState(AudioState.STATE_PAUSE);
            }
        }


    }

    void loadImg() {
        if (!isValidImgUrl()) {
            startImg2();
            return;
        }

        final String imgUrl = music.imgUrl;
        Log.e("andy", "loadimg " + imgUrl);

        String sss = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.lastIndexOf("?") == -1 ? imgUrl.length() : imgUrl.lastIndexOf("?"));
        String imageName = sss;

        File file = new File("/mnt/sdcard/dsn/",
                imageName);
        if (file.exists() && file.length() > 1000 * 10) {
            Log.e("andy", "exists..." + isSame);
            dcimFile = file;
            if (isSame) {
                if (PlayService.mMediaPlayer.isPlaying()) {
                    startImg();
                } else {
                    Bitmap bm = BitmapFactory.decodeFile(dcimFile.getAbsolutePath());
                    cdBox.setImageBitmap(new CircleTransform().transform(bm));
                }

                isSame = false;
            } else {
                startImg();
            }
            return;
        } else if (file.exists() && file.length() == 0) {
            file.delete();
        }


        Log.e("andy", "download img");

        //Target
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.e("andy", "onBitmapLoaded");
                String sss = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.lastIndexOf("?") == -1 ? imgUrl.length() : imgUrl.lastIndexOf("?"));
                String imageName = sss;
                Log.e("andy", "onbitmaploaded " + imageName);

                dcimFile = FileUtil
                        .getDCIMFile(FileUtil.PATH_PHOTOGRAPH, imageName);
                Log.e("andy", "path=" + dcimFile.getAbsolutePath());
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, ostream);

                } catch (Exception e) {
                    // Log.e("andy","exc="+e.getMessage());
//                    e.printStackTrace();
                } finally {
                    try {
                        if (ostream != null) {
                            ostream.close();
                        }
                    } catch (Exception e) {

                    }

                }

                if (isSame) {
                    if (PlayService.mMediaPlayer.isPlaying()) {
                        startImg();
                    } else {
                        Bitmap bm = BitmapFactory.decodeFile(dcimFile.getAbsolutePath());
                        cdBox.setImageBitmap(new CircleTransform().transform(bm));
                    }

                    isSame = false;
                } else {
                    startImg();
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
        cdBox.setTag(target);
    }

    void notifyPlaying() {
        Intent it = new Intent(MainActivity.ACTION_PLAYING);
        sendBroadcast(it);
    }

    void notifyPause() {
        Intent it = new Intent(MainActivity.ACTION_PAUSE);
        sendBroadcast(it);
    }

    void seekTo(int p) {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_SEEK);
        it.putExtra("progress", p);
        startService(it);
    }

    void play(String url) {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_PLAY);
        it.putExtra("url", url);
        startService(it);

        DSNApplication.playingFrom = 1;
    }

    void start() {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_START);
        startService(it);
    }


    void pause() {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_PAUSE);
        startService(it);
    }

    private void prepareMusic() {
        //从Assets中获取音频资源
//        cdBox.getDrawable().setLevel(0);
        Toast.makeText(this, music.url, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(music.url))
            play(music.url);
        else {
            play(music.path);
        }

        startImg();

    }

    void startImg2() {
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_playing);
        if (bm == null) {
            Log.e("andy", "222bm null");
            return;
        }


        cdBox.setImageBitmap(new CircleTransform().transform(bm));

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

        cdBox.startAnimation(rotate);
    }

    public static boolean isValidImgUrl() {
        if (music.imgUrl == null || TextUtils.isEmpty(music.imgUrl) ||music.imgUrl.contains("{size}") || !music.imgUrl.startsWith("http")) {
            return false;
        }
        return true;
    }


    void startImg() {
        if (!isValidImgUrl()) {
            startImg2();
            return;
        }
        if (dcimFile == null) return;
        Log.e("andy", "start img " + dcimFile.getAbsolutePath());
        Bitmap bm = BitmapFactory.decodeFile(dcimFile.getAbsolutePath());
        if (bm == null) {
            Log.e("andy", "bm null");
        }
        if (cdBox == null) {
            Log.e("andy", "cdbox null");
        }

        cdBox.setImageBitmap(new CircleTransform().transform(bm));

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

        cdBox.startAnimation(rotate);


    }

    void pauseImg() {
        if (rotate != null) {
            rotate.cancel();
            rotate = null;
        }
    }


    public  void setAudioState(AudioState audioState) {
       this.audioState = audioState;
        if (audioState == AudioState.STATE_PLAYING) {
            btnPlay.setBackgroundResource(R.drawable.ic_pause);
        } else {
            btnPlay.setBackgroundResource(R.drawable.ic_play);
        }
    }

    @Override
    public void onClick(View view) {
        // 一切按键都必须在动画完成的状态下才能被触发

        switch (view.getId()) {
            case R.id.prev:
                flag = false;
                audioPrevious();
                pauseImg();
                setAudioState(AudioState.STATE_PAUSE);
                break;
            case play:
                if (audioState != AudioState.STATE_PLAYING) {
                    mIsPeople=true;
                    if (audioState == AudioState.STATE_STOP) {
                        prepareMusic();
                        setAudioState(AudioState.STATE_PLAYING);
                    } else if (audioState == AudioState.STATE_PAUSE) {
                        start();
                        {
                            startImg();
                        }
                        setAudioState(AudioState.STATE_PLAYING);
                    }
                } else {
                    mIsPeople=false;
                    pause();
                    pauseImg();
                    setAudioState(AudioState.STATE_PAUSE);

                }

                break;
            case R.id.next:
                flag = false;
                pauseImg();
                audioNext();
                setAudioState(AudioState.STATE_PAUSE);
                break;
            case R.id.store:
                if(isLove(music)){
                    onNotStore();
                }else{
                    onStore();
                }
                break;
            case R.id.playlist:
                onPlayList();
            default:
                break;
        }

    }

    void onNotStore() {
        DBUtils utils = DBUtils.getInstance(this);
        Music_1 music_1 = new Music_1();
        music_1.setName(music.name);
        music_1.setSinger(music.singer);
        music_1.setIs_love(false);
        music_1.setPath(music.path);
        music_1.setLocal(music.local > 0);
        music_1.setUrl(music.url);
        if (utils.isExist(music_1)) {
            utils.updateMusic_1(music_1);
        }else{
            utils.insertMusic_1(music_1);
        }
        storeImg.setBackgroundResource(R.drawable.ic_store);
//        utils.close();
        Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
    }



    void onStore() {
        DBUtils utils = DBUtils.getInstance(this);
        Music_1 music_1 = new Music_1();
        music_1.setName(music.name);
        music_1.setSinger(music.singer);
        music_1.setIs_love(true);
        music_1.setPath(music.path);
        music_1.setLocal(music.local > 0);
        music_1.setUrl(music.url);
        if (utils.isExist(music_1)) {
            utils.updateMusic_1(music_1);
        }else{
            utils.insertMusic_1(music_1);
        }
        storeImg.setBackgroundResource(R.drawable.ic_store_select);
//        utils.close();
        Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
    }

    PopupWindow popupWindow;
    void onPlayList(){
        final View popupWindowView = getLayoutInflater().inflate(R.layout.pop, null);
        ListView listView = (ListView)popupWindowView.findViewById(R.id.list);
        MusicHistoryAdapter adapter = new MusicHistoryAdapter(this, album.musicList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pause();
                Music music = album.musicList.get(position);

                play(music);
                popupWindow.dismiss();

            }
        });
        popupWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT, true);

        popupWindow.setAnimationStyle(R.style.AnimationRightFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xff222222);
        popupWindow.setBackgroundDrawable(dw);

        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main2, null), Gravity.RIGHT, 0, 300);



    }

    void play(Music music){
        this.music = music;
        durationTxt.setText("--:--");
        positionTxt.setText("00:00");

        nameTxt.setText(music.name);

        if (music.url != null) {
            play(music.url);
            setAudioState(AudioState.STATE_PLAYING);

            Music_1 m1 = new Music_1();
            m1.setName(music.name);
            m1.setSinger(music.singer);
            m1.setIs_love(false);
            m1.setTime(Utils.getTime(PlayActivity.this));
            m1.setImgUrl(music.imgUrl);
            m1.setUrl(music.url);
            m1.setPath(music.path);
            m1.setLocal(music.local > 0);

            addToHistory(m1);
            play(music.url);

            notifyPlaying();
        } else if (!TextUtils.isEmpty(music.path)) {
            Music_1 m1 = new Music_1();
            m1.setName(music.name);
            m1.setSinger(music.singer);
            m1.setIs_love(false);
            m1.setTime(Utils.getTime(PlayActivity.this));
            m1.setUrl(music.url);
            m1.setImgUrl(music.imgUrl);
            m1.setPath(music.path);
            m1.setLocal(music.local > 0);


            addToHistory(m1);
            initView();
            play(music.path);
            setAudioState(AudioState.STATE_PLAYING);

            notifyPlaying();
        } else {
            search(music);
        }
        loadImg();


    }



    /**
     * 上一首
     */
    private void audioPrevious() {
        pause();
        durationTxt.setText("--:--");
        positionTxt.setText("00:00");
        ArrayList<Music> musics = album.musicList;


        int p = 0;
        for (int i = 0; i < musics.size(); i++) {
            if (music.url == null && musics.get(i).name.equalsIgnoreCase(music.name)) {
                p = i - 1;
                break;
            } else if (music.url != null &&
                    musics.get(i).url != null &&
                    musics.get(i).url.equalsIgnoreCase(music.url)) {
                p = i - 1;
                break;
            }
        }
        if (p >= 0) {
            music = musics.get(p);
            nameTxt.setText(music.name);

            notifyMusicSwitch(p);
        } else {
            music = musics.get(musics.size() - 1);
            nameTxt.setText(music.name);

            notifyMusicSwitch(musics.size() - 1);
        }
        if (music.url != null) {
            play(music.url);
            setAudioState(AudioState.STATE_PLAYING);

            Music_1 m1 = new Music_1();
            m1.setName(music.name);
            m1.setSinger(music.singer);
            m1.setIs_love(false);
            m1.setTime(Utils.getTime(PlayActivity.this));
            m1.setImgUrl(music.imgUrl);
            m1.setUrl(music.url);
            m1.setPath(music.path);
            m1.setLocal(music.local > 0);

            addToHistory(m1);
            play(music.url);

            notifyPlaying();
        } else if (!TextUtils.isEmpty(music.path)) {
            Music_1 m1 = new Music_1();
            m1.setName(music.name);
            m1.setSinger(music.singer);
            m1.setIs_love(false);
            m1.setTime(Utils.getTime(PlayActivity.this));
            m1.setUrl(music.url);
            m1.setImgUrl(music.imgUrl);
            m1.setPath(music.path);
            m1.setLocal(music.local > 0);


            addToHistory(m1);
            initView();
            play(music.path);
            setAudioState(AudioState.STATE_PLAYING);

            notifyPlaying();
        } else {
            search(music);
        }
        loadImg();
    }

    /**
     * 下一首
     */
    private void audioNext() {
        pause();
        durationTxt.setText("--:--");
        positionTxt.setText("00:00");
        ArrayList<Music> musics = album.musicList;

        int p = 0;
        for (int i = 0; i < musics.size(); i++) {
            if (music.url == null && musics.get(i).name.equalsIgnoreCase(music.name)) {
                p = i + 1;
                break;
            } else if (music.url != null &&
                    musics.get(i).url != null &&
                    musics.get(i).url.equalsIgnoreCase(music.url)) {
                p = i + 1;
                break;
            }
        }
        if (p < musics.size()) {
            music = musics.get(p);
            nameTxt.setText(music.name);

            notifyMusicSwitch(p);

        } else {
            music = musics.get(0);
            nameTxt.setText(music.name);

            notifyMusicSwitch(0);
        }


        if (music.url != null) {
            play(music.url);
            setAudioState(AudioState.STATE_PLAYING);

            Music_1 m1 = new Music_1();
            m1.setName(music.name);
            m1.setSinger(music.singer);
            m1.setIs_love(false);
            m1.setTime(Utils.getTime(PlayActivity.this));
            m1.setUrl(music.url);
            m1.setImgUrl(music.imgUrl);
            m1.setPath(music.path);
            m1.setLocal(music.local > 0);

            addToHistory(m1);
            play(music.url);

            notifyPlaying();
        } else if (!TextUtils.isEmpty(music.path)) {
            Music_1 m1 = new Music_1();
            m1.setName(music.name);
            m1.setSinger(music.singer);
            m1.setIs_love(false);
            m1.setTime(Utils.getTime(PlayActivity.this));
            m1.setUrl(music.url);
            m1.setImgUrl(music.imgUrl);
            m1.setPath(music.path);
            m1.setLocal(music.local > 0);


            addToHistory(m1);
            initView();
            play(music.path);
            setAudioState(AudioState.STATE_PLAYING);

            notifyPlaying();
        } else {
            search(music);
        }
        loadImg();

    }

    void notifyMusicSwitch(int position) {
        Intent it = new Intent(MusicListActivity.ACTION_MUSIC_SWITCH);
        it.putExtra("position", position);
        sendBroadcast(it);
    }

    public enum AudioState {
        STATE_STOP,
        STATE_PAUSE,
        STATE_PREPARE,
        STATE_PLAYING
    }

    boolean running = true;


//    Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
////            int level = cdBox.getDrawable().getLevel();
////            level = level + 100;
////            if(level > 10000) {
////                level = level - 10000;
////            }
////            cdBox.getDrawable().setLevel(level);
//
//
////            seekBar.setProgress((int)(mMediaPlayer.getCurrentPosition()*100f/mMediaPlayer.getDuration()));
//        }
//    };


    @Override
    protected void onPause() {
        super.onPause();
        mIsPeople=true;
        pause();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (audioState == AudioState.STATE_STOP) {
            prepareMusic();
            setAudioState(AudioState.STATE_PLAYING);
        } else if (audioState == AudioState.STATE_PAUSE) {
            start();
            startImg();
            setAudioState(AudioState.STATE_PLAYING);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
        running = false;
        this.unregisterReceiver(receiver);
        //this.unregisterReceiver(playReceiver);
    }


}
