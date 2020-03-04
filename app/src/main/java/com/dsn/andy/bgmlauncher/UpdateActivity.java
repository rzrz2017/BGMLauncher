package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dsn.andy.bgmlauncher.http.GetLatestVersionService;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dell on 2017/10/12.
 */

public class UpdateActivity extends Activity implements View.OnClickListener{


    LinearLayout newLayout;
    Button updateBtn;
    ProgressBar progressBar;
    LinearLayout updateInfos;
    TextView nowVersionTxt;
    TextView upsateTime;

    String downloadUrl;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==2){
                install();
                return;
            }
            if(msg.what ==3){
                upsateTime.setText("正在下载中，请耐心等待...("+(String)msg.obj+")");
                return;
            }
            progressBar.setProgress(msg.arg1);
        }
    };
    int progress;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateBtn = (Button)findViewById(R.id.update);
        progressBar = (ProgressBar) findViewById(R.id.my_progress);
        newLayout = (LinearLayout) findViewById(R.id.new_layout);
        updateInfos = (LinearLayout) findViewById(R.id.update_infos);
        nowVersionTxt = (TextView)findViewById(R.id.latest_version);
        upsateTime = (TextView)findViewById(R.id.progress_label);

        updateBtn.setOnClickListener(this);
        updateBtn.setVisibility(View.GONE);

        try {
            nowVersionTxt.setText("最新版本：??" + ",当前版本：" + Utils.getVersion(UpdateActivity.this));
        }catch (Exception e){

        }


        queryLatestVersion();




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

                    int curCode = Utils.getVersion(UpdateActivity.this);

                    if(code > curCode){
                        showUpdateInfo(json);
                        downloadUrl = json.getString("url");
                    }else{
                        showNoUpdate(curCode);
                    }


                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    void showUpdateInfo(final JSONObject json){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newLayout.setVisibility(View.VISIBLE);
                updateBtn.setEnabled(true);
                updateBtn.setVisibility(View.VISIBLE);
                try {
                    JSONArray infos = json.getJSONArray("new");
                    nowVersionTxt.setText("最新版本："+json.getInt("version_code")+",当前版本："+Utils.getVersion(UpdateActivity.this));
                    for (int i=0;i<infos.length();i++){
                        JSONObject desc = infos.getJSONObject(i);
                        String v = desc.getString("desc");
                        TextView tv = new TextView(UpdateActivity.this);
                        tv.setText((i+1)+"."+v);
                        tv.setTextColor(getResources().getColor(R.color.default_color));
                        tv.setTextSize(16);
                        updateInfos.addView(tv);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void showNoUpdate(final int code){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newLayout.setVisibility(View.GONE);
                nowVersionTxt.setText("当前版本已经是最新版本!");
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.update:
                v.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                upsateTime .setVisibility(View.VISIBLE);

                new Thread(){
                    public void run(){
                        downloadApk(downloadUrl,"dsn","dsn.apk");
                    }
                }.start();

                break;
        }

    }
     boolean isDowning;
    /*
	 * 下载文件
	 */
    public  void downloadApk(String url, String filePath, String fileName) {
        Log.e("andy",url);
        boolean isSuccess = true;
        int progress = 0;
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String savePath = "/mnt/sdcard/" + filePath;
                File dirfile = new File(savePath);
                if (!dirfile.exists()) {
                    dirfile.mkdirs();
                }

                File apkfile = new File(savePath + "/" + fileName);
                if(apkfile.exists()) apkfile.delete();
                apkfile.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(apkfile));
                URL down_url = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) down_url
                        .openConnection();
                BufferedInputStream bis = new BufferedInputStream(
                        connection.getInputStream());
                int fileLength = connection.getContentLength();
                int downLength = 0;
                int n;
                byte[] buffer = new byte[4096];
                isDowning = true;
                while ((n = bis.read(buffer,0,buffer.length)) != -1) {
                    if (!isDowning) {
                        isSuccess = false;
                        break;
                    }

                    bos.write(buffer, 0, n);
                    downLength += n;
                    progress = (int) (((float) downLength / fileLength) * 100);
                    Log.e("andy","read..."+((float) downLength / fileLength) * 100);
                    sendMsg(new DecimalFormat("0.00").format(((float) downLength / fileLength) * 100)+"%");
                    sendMsg(progress);
                }
                bis.close();
                bos.close();
                isDowning = false;
                finish();
                connection.disconnect();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            isDowning = false;
            isSuccess = false;
            e.printStackTrace();
        }
        if (isSuccess) {
            handler.sendEmptyMessage(2);
        } else {

        }
    }

    void sendMsg(int progress){
        Message msg = Message.obtain();
        msg.arg1 = progress;
        msg.what = 1;
        handler.sendMessage(msg);
    }
    void sendMsg(String progress){
        Message msg = handler.obtainMessage();
        msg.what =3;
        msg.obj = progress.toString();
        handler.sendMessage(msg);
    }

    public static final String INSTALL_APK = "application/vnd.android.package-archive";
    void install(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File("/mnt/sdcard/dsn/dsn.apk");
        if (!file.exists()  || !file.getAbsolutePath().endsWith(".apk")) {
            Log.e("andy", "file not exist.");
            return;
        }
        Log.e("andy",file.getAbsolutePath());
        intent.setDataAndType(Uri.fromFile(file),
                INSTALL_APK);
        this.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDowning = false;
    }
}
