package com.dsn.andy.bgmlauncher.fragment2;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dsn.andy.bgmlauncher.AccountActivity;
import com.dsn.andy.bgmlauncher.AppsActivity;
import com.dsn.andy.bgmlauncher.FMRadioActivity;
import com.dsn.andy.bgmlauncher.MainActivity2;
import com.dsn.andy.bgmlauncher.R;
import com.dsn.andy.bgmlauncher.SearchActivity;
import com.dsn.andy.bgmlauncher.ToolActivity;
import com.dsn.andy.bgmlauncher.bean2.Weather;
import com.dsn.andy.bgmlauncher.util.FileUtil;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CircleTransform;
import com.dsn.andy.bgmlauncher.view.CommomDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/9/21.
 */

public class MainFragment extends PageFragment{

    View layout;
    @Bind(R.id.btn_search)
    Button btnSearch;
    @Bind(R.id.btn_local)
    Button btnLocal;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.iv_weather)
    ImageView ivWeather;
    @Bind(R.id.tvTemp)
    TextView tvTemp;
    @Bind(R.id.tvWind)
    TextView tvWind;
    @Bind(R.id.recommend)
    RecyclerView recyclerView;
    @Bind(R.id.iv_play_cover)
    ImageView ivPlayCover;
    @Bind(R.id.tvSongInfo)
    TextView tvSongInfo;
    @Bind(R.id.btnPre)
    ImageView btnPre;
    @Bind(R.id.btnPlay)
    ImageView btnPlay;
    @Bind(R.id.btnNext)
    ImageView btnNext;
    @Bind(R.id.musicLayout)
    LinearLayout musicLayout;
    @Bind(R.id.fmLayout)
    LinearLayout fmLayout;
    @Bind(R.id.toolLayout)
    LinearLayout toolLayout;
    @Bind(R.id.moreLayout)
    LinearLayout moreLayout;
    @Bind(R.id.wifi_img)
    ImageView wifiImg;
    @Bind(R.id.layout_weather)
    LinearLayout layoutWeather;



    public MainFragment() {

    }

    @Override
    public MainFragment setActivity(MainActivity2 activity){
        this.activity = activity;

        return this;
    }


    @Override
    public void refresh() {

    }

    public void showWeather(Weather weather) {
        tvTemp.setText(weather.getCity()+" "+weather.getTemp());
        tvWind.setText(weather.getWind());


        String type = weather.getType();
        if(!TextUtils.isEmpty(type)) {
            if (type.contains("多云")) {
                ivWeather.setBackgroundResource(R.drawable.main_bg_icon_cloud);
            } else if (type.contains("雨")) {
                ivWeather.setBackgroundResource(R.drawable.main_bg_icon_rain);
            } else if (type.contains("晴")) {
                ivWeather.setBackgroundResource(R.drawable.main_bg_icon_sunny);
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, layout);

//        layoutWeather.setClickable(true);

        setTimeDate();

        setRecommend();

        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /*
   给语音助手发送广播指令，以便控制播放
    */
    private void sendPlayCmd(int cmd) {
        Intent it = new Intent(MainActivity2.ACTION_PLAY_MUSIC);
        it.putExtra("type", 2);
        it.putExtra("cmd", cmd);
        getActivity().sendBroadcast(it);
    }

    long lastClick = System.currentTimeMillis();
    final long TIME_CLICK_INTEVAL = 1000*60;
    @OnClick({R.id.iv_weather,R.id.tvWind,R.id.tvTemp,R.id.btn_search, R.id.btn_local, R.id.btnPre, R.id.btnPlay, R.id.btnNext, R.id.musicLayout, R.id.fmLayout, R.id.toolLayout, R.id.moreLayout
    ,R.id.layout_weather})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvWind:
            case R.id.iv_weather:
            case R.id.tvTemp:
            case R.id.layout_weather:
                if(System.currentTimeMillis() - lastClick < TIME_CLICK_INTEVAL){
                    Log.e("andy","click time < 60 s");
                  //  return;
                }else{
                    lastClick = System.currentTimeMillis();
                }
                Log.e("andy","layout weather onclick..."+(activity==null));
                if(activity !=null) activity.requestWeather();
                break;
            case R.id.btn_search:
                onSearch();
                break;
            case R.id.btn_local:
                onAccount();
                break;
            case R.id.btnPre:
                sendPlayCmd(2);
                break;
            case R.id.btnPlay:
                if (isPlaying) {
                    sendPlayCmd(0);
                    pause();
                } else {
                    sendPlayCmd(1);
                    playing();
                }
                break;
            case R.id.btnNext:
                sendPlayCmd(3);
                break;
            case R.id.musicLayout:
                startKwMusic();
                break;
            case R.id.fmLayout:
                startVoiceAssistant();
                break;
            case R.id.toolLayout:
                startActivity(new Intent(getActivity(), ToolActivity.class));
                break;
            case R.id.moreLayout:
                startActivity(new Intent(getActivity(), AppsActivity.class));
                break;
        }
    }

    public void startVoiceAssistant(){
        try {
            Intent intent =new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.szhklt.VoiceAssistant", "com.szhklt.activity.MainActivity"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(this.getActivity(), "请稍候.......", Toast.LENGTH_SHORT).show();
        }
    }

//    CommomDialog wificonnectDialog;

    /**
     * wifi开启提示
     */
    public void showWificonnectDialog() {
        //弹出提示框
//        if (wificonnectDialog != null && wificonnectDialog.isShowing()) {
//            return;
//        }
//        wificonnectDialog = new CommomDialog(getActivity(), R.style.dialog, "您的wifi尚连接,请连接wifi进行体验!", new CommomDialog.OnCloseListener() {
//            @Override
//            public void onClick(Dialog dialog, boolean confirm) {
//                if (confirm) {
//                    Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                    startActivity(it);
//                    dialog.dismiss();
//                }
//            }
//        }).setTitle("提示").setNegativeButton("暂不连接").setPositiveButton("去连接");
//        wificonnectDialog.show();
    }


    private void onSearch() {
        Intent it = new Intent(getActivity(), SearchActivity.class);
        startActivity(it);
    }

    private void startKwMusic() {
        String kwPackageName = "cn.kuwo.kwmusiccar";
        Utils.startAPP(getActivity(), kwPackageName);
    }

    private void onAccount() {
        Intent it = new Intent(getActivity(), AccountActivity.class);
        startActivity(it);
    }


    public void setWifiImg(int res) {
        if (wifiImg != null) {
            wifiImg.setImageResource(res);
        }
    }

    public void setTimeDate() {
        setTimeTxt();
        setDateTxt();
    }

    public void setTimeTxt() {
        if (tvTime != null)
            tvTime.setText(Utils.getTime(getActivity()));
    }

    private void setDateTxt() {
        if (tvDate != null)
            tvDate.setText(Utils.getDateStr(getActivity()));
    }

    /*
    设置推荐歌曲的图标数据
     */
    private void setRecommend() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        RecommendAdapter adapter = new RecommendAdapter(this.getActivity(), RecommendUtils.makeData());
        recyclerView.setAdapter(adapter);
    }


    private String getImageName(String imgUrl) {
        String name = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.lastIndexOf("?") == -1 ? imgUrl.length() : imgUrl.lastIndexOf("?"));
        return name;
    }

    private void setImageState(Bitmap bm, boolean isPlaying) {
        ivPlayCover.setImageBitmap(new CircleTransform().transform(bm));

        ivPlayCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.szhklt.service.MainService");
                intent.putExtra("count", "DSintoMUSIC");
                getActivity().sendBroadcast(intent);
            }
        });

        if (isPlaying) {
            playing();
        } else {
            pause();
        }
    }

    void loadImg(final String imgUrl, final boolean isPlaying) {
        String imageName = getImageName(imgUrl);
        File file = new File("/mnt/sdcard/dsn/",
                imageName);
        if (file.exists() && file.length() > 1000 * 10) {

            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (this.isPlaying != isPlaying) {
                setImageState(bm, isPlaying);
                this.isPlaying = isPlaying;
            }

            return;
        } else if (file.exists() && file.length() == 0) {
            file.delete();
        }
        //Target
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String imageName = getImageName(imgUrl);
                File file = FileUtil
                        .getDCIMFile(FileUtil.PATH_PHOTOGRAPH, imageName);
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, ostream);

                    Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                    setImageState(bm, isPlaying);

                } catch (Exception e) {

                } finally {
                    try {
                        if (ostream != null) {
                            ostream.close();
                        }
                    } catch (Exception e) {

                    }

                }


            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("andy", "on bitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("andy", "onPrepredlaod");

            }
        };

        //Picasso下载
        Picasso.with(getActivity()).load(imgUrl).into(target);
        ivPlayCover.setTag(target);
    }


    private boolean isValidImgUrl(String imgUrl) {
        if (imgUrl == null || TextUtils.isEmpty(imgUrl) || imgUrl.contains("{size}") || !imgUrl.startsWith("http")) {
            return false;
        }
        return true;
    }

    boolean isPlaying;

    public void updatePlaying(String name, String img, boolean playing) {
        this.isPlaying = isPlaying;
        if (tvSongInfo == null) return;
        tvSongInfo.setText(name);
        int[] resIds = {
          R.drawable.main_player_cover1,R.drawable.main_player_cover2,R.drawable.main_player_cover3,
                R.drawable.main_player_cover4,R.drawable.main_player_cover5,R.drawable.main_player_conver6,
                R.drawable.main_player_conver7,R.drawable.main_player_conver8,R.drawable.main_player_conver9,
                R.drawable.main_player_convera
        };
        int random = new Random().nextInt(11);
        Log.e("andy","random = "+random);
        int id = resIds[random];
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(),id);
        if (this.isPlaying != playing) {
            setImageState(bm, playing);
            this.isPlaying = playing;
        }

        if (isValidImgUrl(img)) {
            loadImg(img, playing);
        }

    }

    private void pause() {
        if (rotate != null) {
            rotate.cancel();
            rotate = null;
        }

        btnPlay.setBackgroundResource(R.drawable.main_player_pause);
        isPlaying = false;

    }

    private void playing() {
        if (rotate != null) {
            rotate.cancel();
            rotate = null;
        }

        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setFillAfter(true);
        rotate.setRepeatCount(Integer.MAX_VALUE);               //设置重复次数
        rotate.setRepeatMode(Animation.RESTART);    //重新从头执行
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setDuration(1000 * 20);

        ivPlayCover.startAnimation(rotate);

        btnPlay.setBackgroundResource(R.drawable.main_player_play);
        isPlaying = true;

    }

    RotateAnimation rotate;

}
