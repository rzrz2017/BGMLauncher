package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.dsn.andy.bgmlauncher.adapter.SpeechHelpAdapter;
import com.dsn.andy.bgmlauncher.bean.SpeechHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/1/2.
 */

public class SpeechHelpActivity extends Activity {


    List<SpeechHelp> list=new ArrayList<SpeechHelp>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        getData();
        ListView lv=(ListView)findViewById(R.id.list);
        SpeechHelpAdapter speechHelpAdapter=new SpeechHelpAdapter(SpeechHelpActivity.this,list);
        lv.setAdapter(speechHelpAdapter);
    }
    private void getData(){
        list.clear();
        SpeechHelp speechHelp1=new SpeechHelp(R.drawable.ic_sp_notify,"唤醒词","叮咚叮咚");
        list.add(speechHelp1);
        SpeechHelp speechHelp2=new SpeechHelp(R.drawable.ic_sp_music,"点歌","播放赵雷的成都");
        list.add(speechHelp2);
        SpeechHelp speechHelp3=new SpeechHelp(R.drawable.ic_sp_jipiao,"查询机票","帮我查一下明天上午从深圳到上海的机票");
        list.add(speechHelp3);
        SpeechHelp speechHelp4=new SpeechHelp(R.drawable.ic_sp_joke,"快问快答","陪我聊天");
        list.add(speechHelp4);
        SpeechHelp speechHelp5=new SpeechHelp(R.drawable.ic_sp_translate,"翻译","我明天要去北京翻译成英文怎么说");
        list.add(speechHelp5);
        SpeechHelp speechHelp6=new SpeechHelp(R.drawable.ic_sp_cookbook,"菜谱","糖醋排骨怎么做");
        list.add(speechHelp6);
        SpeechHelp speechHelp7=new SpeechHelp(R.drawable.ic_sp_weather,"天气","深圳明天的天气？");
        list.add(speechHelp7);
        SpeechHelp speechHelp8=new SpeechHelp(R.drawable.ic_sp_shici,"诗词","朗读一首毛泽东的《沁园春 雪》");
        list.add(speechHelp8);
        SpeechHelp speechHelp9=new SpeechHelp(R.drawable.ic_sp_notify,"百度百科","百度一下周杰伦");
        list.add(speechHelp9);
        SpeechHelp speechHelp10=new SpeechHelp(R.drawable.ic_sp_music,"笑话","讲一个笑话");
        list.add(speechHelp10);
        SpeechHelp speechHelp11=new SpeechHelp(R.drawable.ic_sp_shici,"定时","提醒我5分钟后睡觉");
        list.add(speechHelp11);
        SpeechHelp speechHelp12=new SpeechHelp(R.drawable.ic_sp_shici,"穿衣指数","今天该穿什么样的衣服");
        list.add(speechHelp12);
        SpeechHelp speechHelp13=new SpeechHelp(R.drawable.ic_sp_jipiao,"股票","科大讯飞的股票");
        list.add(speechHelp13);
        SpeechHelp speechHelp14=new SpeechHelp(R.drawable.ic_sp_joke,"成语解析","五光十色是什么意思");
        list.add(speechHelp14);
        SpeechHelp speechHelp15=new SpeechHelp(R.drawable.ic_sp_translate,"广播","我想听广播电台");
        list.add(speechHelp15);
        SpeechHelp speechHelp16=new SpeechHelp(R.drawable.ic_sp_cookbook,"算数","1+2+3等于多少");
        list.add(speechHelp16);
        SpeechHelp speechHelp17=new SpeechHelp(R.drawable.ic_sp_weather,"时间","现在几点了");
        list.add(speechHelp17);
        SpeechHelp speechHelp18=new SpeechHelp(R.drawable.ic_sp_shici,"游戏","猜字谜");
        list.add(speechHelp18);

    }
}

