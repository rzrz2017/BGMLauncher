package com.dsn.andy.bgmlauncher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dell on 2017/9/26.
 */

public class PlayService extends Service  implements  MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener  {

    public static final String ACTION_UPDATE_PROGERSS = "action_update_progress";
    public static final String ACTION_PREPARED = "action_prepared";
    public static final String ACTION_COMPLETED = "action_completed";
    public static final String ACTION_PLAYING = "action_playing";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_ERROR = "action_error";

    public static final int CMD_PLAY = 1;
    public static final int CMD_SEEK = 2;
    public static final int CMD_START = 3;
    public static final int CMD_PAUSE = 4;
    public static final int CMD_GOON = 5;

    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_RADIO = 2;

    public static MediaPlayer mMediaPlayer;

    static String url;

    public static int playCmd = 1; //1-播放 0-暂停
    public static AudioManager mAudioManager;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        requestFocus();

        int cmd = intent.getIntExtra("cmd",-1);
        if(cmd == CMD_PLAY){
            url = intent.getStringExtra("url");
            prepareMusic();
        }else if(cmd == CMD_SEEK){
            if(mMediaPlayer == null){
                return super.onStartCommand(intent, flags, startId);
            }
            int progress = intent.getIntExtra("progress",-1);
            long duration =0;
            try{
                if(mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    duration = mMediaPlayer.getDuration();
                }
            }catch (Exception e){
                if(timer != null){
                    timer.cancel();
                    timer = null;
                }
                return super.onStartCommand(intent, flags, startId);
            }
            int position = (int)(duration*progress/100f);
            mMediaPlayer.seekTo(position);
            requestFocus();
        }else if(cmd == CMD_START){
            if(mMediaPlayer == null){
                return super.onStartCommand(intent, flags, startId);
            }
            mMediaPlayer.start();

            requestFocus();

            handler.sendEmptyMessage(4);
        }else if(cmd == CMD_PAUSE){
          mAudioManager.abandonAudioFocus(mAudioFocusChange);
            if(mMediaPlayer == null){
                return super.onStartCommand(intent, flags, startId);
            }
            mMediaPlayer.pause();
           requestFocus();

            handler.sendEmptyMessage(5);
        }else if(cmd == CMD_GOON){
            mMediaPlayer.start();
            requestFocus();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    void requestFocus(){
        //1 初始化AudioManager对象
       mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        //2 申请焦点
       mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    void prepareMusic(){
        try {
            exit();
            requestFocus();

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Log.e("andy","11111");
            mMediaPlayer.prepareAsync();
            Log.e("andy","2222");
            mMediaPlayer.setOnPreparedListener(PlayService.this);
            mMediaPlayer.setOnCompletionListener(PlayService.this);
            mMediaPlayer.setOnErrorListener(PlayService.this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  final String TAG = "andy";
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            switch (focusChange){
                case AudioManager.AUDIOFOCUS_LOSS:
                    //长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时，
                    //会触发此回调事件，例如播放QQ音乐，网易云音乐等
                    //通常需要暂停音乐播放，若没有暂停播放就会出现和其他音乐同时输出声音
                  showTip( "AUDIOFOCUS_LOSS1111111");
                  /*  if(mMediaPlayer != null) {
                        mMediaPlayer.pause();
                    }*/
                    handler.sendEmptyMessage(5);

                    //释放焦点，该方法可根据需要来决定是否调用
                    //若焦点释放掉之后，将不会再自动获得
                   mAudioManager.abandonAudioFocus(mAudioFocusChange);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，
                    //会触发此回调事件，例如播放短视频，拨打电话等。
                    //通常需要暂停音乐播放
                    if(mMediaPlayer != null) {
                        mMediaPlayer.pause();
                    }

                    handler.sendEmptyMessage(5);
                    Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT111111");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    //短暂性丢失焦点并作降音处理
                    Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK111111");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    //当其他应用申请焦点之后又释放焦点会触发此回调
                    //可重新播放音乐
                    Log.d(TAG, "AUDIOFOCUS_GAIN111111");
                    showTip("AUDIO FOCUS");
                    /*if(PlayService.playCmd == 1) {
                        handler.sendEmptyMessage(4);
                        if(mMediaPlayer != null) {
                            mMediaPlayer.start();
                        }
                    }*/
                    break;
            }
        }
    };

    void showTip(String str){
       // Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        exit();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        try {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;

            if(timer != null){
                timer.cancel();
                timer = null;
            }
        }catch (Exception e){

        }
        handler.sendEmptyMessage(6);
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //  stop(1);
//        mMediaPlayer.start();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
        Log.e("andy","po="+mMediaPlayer.getCurrentPosition());
        if(mMediaPlayer.getCurrentPosition()>0)
        {
           // Toast.makeText(this,"complete ",Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessage(3);
        }

    }

    @Override
    public void onPrepared(MediaPlayer mMediaPlayer) {
        Log.e("andy","33333");
        mMediaPlayer.start();

        if(timer == null){
            timer = new Timer();
        }else{
            timer.cancel();
            timer = new Timer();
        }

        handler.sendEmptyMessage(2);

        task = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        timer.schedule(task,0,1000);

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Intent it = new Intent(ACTION_UPDATE_PROGERSS);
                long duration =0;
                try{
                    if(mMediaPlayer !=null && mMediaPlayer.isPlaying()) {
                        duration = mMediaPlayer.getDuration();

                        int position = (int) (mMediaPlayer.getCurrentPosition()) ;
                        int progress =(int) (position*100/duration);
                        it.putExtra("position", position);
                        it.putExtra("duration", (int) duration);
                        it.putExtra("progress",progress);
                        sendBroadcast(it);
                    }
                }catch (Exception e){
                    Log.e("andy","e ="+e.getMessage());
                    exit();
                    return;
                }

            }else if(msg.what ==2){
                Intent it = new Intent(ACTION_PREPARED);
                sendBroadcast(it);
            }else if(msg.what == 3){
                Intent it = new Intent(ACTION_COMPLETED);
                sendBroadcast(it);
            }else if(msg.what == 4){
                Intent it = new Intent(ACTION_PLAYING);
                sendBroadcast(it);
            }else if(msg.what == 5){
                Intent it = new Intent(ACTION_PAUSE);
                sendBroadcast(it);
            }else if(msg.what == 6){
                Intent it = new Intent(ACTION_ERROR);
                sendBroadcast(it);
            }
            super.handleMessage(msg);
        }
    };

    static Timer timer;
    static TimerTask task;

    private void exit(){
        try{
            if(timer != null){
                timer.cancel();
                timer = null;
            }

            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;

            }
        }catch (Exception e){

        }
    }
}
