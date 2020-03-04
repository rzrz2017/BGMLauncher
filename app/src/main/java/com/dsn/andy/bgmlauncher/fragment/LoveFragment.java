package com.dsn.andy.bgmlauncher.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.andy.music.Music_1;
import com.andy.music.Program_1;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.adapter.MusicHistoryAdapter;
import com.dsn.andy.bgmlauncher.bean.Album;
import com.dsn.andy.bgmlauncher.bean.Music;
import com.dsn.andy.bgmlauncher.bean2.Program;
import com.dsn.andy.bgmlauncher.db2.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/10/23.
 */

public class
LoveFragment extends Fragment implements  View.OnClickListener{

    Button music;
    Button fm;
    ListView listView;

    Button curBtn;


    public LoveFragment(){

    }


    
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_history,null);

        music = (Button)view.findViewById(R.id.music);
        fm = (Button)view.findViewById(R.id.fm);
        listView = (ListView)view.findViewById(R.id.list);

        music.setOnClickListener(this);
        fm.setOnClickListener(this);

        view.findViewById(R.id.clear).setVisibility(View.GONE);

        music.performClick();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(curBtn == v){
            return;
        }
        if(curBtn == null){
            curBtn = (Button)v;
        }else {
            curBtn.setBackground(null);
        }
        curBtn = (Button )v;
        curBtn.setBackgroundResource(R.drawable.bg_select_shape);
        switch (v.getId())
        {
            case R.id.music:
                showMusic();
                break;
            case R.id.fm:
                showFM();
                break;
        }

    }

    public void showMusic(){

        DBUtils utils = DBUtils.getInstance(getContext());
        final List<Music_1> list = utils.queryLove();

        listView.setAdapter(new MusicHistoryAdapter(this.getActivity(),list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music_1 m = list.get(position);
                Music music = covertMusic(m);
                Intent it = new Intent(Intent.ACTION_MAIN);
                ComponentName component = new ComponentName( "com.szhklt.VoiceAssistant", "com.szhklt.activity.MediaPlayActivity");

                it.setComponent(component);
                it.putExtra("music",music);
                it.putExtra("album",convert2Album(list));

                startActivity(it);


            }
        });


    }

    Album convert2Album(List<Music_1> list){
        Album album = new Album();
        album.name = "歌曲收藏";
        album.musicList = new ArrayList();

        for(Music_1 m:list){
            album.musicList.add(covertMusic(m));
        }

        return album;

    }

    Music covertMusic(Music_1 music_1){
        Music music = new Music();
        music.name = music_1.getName();
        music.singer = music_1.getSinger();
        music.url = music_1.getUrl();
        music.imgUrl = music_1.getImgUrl();
        music.local =music_1.getLocal()!=null?(music_1.getLocal()?1:0):0;
        music.path = music_1.getPath();
        return music;
    }

    private Program convert2Program(Program_1 p1){
        Program p = new Program();
        p.title = p1.getTitle();
        String url = p1.getUrl();
        Log.e("andy","convert program ,url="+url);
        int domainStart = "http://".length();
        int domainEnd = url.indexOf("/",domainStart);
        p.mediainfo_file_path = url.substring(domainEnd+1);
        Log.e("andy","filepath="+p.mediainfo_file_path);
        p.imgUrl = p1.getImg_url();
        p.id = (int)(p1.getId().longValue());

        return p;
    }

    ArrayList<Program> convert2ProgramList(List<Program_1> p1List){
        ArrayList<Program> list = new ArrayList();
        for(Program_1 p:p1List){
            list.add(convert2Program(p));
        }

        return list;
    }

    String getDomain(Program_1 p1){
        String url = p1.getUrl();
        int domainStart = "http://".length();
        int domainEnd = url.indexOf("/",domainStart);
        String domain = url.substring(domainStart,domainEnd);
        Log.e("andy","domain="+domain);
        return domain;
    }


    private void showFM(){

        DBUtils utils = DBUtils.getInstance(getContext());
        final List<Program_1> list = utils.queryLoveProgram();

        listView.setAdapter(new MusicHistoryAdapter(this.getActivity(),list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Program_1 p1 = list.get(position);

                Program p = convert2Program(p1);

                Intent it=new Intent();
                ComponentName component = new ComponentName( "com.szhklt.VoiceAssistant", "com.szhklt.activity.MediaPlayActivity");
                it.setComponent(component);
                it.putExtra("position",position);
                it.putExtra("program",p);
                it.putExtra("domain",getDomain(p1));
                it.putExtra("img",p1.getImg_url());
                it.putExtra("program_list",convert2ProgramList(list));
                startActivity(it);
            }
        });

    }
}
