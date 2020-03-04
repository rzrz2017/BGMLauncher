package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.adapter.CategoryAdapter;
import com.dsn.andy.bgmlauncher.adapter.ChannelAdapter;
import com.dsn.andy.bgmlauncher.bean2.Category;
import com.dsn.andy.bgmlauncher.bean2.Channel;
import com.dsn.andy.bgmlauncher.http2.Constants;
import com.dsn.andy.bgmlauncher.http2.GetAccessTokenService;
import com.dsn.andy.bgmlauncher.http2.GetCategoriesService;
import com.dsn.andy.bgmlauncher.http2.GetChannelsService;
import com.dsn.andy.bgmlauncher.http2.GetDomainService;
import com.dsn.andy.bgmlauncher.view.AutoLoadListener;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dell on 2017/10/19.
 */

public class FMRadioActivity extends Activity {

    ListView categoryList;
    GridView channelGrid;

    private String accessToken;
    private String domain;
    ArrayList<Category> categoryArrayList = new ArrayList();
    ArrayList<Channel> channelArrayList = new ArrayList();

    Category curCategory;
    int curPage;
    final int pageSize = 30;
    int total;
    ChannelAdapter adapter;
    final int MAX_CHANNEL_COUNT = 200;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_radio);

        categoryList = (ListView) findViewById(R.id.categorys);
        channelGrid = (GridView) findViewById(R.id.channels);

        queryAccessToken();

    }

    /*
    获取accesstoken
     */
    void queryAccessToken() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetAccessTokenService.HOST)
                .build();
        GetAccessTokenService service = retrofit.create(GetAccessTokenService.class);
        Call<ResponseBody> call = service.getAccessToken(Constants.client_id, Constants.client_secret);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                Log.e("andy", "kjkldsk");
                try {

                    String str = response.body().string();
                    JSONObject json = new JSONObject(str);
                    accessToken = json.getString("access_token");

                    queryDomain();

                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(FMRadioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("andy", "dsddsds");
            }
        });

    }


    /*
    获取域名
     */
    void queryDomain() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetDomainService.HOST)
                .build();
        GetDomainService service = retrofit.create(GetDomainService.class);
        Call<ResponseBody> call = service.getDomain(accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String str = response.body().string();

                    getDomainAddress(str);

                    queryCategory();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    void getDomainAddress(String str) {
        try {
            JSONObject json = new JSONObject(str);
            JSONObject data = json.getJSONObject("data");
            JSONObject m4a = data.getJSONObject("storedaudio_m4a");
            JSONArray mediacenters = m4a.getJSONArray("mediacenters");
            JSONObject obj = mediacenters.getJSONObject(0);
            domain = obj.getString("domain");
        } catch (Exception e) {

        }
    }

    void queryCategory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetCategoriesService.HOST)
                .build();
        GetCategoriesService service = retrofit.create(GetCategoriesService.class);
        Call<ResponseBody> call = service.getCategorys(accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String str = response.body().string();

                    fillCategoryList(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    void getChannel(Category category){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetChannelsService.HOST)
                .build();
        GetChannelsService service = retrofit.create(GetChannelsService.class);
        Call<ResponseBody> call = service.getDomain(category.id,1,accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try{
                    String str = response.body().string();

                    fillChannel(str);
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    void fillCategoryList(String str) {
        categoryArrayList.clear();
        ;
        try {
            JSONObject json = new JSONObject(str);

            JSONArray data = json.getJSONArray("data");
            JSONObject obj = null;
            Category c = null;
            for (int i = 0; i < data.length(); i++) {
                obj = data.getJSONObject(i);
                c = Category.parse(obj);
                if(c == null) continue;
//                Log.e("andy", i + ":" + c.name);
                categoryArrayList.add(c);
            }

            fillCategoryAdapter();


        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FMRadioActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    void fillCategoryAdapter() {

        final CategoryAdapter adapter = new CategoryAdapter(FMRadioActivity.this, categoryArrayList);
        categoryList.setAdapter(adapter);

        categoryList.setSelector(new ColorDrawable(0));


        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category c = categoryArrayList.get(position);
                adapter.setSelection(position);
                curPage = 1;
                curCategory = c;
                queryChannel(c,curPage);

            }
        });

        adapter.setSelection(0);
        Category c = categoryArrayList.get(0);
        curCategory = c;
        curPage = 1;
        queryChannel(c,curPage);

    }

    void queryChannel(Category category,int page){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetChannelsService.HOST)
                .build();
        GetChannelsService service = retrofit.create(GetChannelsService.class);
        Call<ResponseBody> call = service.getDomain(category.id,page,accessToken);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try{
                    String str = response.body().string();

                    fillChannel(str);
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    void fillChannel(final String str){
        if(curPage == 1) {
            channelArrayList.clear();
        }
        try{
            JSONObject json = new JSONObject(str);
            total = json.getInt("total");
            JSONArray data = json.getJSONArray("data");

            JSONObject obj = null;
            Channel channel = null;
            for (int i=0;i<data.length();i++)
            {
                obj = data.getJSONObject(i);
                channel = Channel.parse(obj);
                if(channel == null) continue;
                channelArrayList.add(channel);
            }

            fillChannelList();


        }catch (Exception e){

        }
    }

    void fillChannelList(){
        if(adapter == null) {
            adapter = new ChannelAdapter(this, channelArrayList);
            channelGrid.setAdapter(adapter);

            channelGrid.setSelector(new ColorDrawable(0));

            channelGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Channel channel = channelArrayList.get(position);

                    Intent it = new Intent(FMRadioActivity.this,ChannelActivity.class);
                    it.putExtra("channel",channel);
                    it.putExtra("access_token",accessToken);
                    it.putExtra("domain",domain);
                    startActivity(it);


                }
            });

            AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
            channelGrid.setOnScrollListener(autoLoadListener);
        }else{
            adapter.notifyDataSetChanged();
        }


    }

    AutoLoadListener.AutoLoadCallBack callBack = new AutoLoadListener.AutoLoadCallBack() {

        public void execute() {
            //            Utils.showToast("已经拖动至底部");
            if(curPage*pageSize > MAX_CHANNEL_COUNT){
                return;
            }
            curPage++;

            queryChannel(curCategory,curPage);
        }

    };


}
