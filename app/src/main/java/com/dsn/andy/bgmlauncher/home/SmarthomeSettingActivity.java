package com.dsn.andy.bgmlauncher.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.view.CommomDialog;
import com.dson.smart.common.entity.DsonSmartHostInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/16.
 */

public class SmarthomeSettingActivity extends Activity {

    private static final String APK_SMARTHOME = "smarthome.apk";


    @Bind(R.id.txt_smarthome)
    TextView txtSmarthome;
    @Bind(R.id.layout_sm_display)
    LinearLayout layoutSmDisplay;
    @Bind(R.id.custom_name)
    TextView customName;
    @Bind(R.id.latest_version)
    TextView latestVersion;
    @Bind(R.id.update_infos)
    LinearLayout updateInfos;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.my_progress)
    ProgressBar myProgress;
    @Bind(R.id.progress_label)
    TextView progressLabel;
    @Bind(R.id.new_layout)
    LinearLayout newLayout;

    String downloadUrl;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                install();
                return;
            }
            if (msg.what == 3) {
                progressLabel.setText("正在下载中，请耐心等待...(" + (String) msg.obj + ")");
                return;
            }
            myProgress.setProgress(msg.arg1);
        }
    };
    int progress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smarthome_setting);
        ButterKnife.bind(this);
        DsonSmartHostInfo host = DsonConstants.getInstance().getHostInfo();
        if (host != null) {
            getLaestVersion(host.getUpdateUrl());
            customName.setText(host.getLoginPrompt()+"智能家居");
        } else {
            Toast.makeText(this, "请先安装智能家居插件APP", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @OnClick({R.id.layout_sm_display, R.id.update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_sm_display:
                new CommomDialog(this, R.style.dialog, "请设置是否显示智能家居", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            txtSmarthome.setText("显示");
                        } else {
                            txtSmarthome.setText("隐藏");
                        }
                    }
                }).setPositiveButton("显示").setNegativeButton("隐藏").show();
                break;
            case R.id.update:

                view.setVisibility(View.GONE);
                myProgress.setVisibility(View.VISIBLE);
                progressLabel.setVisibility(View.VISIBLE);

                new Thread() {
                    public void run() {
                        downloadApk(downloadUrl, "dsn", APK_SMARTHOME);
                    }
                }.start();

                break;
        }
    }

    void getLaestVersion(final String path) {

        new Thread(){
            public void run(){
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        //请求成功

                        InputStream is = conn.getInputStream();   //获取输入流，此时才真正建立链接
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader bufferReader = new BufferedReader(isr);
                        String result = "", line;
                        while ((line = bufferReader.readLine()) != null) {
                            result += line;
                        }
                        Log.e("andy","getversion,result="+result);

                        JSONObject json = new JSONObject(result);
                        int code = json.getInt("version_code");

                        int curCode = DsonConstants.getInstance().getHostInfo().getVersionCode();

                        if (code > curCode) {
                            showUpdateInfo(json);
                            downloadUrl = json.getString("url");
                        } else {
                            showNoUpdate(curCode);
                        }

                        is.close();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }.start();

    }


    void showUpdateInfo(final JSONObject json) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newLayout.setVisibility(View.VISIBLE);
                update.setEnabled(true);
                update.setVisibility(View.VISIBLE);
                try {
                    JSONArray infos = json.getJSONArray("new");
                    latestVersion.setText("最新版本：" + json.getInt("version_code") + ",当前版本：" + DsonConstants.getInstance().getHostInfo().getVersionCode());
                    for (int i = 0; i < infos.length(); i++) {
                        JSONObject desc = infos.getJSONObject(i);
                        String v = desc.getString("desc");
                        TextView tv = new TextView(SmarthomeSettingActivity.this);
                        tv.setText((i + 1) + "." + v);
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

    void showNoUpdate(final int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newLayout.setVisibility(View.GONE);
                latestVersion.setText("当前版本已经是最新版本!");
            }
        });
    }


    boolean isDowning;

    /*
     * 下载文件
	 */
    public void downloadApk(String url, String filePath, String fileName) {
        Log.e("andy", url);
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
                if (apkfile.exists()) apkfile.delete();
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
                while ((n = bis.read(buffer, 0, buffer.length)) != -1) {
                    if (!isDowning) {
                        isSuccess = false;
                        break;
                    }

                    bos.write(buffer, 0, n);
                    downLength += n;
                    progress = (int) (((float) downLength / fileLength) * 100);
                    Log.e("andy", "read..." + ((float) downLength / fileLength) * 100);
                    sendMsg(new DecimalFormat("0.00").format(((float) downLength / fileLength) * 100) + "%");
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

    void sendMsg(int progress) {
        Message msg = Message.obtain();
        msg.arg1 = progress;
        msg.what = 1;
        handler.sendMessage(msg);
    }

    void sendMsg(String progress) {
        Message msg = handler.obtainMessage();
        msg.what = 3;
        msg.obj = progress.toString();
        handler.sendMessage(msg);
    }

    public static final String INSTALL_APK = "application/vnd.android.package-archive";

    void install() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File("/mnt/sdcard/dsn/" + APK_SMARTHOME);
        if (!file.exists() || !file.getAbsolutePath().endsWith(".apk")) {
            Log.e("andy", "file not exist.");
            return;
        }
        Log.e("andy", file.getAbsolutePath());
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
