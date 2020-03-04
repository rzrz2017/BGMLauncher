package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.adapter.MusicAdapter2;
import com.dsn.andy.bgmlauncher.bean.Album;
import com.dsn.andy.bgmlauncher.bean.DSMedia;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MusicListActivity extends Activity {
    Album album;
    public static final String ACTION_MUSIC_SWITCH = "action_music_switch";
    private ArrayList<DSMedia> mediaslist;

    ListView list;
    private String str="";
    public static String[] urls = {
            "http://audio.xmcdn.com/group30/M07/38/96/wKgJXlmKc2qTV6WzAMnF8-kXa_U818.m4a",
            "http://audio.xmcdn.com/group31/M08/0C/36/wKgJX1mAI37SJoSiAJc4oNqZn7Y616.m4a",
            "http://audio.xmcdn.com/group31/M0B/9E/19/wKgJX1l4Wn-h5gyiAKj916t4mVg404.m4a",
            "http://audio.xmcdn.com/group30/M05/22/2D/wKgJXllux43BVWe3ANBgHuDwM18846.m4a",
            "http://audio.xmcdn.com/group29/M08/22/E3/wKgJWVll-HPy1_yTAMa1zAuR0aM006.m4a",
            "http://audio.xmcdn.com/group28/M01/A4/D5/wKgJXFlcxvvCjg-bAJuqrLRlOyk667.m4a",
            "http://audio.xmcdn.com/group28/M08/A3/C4/wKgJXFlJ4qnSxqM4ALnN0rmXgvw473.m4a",
            "http://audio.xmcdn.com/group28/M05/19/C2/wKgJSFlAoKjjbQm8AKBUgcT7psA274.m4a",
           // "http://audio.xmcdn.com/group29/M09/98/D0/wKgJXVk32znRAIdhALchiou6TlE023.m4a",
            //"http://audio.xmcdn.com/group28/M00/FC/5B/wKgJXFkuN06QNpAWAIYw21njRyw825.m4a"

    };

    public static String[] names;

    MusicAdapter2 adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        initView();
        mediaslist=new ArrayList<DSMedia>();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MUSIC_SWITCH);
        this.registerReceiver(receiver,filter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(MusicListActivity.this,"加载中....",Toast.LENGTH_SHORT).show();
                str=new Gson().toJson(album.musicList);
                Log.d("hahahh",str+"====");
                PlayMedia(str,position);
                //if(str.length()>10){
                //   send("[DSKUGOU]"+str+"@@"+position);
                //}
            }
        });
    }

    public void PlayMedia(String json,int position){
        Intent intent = new Intent();
        intent.putExtra("listupdate",json);
        intent.putExtra("position",position);
        intent.setPackage("com.iflytek.cyber.iot.show.core");
        intent.setAction("android.intent.action.START_HK_MEDIA_SERVICE");
        startService(intent);
    }

    public List<DSMedia> mySort(List<DSMedia> list){
        Collections.sort(list, new Comparator<DSMedia>() {
            @Override
            public int compare(DSMedia o1, DSMedia o2) {
                if(o1.getPostion()>o2.getPostion()){
                    return 1;
                }
                if(o1.getPostion()==o2.getPostion()){
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(ACTION_MUSIC_SWITCH.equals(action)){
                int position = intent.getIntExtra("position",-1);
                adapter.setCurPosition(position);
            }
        }
    };

    void initView(){
        list = (ListView) findViewById(R.id.list);

        TextView nameTv = (TextView) findViewById(R.id.name);

        album = (Album) getIntent().getSerializableExtra("album");

        nameTv.setText(album.name);

        adapter = new MusicAdapter2(this,album.musicList);

        list.setAdapter(adapter);

    }


    private void send(String text) {
        Intent intent = new Intent();
        intent.putExtra("count", text);
        intent.setAction("com.szhklt.service.MainService");
        sendBroadcast(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

}
