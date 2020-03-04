package com.dsn.andy.bgmlauncher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.adapter.MusicHistoryAdapter;
import com.dsn.andy.bgmlauncher.bean.Album;
import com.dsn.andy.bgmlauncher.bean.LocalMusic;
import com.dsn.andy.bgmlauncher.bean.Music;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.SearchDevicesView;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by dell on 2017/10/23.
 */

public class LocalFragment extends Fragment implements View.OnClickListener {

    long beginScanTime;

    Handler handler = new Handler();
    ArrayList<LocalMusic> list = new ArrayList();


    LinearLayout normalLayout;
    Button searchBtn;
    ListView listView;

    LinearLayout searchingLayout;
    SearchDevicesView searchDevicesView;
    TextView searchingDir;
    TextView searchResult;
    Button stopBtn;
    TextView error;

    MusicHistoryAdapter<LocalMusic> adapter;


    public LocalFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_local, null);
        normalLayout = (LinearLayout) layout.findViewById(R.id.normal_layout);
        searchBtn = (Button) layout.findViewById(R.id.scan);
        listView = (ListView) layout.findViewById(R.id.list);

        searchingLayout = (LinearLayout) layout.findViewById(R.id.searching_layout);
        searchDevicesView = (SearchDevicesView) layout.findViewById(R.id.searchView);
        searchDevicesView.setWillNotDraw(false);
        searchingDir = (TextView) layout.findViewById(R.id.searching_dir);
        searchResult = (TextView) layout.findViewById(R.id.search_result);

        stopBtn = (Button) layout.findViewById(R.id.stop_scan);
        error = (TextView) layout.findViewById(R.id.error);

        searchBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);

        adapter = new MusicHistoryAdapter<LocalMusic>(getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album album = convertToMusicList(list);
                album.name = "本地音乐";
                String str = new Gson().toJson(album.musicList);
                Log.d("hahahha", str);
                if (str.length() > 10) {
                    send("[DSKUGOU]" + str + "@@" + position);
                }

            }
        });

        File tf = new File("/mnt/extsd");
        ;
        if (!isAlready(tf)) {
            Log.e("andy", "no tf");
            searchBtn.setEnabled(false);
            listView.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);

        } else {
            Log.e("andy", "tf");
        }

        onSearch();

        return layout;
    }

    private void send(String text) {
        Intent intent = new Intent();
        intent.putExtra("count", text);
        intent.setAction("com.szhklt.service.MainService");
        getActivity().sendBroadcast(intent);
    }

    Album convertToMusicList(ArrayList<LocalMusic> list) {
        Album album = new Album();
        album.name = "本地歌曲";
        album.musicList = new ArrayList<Music>();

        for (LocalMusic m : list) {
            Music music1 = new Music();
            music1.name = m.name;
            music1.path = m.path;
            music1.singer = "";
            music1.local = 1;

            album.musicList.add(music1);
        }

        return album;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
                onSearch();
                break;
            case R.id.stop_scan:
                onStopSearch();

                break;
        }
    }

    void onSearch() {
        File tf = new File("/mnt/extsd");
        ;
        if (isAlready(tf)) {
            list.clear();
            normalLayout.setVisibility(View.GONE);
            searchingLayout.setVisibility(View.VISIBLE);
            searchResult.setText("共扫描到0首歌曲");
            scanMusicFile();
            searchDevicesView.setSearching(true);
        } else {
            findNoTF();
        }


    }

    void onStopSearch() {
        if(list == null || list.isEmpty()){
            findNoMusic();
            return;
        }

        searchingLayout.setVisibility(View.GONE);
        normalLayout.setVisibility(View.VISIBLE);
        searchDevicesView.setSearching(false);

        adapter.notifyDataSetChanged();


    }

    void foundFile(File file) {

        LocalMusic music = new LocalMusic();
        music.name = file.getName();
        music.path = file.getAbsolutePath();
        list.add(music);
        Log.e("andy", "name=" + file.getName());

        adapter.notifyDataSetChanged();

        searchResult.setText("共扫描到" + list.size() + "首歌曲");
    }

    boolean isAlready(File f) {
        if (f.exists()) {
            File[] son = f.listFiles();
            if (son != null) {
                return true;
            }
        }

        return false;

    }

    void showDir(File dir) {
        searchingDir.setText(dir.getAbsolutePath());
    }

    void scanMusicFile() {
        new Thread() {
            public void scanDir(final File file) {

                if (file == null || !file.exists()) {
                    return;
                }

                if (file.isFile() && Utils.isAudioFile(file)) {
                    Log.e("andy", "file=" + file.getName());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            foundFile(file);
                        }
                    });


                    return;
                } else if (file.isDirectory()) {
                    Log.e("andy", "dir=" + file.getAbsolutePath());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            showDir(file);
                        }
                    });


                    File[] files = file.listFiles();
                    if (isAlready(file)) {
                        for (File f : files) {
                            Log.e("andy", "ff=" + f.getName());
                            scanDir(f);
                        }
                    }
                }


            }

            public void run() {
                File tf = new File("/mnt/extsd");
                ;
                if (isAlready(tf)) {
                    scanDir(tf);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onStopSearch();
                        }
                    },2000);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findNoTF();
                        }
                    },2000);
                }


            }
        }.start();
    }

    void findNoTF() {

        normalLayout.setVisibility(View.VISIBLE);
        searchingLayout.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
        error.setText("请先插入TF卡");

        searchDevicesView.setSearching(false);

    }

    void findNoMusic() {

        normalLayout.setVisibility(View.VISIBLE);
        searchingLayout.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);

        searchDevicesView.setSearching(false);

        error.setText("没有找到歌曲!");

    }

}
