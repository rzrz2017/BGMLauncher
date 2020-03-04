package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.adapter.ProgramAdapter;
import com.dsn.andy.bgmlauncher.bean.DSMedia;
import com.dsn.andy.bgmlauncher.bean2.Channel;
import com.dsn.andy.bgmlauncher.bean2.Program;
import com.dsn.andy.bgmlauncher.http2.GetProgramsService;
import com.dsn.andy.bgmlauncher.view.AutoLoadListener;
import com.dsn.andy.bgmlauncher.view.CircleTransform;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dell on 2017/10/19.
 */

public class ChannelActivity extends Activity {

    ImageView img;
    TextView title;
    TextView author;
    TextView playCount;
    TextView programCount;
    TextView introduce;
    ListView programList;

   public static Channel channel;
   public static  String accessToken;
    public static String domain;

    int total;
    int curPage =1;

    ProgramAdapter adapter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        channel = (Channel)this.getIntent().getSerializableExtra("channel");
        accessToken = this.getIntent().getStringExtra("access_token");
        domain = this.getIntent().getStringExtra("domain");

        initView();

        queryProgram(channel,curPage);

    }

    void initView(){
        img = (ImageView)findViewById(R.id.img);
        title = (TextView)findViewById(R.id.title);
        author = (TextView)findViewById(R.id.author);
        playCount = (TextView)findViewById(R.id.play_count);
        programCount = (TextView)findViewById(R.id.program_count);
        introduce = (TextView)findViewById(R.id.introduction);
        programList = (ListView)findViewById(R.id.programs);

        Picasso.with(this).load(channel.thumb_medium).transform(new CircleTransform()).into(img);
        title.setText(channel.title);
        author.setText(channel.podcasters_name);
        playCount.setText(channel.playcount);
        programCount.setText(channel.program_count+"");
        introduce.setText(channel.description);


    }

    void queryProgram(Channel channel,int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetProgramsService.HOST)
                .build();
        GetProgramsService service = retrofit.create(GetProgramsService.class);
        Call<ResponseBody> call = service.getProgram(channel.id+"",page+"",accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try{
                    String str = response.body().string();


                    fillProgram(str);

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    public static ArrayList<Program> programArrayList = new ArrayList();
    void fillProgram(final String str){
        if(curPage == 1) {
            programArrayList.clear();
        }
        try{
            JSONObject json = new JSONObject(str);
            total = json.getInt("total");
            JSONArray data = json.getJSONArray("data");

            JSONObject obj = null;
            Program program = null;
            for(int i=0;i<data.length();i++){
                obj = data.getJSONObject(i);
                program = Program.parse(obj);
                if(program == null)continue;
                program.author = channel.podcasters_name;
                programArrayList.add(program);
            }

            fillProgramList();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
List<DSMedia>DSMedia=new ArrayList<DSMedia>();
    String str="";
    void fillProgramList(){
        if(adapter == null) {
            adapter = new ProgramAdapter(this, programArrayList);
            programList.setAdapter(adapter);

            programList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 /*    Program p = programArrayList.get(position);

                    Intent it = new Intent(ChannelActivity.this,FMPlayActivity.class);
                   it.putExtra("program",p);
                    it.putExtra("domain",domain);
                    it.putExtra("img",channel.thumb_medium);
                    it.putExtra("program_list",programArrayList);
                    startActivity(it);*/
                 DSMedia media = null;
                    int i=0;
                 for (Program program:programArrayList){
                     media = new DSMedia();
                     media.setAuther(program.author);
                     media.setDetile(program.description);
                     media.setImgurl(channel.thumb_medium);
                     media.setUrl("http://"+domain+"/"+program.mediainfo_file_path);
                     media.setName(program.title);
                     media.setPostion(i++);
                     DSMedia.add(media);
                 }


                    str=new Gson().toJson(DSMedia);
                     Log.e("111111",  str);
                    if(str.length()>10){
                        send("[DSQINGTING]"+str+"@@"+position);
                    }

                }
            });

            AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
            programList.setOnScrollListener(autoLoadListener);

        }else{
            adapter.notifyDataSetChanged();
        }



    }

    AutoLoadListener.AutoLoadCallBack callBack = new AutoLoadListener.AutoLoadCallBack() {

        public void execute() {
            //            Utils.showToast("已经拖动至底部");
            if(curPage*50 > total){
                return;
            }
            curPage++;

            queryProgram(channel,curPage);
        }

    };
    private void send(String text) {
        Intent intent = new Intent();
        intent.putExtra("count", text);
        intent.setAction("com.szhklt.service.MainService");
        sendBroadcast(intent);
    }
}
