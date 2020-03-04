package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.andy.music.Key_1;
import com.dsn.andy.bgmlauncher.adapter.KeyHistoryAdapter;
import com.dsn.andy.bgmlauncher.adapter.SearchMusicAdapter;
import com.dsn.andy.bgmlauncher.bean.Album;
import com.dsn.andy.bgmlauncher.bean.DSMedia;
import com.dsn.andy.bgmlauncher.bean.Music;
import com.dsn.andy.bgmlauncher.db2.DBUtils;
import com.dsn.andy.bgmlauncher.http.SearchMusicListService;
import com.dsn.andy.bgmlauncher.http.Song;
import com.dsn.andy.bgmlauncher.label.TagBaseAdapter;
import com.dsn.andy.bgmlauncher.label.TagCloudLayout;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CommomDialog;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by Administrator on 2017/8/28.
 */

public class SearchActivity extends Activity implements View.OnClickListener{


    Context mContext;

//    GridView gridView;
    ListView listView;
    EditText keyEdt;
    Button searchBtn;

    ImageView clear;


    TagCloudLayout mContainer;
    ArrayList<String> mList;
    TagBaseAdapter mAdapter;

    GridView historyGrid;
    KeyHistoryAdapter adapter;
    private ArrayList<DSMedia> mediaslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext=this;
        mediaslist=new ArrayList<DSMedia>();
        initViews();
    }

    private void initViews(){
        clear = (ImageView) findViewById(R.id.clear);
        historyGrid =(GridView) findViewById(R.id.grid);
        refresh();
        listView = (ListView)findViewById(R.id.list_search_result);
        keyEdt = (EditText) findViewById(R.id.search_key);
        searchBtn = (Button) findViewById(R.id.search);
        searchBtn.setOnClickListener(this);
        clear.setOnClickListener(this);
        mContainer = (TagCloudLayout) findViewById(R.id.container);
        mList = new ArrayList<String>();
        mList.add("体面");
        mList.add("走着走着就散了");
        mList.add("从不放弃");
        mList.add("薛之谦");
        mList.add("张碧晨");
        mList.add("庄心妍");
        mList.add("毛不易");
        mList.add("鹿晗");
        mList.add("周杰伦");
        mList.add("陈奕迅");
        mList.add("张学友");
        mList.add("邓紫棋");


        mAdapter = new TagBaseAdapter(this,mList);
        mContainer.setAdapter(mAdapter);



        mContainer.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                keyEdt.setText(mAdapter.getItem(position));
                onSearch(keyEdt.getText().toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search:
                if(TextUtils.isEmpty(keyEdt.getText().toString())){
                    Toast.makeText(this,"请输入歌曲或歌手名",Toast.LENGTH_SHORT).show();
                    return;
                }
                onSearch(keyEdt.getText().toString());

                break;
            case R.id.clear:
                keyEdt.setText("");
                break;
        }

    }

    void refresh(){
        DBUtils u = DBUtils.getInstance(this);
        adapter = new KeyHistoryAdapter(this,u.listAllKeySearch());
        historyGrid.setAdapter(adapter);

        historyGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keyEdt.setText(adapter.getItem(position).getKey());
                onSearch(adapter.getItem(position).getKey());
            }
        });
    }
String str="";
    void onSearch(final String key) {
        if (!Utils.isWifiConnected(SearchActivity.this)) {
//            CommomDialog wificonnectDialog = new CommomDialog(this, R.style.dialog, "您的wifi尚连接,请连接wifi进行体验!", new CommomDialog.OnCloseListener() {
//                @Override
//                public void onClick(Dialog dialog, boolean confirm) {
//                    if (confirm) {
//                        Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                        startActivity(it);
//                        dialog.dismiss();
//                    }
//                }
//            }).setTitle("提示").setNegativeButton("暂不连接").setPositiveButton("去连接");
//            wificonnectDialog.show();

        } else {
            Key_1 key_1 = new Key_1();
            key_1.setKey(key);
            key_1.setTime(Utils.getTime(this));
            DBUtils u = DBUtils.getInstance(this);
            u.insertKeySearch(key_1);
            refresh();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://mobilecdn.kugou.com/")
                    .build();

            SearchMusicListService service = retrofit.create(SearchMusicListService.class);
            Call<ResponseBody> call = service.getMusicList(1, key, 1, 30);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                    Utils.pw("response=" + response.isSuccess());

                    Song ss = null;
                    final ArrayList<Music> mList = new ArrayList<Music>();
                    try {
                        String str = response.body().string();

                        JSONObject obj = new JSONObject(str);
                        JSONObject data = obj.getJSONObject("data");
                        JSONArray info = data.getJSONArray("info");

                        for (int i = 0; i < info.length(); i++) {
                            JSONObject s = info.getJSONObject(i);
                            Song song = Song.parse(s);

                            Music music = new Music();
                            music.name = song.filename;
                            music.singer = song.singername;
                            mList.add(music);
                        }
                    } catch (Exception e) {
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SearchMusicAdapter sAdapter = new SearchMusicAdapter(SearchActivity.this, mList);
                            listView.setAdapter(sAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Album album = new Album();
                                    album.musicList = mList;
                                    album.name = key;
                                    str=new Gson().toJson(mList);
                                    PlayMedia(str,position);
                                    Log.d("hahahha",str);
//                                    if(str.length()>10){
//                                        send("[DSKUGOU]"+str+"@@"+position);
//                                    }
                            }
                            });
                        }
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("andy", "onfailure=" + t.getMessage());
                }
            });
        }
    }

    public void PlayMedia(String json,int position){
        Intent intent = new Intent();
        intent.putExtra("listupdate",json);
        intent.putExtra("position",position);
        intent.setPackage("com.iflytek.cyber.iot.show.core");
        intent.setAction("android.intent.action.START_HK_MEDIA_SERVICE");
        startService(intent);
    }

    private void startOtherApp(String action){
        Intent it = new Intent(action);
        startActivity(it);
    }
    private void send(String text) {
        Intent intent = new Intent();
        intent.putExtra("count", text);
        intent.setAction("com.szhklt.service.MainService");
        sendBroadcast(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
}
