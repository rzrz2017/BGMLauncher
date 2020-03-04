package com.dsn.andy.bgmlauncher;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.dsn.andy.bgmlauncher.http.GetLatestVersionService;
import com.dsn.andy.bgmlauncher.util.ServiceUtils;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dell on 2017/11/23.
 */

public class UpdateService extends Service {
    Timer timer;

    Timer timer2;

    Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        timer2 = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                queryLatestVersion();



            }
        },30000,60000*10);

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ServiceUtils.checkAndStartSmartSDK(UpdateService.this);
                    }
                });
            }
        },20000,20000);


    }

    void queryLatestVersion(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://112.74.187.196/")
                .build();

        GetLatestVersionService service = retrofit.create(GetLatestVersionService.class);
        Call<ResponseBody> call = service.getLatest();
        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    String str = response.body().string();

                    JSONObject json = new JSONObject(str);
                    int code = json.getInt("version_code");

                    int curCode = Utils.getVersion(UpdateService.this);

                    if(code > curCode){
                        Intent it = new Intent(MainActivity.ACTION_UPDATE);
                        sendBroadcast(it);
                    }else{

                    }


                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(timer != null){
            timer.cancel();
        }

        if(timer2 != null){
            timer2.cancel();
        }
    }
}
