package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dell on 2017/10/31.
 */

public class SpeakerActivity extends Activity {

    ListView listView;

    public static SpeakerActivity instance;

    ArrayList<String> list = new ArrayList();

    String[] mp3 = {"notify_audio.wav","notify_audio.wav","notify_audio.wav"};

    SpeakerAdapter adapter ;

    long begin = System.currentTimeMillis();
    long latest;

    AudioManager audioManager;
    int curVolumn;

    Runnable r;

    public static final String ACTION_NEW_SPEAK = "action_new_speak";
    public static final String ACTION_OPEN_AUDIO = "action_open_audio";
    public static final String ACTION_FINISH = "action_finish";

   // AudioManager mAudioManager;
  //  MediaPlayer mediaPlayer;
    public   void playAudio(Context context,String file){
        try {
            //1 初始化AudioManager对象
         /*   //mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            //2 申请焦点
            //mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
            AssetManager assetManager=context.getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd(file);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            });*/

        } catch (Exception e) {

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
//                    Toast.makeText(SpeakerActivity.this,"loss2221",Toast.LENGTH_SHORT).show();
//                    mediaPlayer.pause();
                    //释放焦点，该方法可根据需要来决定是否调用
                    //若焦点释放掉之后，将不会再自动获得
                    //mAudioManager.abandonAudioFocus(mAudioFocusChange);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，
                    //会触发此回调事件，例如播放短视频，拨打电话等。
                    //通常需要暂停音乐播放
//                    mediaPlayer.pause();
                    Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT2222");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    //短暂性丢失焦点并作降音处理
                    Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK2222");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    //当其他应用申请焦点之后又释放焦点会触发此回调
                    //可重新播放音乐
                    Log.d(TAG, "AUDIOFOCUS_GAIN2222");
//                    mediaPlayer.start();
                    break;
            }
        }
    };

    @Override
    public void onNewIntent(Intent intent){
        list.clear();

        list.add("主人,我在。");
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter = new SpeakerAdapter());

        playAudio(this,mp3[(int)(System.currentTimeMillis()%3)]);


        handler.removeMessages(0);
        handler.removeCallbacks(r);
        handler.sendEmptyMessageDelayed(0,10000);

        handler.postDelayed(r = new Runnable() {
            @Override
            public void run() {
//                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//                curVolumn= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
            }
        },1000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        instance = this;

        list.add("主人,我在。");
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter = new SpeakerAdapter());

        playAudio(this,mp3[(int)(System.currentTimeMillis()%3)]);


//        handler.sendEmptyMessageDelayed(0,10000);
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//                curVolumn= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
//            }
//        },1000);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NEW_SPEAK);
        filter.addAction(ACTION_OPEN_AUDIO);
        filter.addAction(ACTION_FINISH);

        this.registerReceiver(receiver,filter);



    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(ACTION_NEW_SPEAK.equals(action)) {
                String s = intent.getStringExtra("speak");
                addStr(s);
            }else if(ACTION_OPEN_AUDIO.equals(action)){
                openAudio();
            }else if(ACTION_FINISH.equals(action)){
                finish();
            }
        }
    };

    void openAudio(){
//        if(audioManager == null){
//            audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        }
//
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolumn, 0);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(latest==0 && System.currentTimeMillis()-begin>9000){
                finish();
            }else if(System.currentTimeMillis()-begin>10000){
                finish();
            }

        }
    };

    public void addStr(String s){
        list.add(s);
        adapter.notifyDataSetChanged();

        latest = System.currentTimeMillis();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;

    /*    if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }*/
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(mAudioFocusChange);


//        if(audioManager != null) {
//            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolumn, 0);
//        }else{
//            audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolumn, 0);
//        }

        this.unregisterReceiver(receiver);

    }

    class SpeakerAdapter extends BaseAdapter
    {


        public SpeakerAdapter(){

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2;// Type 两种 0和1
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int viewType = getItemViewType(position);
            LeftViewHolder leftViewHolder = null;
            RightViewHolder rightViewHolder = null;
            if (convertView == null) {
                if (viewType == 0) {// left
                    convertView = LayoutInflater.from(SpeakerActivity.this).inflate(R.layout.item_left, null);
                    leftViewHolder = new LeftViewHolder();
                    leftViewHolder.leftImageView = (ImageView) convertView.findViewById(R.id.header);
                    leftViewHolder.leftTextView = (TextView)convertView.findViewById(R.id.text);
                    convertView.setTag(leftViewHolder);
                } else {
                    convertView = LayoutInflater.from(SpeakerActivity.this).inflate(R.layout.item_right, null);
                    rightViewHolder = new RightViewHolder();
                    rightViewHolder.rightImageView = (ImageView) convertView.findViewById(R.id.header);
                    rightViewHolder.rightTextView = (TextView)convertView.findViewById(R.id.text);
                    convertView.setTag(rightViewHolder);
                }
            } else {
                if (viewType == 0) {// left
                    leftViewHolder = (LeftViewHolder) convertView.getTag();
                } else {
                    rightViewHolder = (RightViewHolder) convertView.getTag();
                }
            }



            if (viewType == 0) {// left
                leftViewHolder.leftImageView.setImageResource(R.drawable.ic_bgm_speaker);
                leftViewHolder.leftTextView.setText(getItem(position));
            } else {
                rightViewHolder.rightImageView.setImageResource(R.drawable.ic_people_speaker);
                rightViewHolder.rightTextView.setText(getItem(position));
            }

            return convertView;
        }
    }

    class LeftViewHolder {
        ImageView leftImageView;
        TextView leftTextView;
    }

    class RightViewHolder {
        ImageView rightImageView;
        TextView rightTextView;
    }

}
