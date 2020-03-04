package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.dsn.andy.bgmlauncher.adapter.AlbumAdapter;
import com.dsn.andy.bgmlauncher.bean.Album;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/28.
 */

public class OnlineMusicActivity extends Activity implements View.OnClickListener {

    Button pop;
    Button oumei;
    Button rihan;
    Button classic;
    Button minyao;
    Button chun;
    Button recommend;
    Button curBtn;

    GridView grid;

    AlbumAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_online);

        initView();

        pop.performClick();

    }

    void initView(){
        grid = (GridView) findViewById(R.id.albums);
        pop = (Button)findViewById(R.id.type_pop);
        oumei = (Button)findViewById(R.id.type_oumei);
        rihan = (Button)findViewById(R.id.type_rihan);
        classic = (Button)findViewById(R.id.type_class);
        minyao = (Button)findViewById(R.id.type_minyao);
        chun = (Button)findViewById(R.id.type_chun);
        recommend = (Button)findViewById(R.id.type_recommand);
        recommend.setVisibility(View.GONE);

        pop.setOnClickListener(this);
        oumei.setOnClickListener(this);
        rihan.setOnClickListener(this);
        classic.setOnClickListener(this);
        minyao.setOnClickListener(this);
        chun.setOnClickListener(this);
        recommend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(curBtn!=null) {
            curBtn.setBackground(new ColorDrawable(Color.TRANSPARENT));
            curBtn.setGravity(Gravity.CENTER);
        }
        curBtn = (Button)v;
        curBtn.setBackgroundResource(R.drawable.bg_type_select);


        switch (v.getId())
        {
            case R.id.type_pop:

                int[]  popResIds = {R.drawable.ic_pop_album1,R.drawable.ic_pop_album2,R.drawable.ic_pop_album3,
                        R.drawable.ic_pop_album4,R.drawable.ic_pop_album5,R.drawable.ic_pop_album6,
                        R.drawable.ic_pop_album7,R.drawable.ic_pop_album8};
                String[] popNames = {"不老情歌","经典珍藏","那些年，那些歌","且听风吟","世界流行歌曲","日韩流行","天天好歌曲"
                ,"最爱的欧美女声"
                };

                String[][] array = { Album.album11,Album.album12,Album.album13,Album.album14,Album.album15,Album.album16,Album.album17,Album.album18,};

                fill(popResIds,popNames,array);


                break;
            case R.id.type_oumei:
                int[]  oumeiResIds = {R.drawable.ic_oumei_album1,R.drawable.ic_oumei_album2,R.drawable.ic_oumei_album3,
                        R.drawable.ic_oumei_album4,R.drawable.ic_oumei_album5,R.drawable.ic_oumei_album6,
                        R.drawable.ic_oumei_album7,R.drawable.ic_oumei_album8};
                String[] oumeiNames = {"轻快入耳的旋律","最热男声","慢跑音乐","劲爆音乐","灵魂女声","欧美大叔","我就是前奏控"
                        ,"醉人柔顺爵士"
                };
                String[][] array1 = {Album.album21,Album.album22,Album.album23,Album.album24,Album.album25,Album.album26,Album.album27,Album.album28,};
                fill(oumeiResIds,oumeiNames,array1);

                break;
            case R.id.type_rihan:
                int[]  rihanResIds = {R.drawable.ic_rihan_album1,R.drawable.ic_rihan_album2,R.drawable.ic_rihan_album3,
                        R.drawable.ic_rihan_album4,R.drawable.ic_rihan_album5,R.drawable.ic_rihan_album6,
                        R.drawable.ic_rihan_album7,R.drawable.ic_rihan_album8};
                String[] rihanNames = {"梦想音乐","蔡妍","流行经典","魅力女声","日韩影视","治愈疗伤","一代歌姬"
                        ,"男神组合"
                };
                String[][] array2 = {Album.album31,Album.album32,Album.album33,Album.album34,Album.album35,Album.album36,Album.album37,Album.album38,};
                fill(rihanResIds,rihanNames,array2);
                break;
            case R.id.type_class:
                int[]  classicResIds = {R.drawable.ic_classic_album1,R.drawable.ic_classic_album2,R.drawable.ic_classic_album3,
                        R.drawable.ic_classic_album4,R.drawable.ic_classic_album5,R.drawable.ic_classic_album6,
                        R.drawable.ic_classic_album7,R.drawable.ic_classic_album8};
                String[] classicNames = {"经典纯音乐","电子节奏纯音乐","当朗读遇见音乐","柏林爱乐乐团","励志","经典小提琴曲","影视配乐"
                        ,"世界名曲"
                };
                String[][] array3 = {Album.album41,Album.album42,Album.album43,Album.album44,Album.album45,Album.album46,Album.album47,Album.album48,};
                fill(classicResIds,classicNames,array3);
                break;
            case R.id.type_minyao:
                int[]  minyaoResIds = {R.drawable.ic_minyao_album1,R.drawable.ic_minyao_album2,R.drawable.ic_minyao_album3,
                        R.drawable.ic_minyao_album4,R.drawable.ic_minyao_album5,R.drawable.ic_minyao_album6,
                        R.drawable.ic_minyao_album7,R.drawable.ic_minyao_album8};
                String[] minyaoNames = {"清新舒适欧美风","暖心民谣","世界民谣","Christmas","中国风","民谣是毒也是药","民谣里的小清新"
                        ,"童谣"
                };
                String[][] array4 = {Album.album51,Album.album52,Album.album53,Album.album54,Album.album55,Album.album56,Album.album57,Album.album58,};
                fill(minyaoResIds,minyaoNames,array4);

                break;
            case R.id.type_chun:
                int[]  chunResIds = {R.drawable.ic_chun_album1,R.drawable.ic_chun_album2,R.drawable.ic_chun_album3,
                        R.drawable.ic_chun_album4,R.drawable.ic_chun_album5,R.drawable.ic_chun_album6,
                        R.drawable.ic_chun_album7,R.drawable.ic_chun_album8};
                String[] chunNames = {"余音袅袅","宁静舒适","当朗读遇见音乐","流行钢琴曲","睡不着必听","经典古典吉他","大师精选"
                        ,"陶笛精选集"
                };
                String[][] array5 = {Album.album61,Album.album62,Album.album63,Album.album64,Album.album65,Album.album66,Album.album67,Album.album68,};
                fill(chunResIds,chunNames,array5);
                break;
            case R.id.type_recommand:
                int[]  recommendResIds = {R.drawable.ic_recommend_album1,R.drawable.ic_recommend_album2,R.drawable.ic_recommend_album3,
                        R.drawable.ic_recommend_album4,R.drawable.ic_recommend_album5,R.drawable.ic_recommend_album6,
                        R.drawable.ic_recommend_album7,R.drawable.ic_recommend_album8};
                String[] recommendNames = {"聆墨阁","古灵精怪小清新","古筝慢慢弹","埙","尺八乐曲","朝花惜时","玄舞花下"
                        ,"逼哥夜話第二季"
                };
                fill(recommendResIds,recommendNames,null);
                break;
        }
    }

    public void fill(int[] resIds,String[] names,String[][] array){
        ArrayList<Album> albums = new ArrayList<Album>();

        for(int i=0;i<resIds.length;i++){
            Album a = new Album();
            a.resId = resIds[i];
            a.name = names[i];
            if(i<array.length) a.musicList = Album.createAlbum2(array[i]);
            albums.add(a);
        }

        adapter = new AlbumAdapter(this,albums);
        grid.setAdapter(adapter);

        grid.setSelector(new ColorDrawable(Color.TRANSPARENT));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album album = adapter.getItem(position);
                if(album == null) return;
                Intent it = new Intent(OnlineMusicActivity.this,MusicListActivity.class);
                it.putExtra("album",album);
                startActivity(it);
            }
        });
    }
}
