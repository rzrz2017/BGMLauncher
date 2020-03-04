package com.dsn.andy.bgmlauncher.receiver;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.andy.net.bean.PlaySong;
import com.andy.net.socket.ServerSocketManager;
import com.andy.net.util.ServerDataUtils;
import com.dsn.andy.bgmlauncher.home.BaseBroadcastReceiver;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * Created by dell on 2018/7/19.
 */

public class MusicReceiver extends BaseBroadcastReceiver {
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    public static final String ACTION_MUSIC_NOTIFY = "com.hklt.play_song";
    private static final String[] MY_ACTIONS =
            {
                    ACTION_MUSIC_NOTIFY
            };

    public MusicReceiver(Context context, OutputStream outputStream) {
        super(context, MY_ACTIONS);
        mOutputStream = outputStream;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra("type",-1);
        String data = intent.getStringExtra("data");
        Log.e("andy","recv data "+data);
        Log.e("andy","type="+type);
        if(type == -1 || TextUtils.isEmpty(data)){
            return;
        }

        if(ACTION_MUSIC_NOTIFY.equals(action)){
            if(intent.getSerializableExtra("musics") != null){
            }

            if(type == 1){//歌曲信息
                PlaySong playSong = new PlaySong();
                try {
                    JSONObject json = new JSONObject(data);
                    String singer = json.getString("singer");
                    String song = json.getString("song");
                    String player = json.getString("player");


                    playSong.setSingerName(singer);
                    playSong.setSongName(song);

                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    Log.e("andy","mmmmm");
                    ServerSocketManager.getInstance(context).notifyAllClient(ServerDataUtils.makePlaySongInfoResponse(playSong));
                    Log.e("andy","song="+ServerDataUtils.makePlaySongInfoResponse(playSong));
                }

            }else if(type == 2){ //播放状态
                PlaySong playSong = new PlaySong();
                try {
                    JSONObject json = new JSONObject(data);
                    int state = json.getInt("state");
                    Log.e("dinaike","state:"+state);
                    int position = json.getInt("position");
                    int duration = json.getInt("duration");
                    int volume = json.getInt("volume");

                    if(state == 1){//播放
                        respondMsg(new byte[]{0x20});
                    }else if(state == 2){//暂停
                        respondMsg(new byte[]{0x21});
                    }

                    playSong.setPlaying(state==1);
                    playSong.setPosition(position);
                    playSong.setDuration(duration);

                }catch (Exception ex){

                }finally {
                    ServerSocketManager.getInstance(context).notifyAllClient(ServerDataUtils.makePlayStateResponse(playSong));
                    Log.e("andy","song="+ServerDataUtils.makePlayStateResponse(playSong));
                }
            }else if(type == 3){//音量控制
                String volres = intent.getStringExtra("VolRes");
                Log.e("dinaike","volres:"+volres);
                if(volres.equals("increase")){//增加音量成功
                    respondMsg(new byte[]{0x24});
                }else if (volres.equals("decrease")){
                    respondMsg(new byte[]{0x25});
                }
            }
        }
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
}
