package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.music.Music_1;
import com.andy.music.Program_1;
import com.dsn.andy.bgmlauncher.adapter.ProgramAdapter;
import com.dsn.andy.bgmlauncher.bean2.Program;
import com.dsn.andy.bgmlauncher.db2.DBUtils;
import com.dsn.andy.bgmlauncher.util.FileUtil;
import com.dsn.andy.bgmlauncher.util.Utils;
import com.dsn.andy.bgmlauncher.view.CircleTransform;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by dell on 2017/10/19.
 */

public class FMPlayActivity extends Activity implements View.OnClickListener{

    String domain;

    private final int Duration = 600;  // 动画时长

    private AudioState audioState = AudioState.STATE_STOP;   //音乐播放器状态

    private ImageView btnPre, btnPlay, btnNext;
    private ImageView cdBox;
    private ImageView stroeImg;
    private ImageView playListImg;

    private boolean flag = false;  //标记，控制唱片旋转

//    static Music music;
//    Album album;

    SeekBar seekBar;

    TextView positionTxt;
    TextView durationTxt;

    int duration;
    String thumb;

    boolean isSame = false;

    static Program program;

    String url;

    private ArrayList<Program> list;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Program program1 = (Program) this.getIntent().getSerializableExtra("program");
        domain = this.getIntent().getStringExtra("domain");
        thumb = this.getIntent().getStringExtra("img");
        list = (ArrayList<Program>) this.getIntent().getSerializableExtra("program_list");

        if (program == null || program1.id!=program.id) {
            isSame = false;
            program = program1;
            url = "http://"+domain+"/"+program.mediainfo_file_path;
            initView();
            loadImg();

            play(url);
            Log.e("andy","play url="+url);
            setAudioState(AudioState.STATE_PLAYING);
        } else {

            url = "http://"+domain+"/"+program.mediainfo_file_path;
            initView();
            loadImg();
            isSame = true;


            if(PlayService.mMediaPlayer != null) {
                setAudioState(PlayService.mMediaPlayer.isPlaying() ? AudioState.STATE_PLAYING : AudioState.STATE_PAUSE);
            }
        }


        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayService.ACTION_UPDATE_PROGERSS);
        filter.addAction(PlayService.ACTION_PREPARED);
        filter.addAction(PlayService.ACTION_COMPLETED);
        filter.addAction(PlayService.ACTION_PLAYING);
        filter.addAction(PlayService.ACTION_PAUSE);
        this.registerReceiver(receiver, filter);



        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));

        addToHistory(program_1);

    }

    public static String toTime(int time) {
        String str = "";
        int seconds = time/1000;
        int m = seconds / 60;
        int s = seconds % 60;
        if (m == 0) {
            str = "00:";
        } else if(m<10){
            str = "0" + m + ":";
        }else{
            str = m+":";
        }

        if (s < 10) {
            str += "0" + s;
        } else {
            str += s + "";
        }

        return str;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (PlayService.ACTION_UPDATE_PROGERSS.equals(action)) {
                int progress = intent.getIntExtra("progress", -1);
                seekBar.setProgress(progress);

                duration = intent.getIntExtra("duration", -1);
                if (duration == -1) {
                    return;
                }
                int position = intent.getIntExtra("position", -1);
                positionTxt.setText(toTime(position));

                durationTxt.setText(toTime(duration));

            } else if (PlayService.ACTION_PREPARED.equals(action)) {
                showtip(action);
                flag = true;

                audioState = AudioState.STATE_PLAYING;
                setAudioState(audioState);
            } else if (PlayService.ACTION_COMPLETED.equals(action)) {
                flag = false;

//                audioState = AudioState.STATE_STOP;

                audioNext();

            } else if (PlayService.ACTION_PLAYING.equals(action)) {
                audioState = AudioState.STATE_PLAYING;

                flag = true;
                setAudioState(audioState);
            } else if (PlayService.ACTION_PAUSE.equals(action)) {
                audioState = AudioState.STATE_PAUSE;
                setAudioState(audioState);
                flag = false;
            }
        }
    };

    void showtip(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(PlayActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
    }




    void addToHistory(Music_1 music) {
        DBUtils dbUtils = DBUtils.getInstance(this);
        dbUtils.insertMusic_1(music);
//        dbUtils.close();
    }

//    void onStore(Music_1 music_1) {
//        DBUtils dbUtils = DBUtils.getInstance(this);
//        music_1.setIs_love(true);
//        if (dbUtils.isExist(music_1)) {
//            dbUtils.updateMusic_1(music_1);
//        }else{
//            dbUtils.insertMusic_1(music_1);
//        }
//        dbUtils.close();
//        Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
//    }




    @Override
    protected void onResume() {
        super.onResume();


    }

    TextView nameTxt;
    public static File dcimFile;
    Animation rotate;

    boolean isLove(Program program){
        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));


        DBUtils utils = DBUtils.getInstance(this);
        return utils.isLove(program_1);
    }

    private void initView() {
        positionTxt = (TextView) findViewById(R.id.position);
        durationTxt = (TextView) findViewById(R.id.duration);

        nameTxt = (TextView) findViewById(R.id.name);
        nameTxt.setText(program.title);

        cdBox = (ImageView) findViewById(R.id.sound_img);
        btnNext = (ImageView) findViewById(R.id.next);
        btnPre = (ImageView) findViewById(R.id.prev);
        btnPlay = (ImageView) findViewById(R.id.play);

        stroeImg = (ImageView)findViewById(R.id.store);
        playListImg = (ImageView)findViewById(R.id.playlist);

        btnPlay.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        stroeImg.setOnClickListener(this);
        playListImg.setOnClickListener(this);

        stroeImg.setBackgroundResource(isLove(program)?R.drawable.ic_store_select:R.drawable.ic_store);

        seekBar = (SeekBar) findViewById(R.id.my_progress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                seekTo(progress);
            }
        });






    }

    void loadImg() {

        final String imgUrl = thumb;

        String sss = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.lastIndexOf("!"));
        String imageName = sss;

        File file = new File("/mnt/sdcard/dsn/",
                imageName);

        if (file.exists() && file.length()>1000*10) {
            Log.e("andy","exists..."+isSame);
            dcimFile = file;
            if (isSame) {
                if (PlayService.mMediaPlayer.isPlaying()) {
                    startImg();
                    notifyPlaying();
                } else {
                    Bitmap bm = BitmapFactory.decodeFile(dcimFile.getAbsolutePath());
                    cdBox.setImageBitmap(new CircleTransform().transform(bm));
                    notifyPause();
                }

                isSame = false;
            }else{
                startImg();
                notifyPlaying();
            }
            return;
        }else if(file.exists() && file.length()==0){
            file.delete();
        }

        Log.e("andy","download img");

        //Target
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.e("andy","onBitmapLoaded");
                String sss = imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.lastIndexOf("!"));
                String imageName = sss;
                Log.e("andy","onbitmaploaded "+imageName);

                dcimFile = FileUtil
                        .getDCIMFile(FileUtil.PATH_PHOTOGRAPH, imageName);
                Log.e("andy", "path=" + dcimFile.getAbsolutePath());
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, ostream);
                    ostream.close();
                } catch (Exception e) {
                    // Log.e("andy","exc="+e.getMessage());
//                    e.printStackTrace();
                }

                if (isSame) {
                    if (PlayService.mMediaPlayer.isPlaying()) {
                        startImg();
                        notifyPlaying();
                    } else {
                        Bitmap bm = BitmapFactory.decodeFile(dcimFile.getAbsolutePath());
                        cdBox.setImageBitmap(new CircleTransform().transform(bm));
                        notifyPause();
                    }

                    isSame = false;
                } else {
                    startImg();
                    notifyPlaying();
                }


            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("andy","on bitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("andy","onPrepredlaod");

            }
        };

        //Picasso下载
        Picasso.with(this).load(imgUrl).into(target);
        cdBox.setTag(target);
    }

    void seekTo(int p) {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_SEEK);
        it.putExtra("progress", p);
        startService(it);
    }

    void play(String url) {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_PLAY);
        it.putExtra("url", url);
        startService(it);

        DSNApplication.playingFrom = 3;


    }

    void notifyPlaying() {
        Log.e("andy","notify playing");
        Intent it = new Intent(MainActivity.ACTION_PLAYING);
        sendBroadcast(it);
    }

    void notifyPause() {
        Log.e("andy","notify pause");
        Intent it = new Intent(MainActivity.ACTION_PAUSE);
        sendBroadcast(it);
    }

    void start() {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_START);
        startService(it);
    }

    void pause() {
        Intent it = new Intent(this, PlayService.class);
        it.putExtra("cmd", PlayService.CMD_PAUSE);
        startService(it);

        DSNApplication.playingFrom = 3;
    }

    void addToHistory(Program_1 program) {
        DBUtils dbUtils = DBUtils.getInstance(this);
        boolean isSuccess = dbUtils.insertProgram_1(program);
        Log.e("andy","add program isSuccess="+isSuccess);
//        dbUtils.close();
    }

    private void prepareMusic() {
        //从Assets中获取音频资源
//        cdBox.getDrawable().setLevel(0);
        play(url);

        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));


        addToHistory(program_1);

        startImg();
        notifyPlaying();

    }


    void startImg() {
        Log.e("andy","start img");
        if(dcimFile == null) return;
        Bitmap bm = BitmapFactory.decodeFile(dcimFile.getAbsolutePath());

        if(bm == null) return;

        cdBox.setImageBitmap(new CircleTransform().transform(bm));

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

        cdBox.startAnimation(rotate);


    }

    void pauseImg() {
        if (rotate != null) {
            rotate.cancel();
            rotate = null;
        }
    }


    private void setAudioState(AudioState audioState) {
        this.audioState = audioState;
        if (audioState == AudioState.STATE_PLAYING) {
          btnPlay.setBackgroundResource(R.drawable.ic_pause);
        } else {
           btnPlay.setBackgroundResource(R.drawable.ic_play);
        }
    }

    @Override
    public void onClick(View view) {
        // 一切按键都必须在动画完成的状态下才能被触发

        switch (view.getId()) {
            case R.id.prev:
                flag = false;
                audioPrevious();
                pauseImg();
                notifyPause();
                setAudioState(AudioState.STATE_PAUSE);
                break;
            case R.id.play:
                if (audioState != AudioState.STATE_PLAYING) {
                    if (audioState == AudioState.STATE_STOP) {
                        prepareMusic();
                        setAudioState(AudioState.STATE_PAUSE);
                    } else if (audioState == AudioState.STATE_PAUSE) {
                        start();
                        startImg();
                        notifyPlaying();
                        setAudioState(AudioState.STATE_PLAYING);
                    }
                } else {
                    pause();
                    pauseImg();
                    notifyPause();
                    setAudioState(AudioState.STATE_PAUSE);

                }
                break;
            case R.id.next:
                flag = false;
                pauseImg();
                notifyPause();
                audioNext();
                setAudioState(AudioState.STATE_PAUSE);
                break;
            case R.id.store:
                if(isLove(program)){
                    onNotStore();
                }else {
                    onStore();
                }
                break;
            case R.id.playlist:
                onPlayList();
                break;
            default:
                break;
        }

    }
    PopupWindow popupWindow;
    void onPlayList(){
        final View popupWindowView = getLayoutInflater().inflate(R.layout.pop, null);
        ListView listView = (ListView)popupWindowView.findViewById(R.id.list);
        ProgramAdapter adapter = new ProgramAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pause();
                Program p = list.get(position);

                play(p);
                popupWindow.dismiss();

            }
        });
        popupWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.FILL_PARENT, true);

        popupWindow.setAnimationStyle(R.style.AnimationRightFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xff222222);
        popupWindow.setBackgroundDrawable(dw);

        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main2, null), Gravity.RIGHT, 0, 300);



    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    void onNotStore() {
        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));
        program_1.setIs_love(false);

        DBUtils utils = DBUtils.getInstance(this);
        if(utils.isExistProgram(program_1)) {
            utils.updateProgram_1(program_1);
        }

        stroeImg.setBackgroundResource(R.drawable.ic_store);



        Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();

    }

    void onStore() {
        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));
        program_1.setIs_love(true);

        DBUtils utils = DBUtils.getInstance(this);
        if(utils.isExistProgram(program_1)) {
            utils.updateProgram_1(program_1);
        }

        stroeImg.setBackgroundResource(R.drawable.ic_store_select);



        Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();

    }


    /**
     * 上一首
     */
    private void audioPrevious() {
        pause();
        durationTxt.setText("--:--");
        positionTxt.setText("00:00");
        ArrayList<Program> programs = list;


        int p = 0;
        for (int i = 0; i < programs.size(); i++) {
            if (programs.get(i).id == program.id) {
                p = i - 1;
                break;
            }
        }
        if (p >= 0) {
            program = programs.get(p);
            nameTxt.setText(program.title);

            notifyMusicSwitch(p);
        } else {
            program = programs.get(programs.size() - 1);
            nameTxt.setText(program.title);

            notifyMusicSwitch(programs.size() - 1);
        }
        url = "http://"+domain+"/"+program.mediainfo_file_path;
        play(url);
        loadImg();

        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));

        addToHistory(program_1);
    }

    void play(Program program){
        durationTxt.setText("--:--");
        positionTxt.setText("00:00");
        this.program = program;
        nameTxt.setText(program.title);
        url = "http://"+domain+"/"+program.mediainfo_file_path;
        play(url);
        loadImg();

        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));

        addToHistory(program_1);
    }

    /**
     * 下一首
     */
    private void audioNext() {
        pause();
        durationTxt.setText("--:--");
        positionTxt.setText("00:00");
        ArrayList<Program> programs = list;

        int p = 0;
        for (int i = 0; i < programs.size(); i++) {
            if (programs.get(i).id==program.id) {
                p = i + 1;
                break;
            }
        }
        if (p < programs.size()) {
            program = programs.get(p);
            nameTxt.setText(program.title);

            notifyMusicSwitch(p);

        } else {
            program = programs.get(0);
            nameTxt.setText(program.title);

            notifyMusicSwitch(0);
        }

        url = "http://"+domain+"/"+program.mediainfo_file_path;
        play(url);
        loadImg();

        Program_1 program_1 = new Program_1();
        program_1.setUrl(url);
        program_1.setImg_url(thumb);
        program_1.setTitle(program.title);
        program_1.setTime(Utils.getTime(FMPlayActivity.this));

        addToHistory(program_1);

    }

    void notifyMusicSwitch(int position) {
        Intent it = new Intent(MusicListActivity.ACTION_MUSIC_SWITCH);
        it.putExtra("position", position);
        sendBroadcast(it);
    }

    public enum AudioState {
        STATE_STOP,
        STATE_PAUSE,
        STATE_PREPARE,
        STATE_PLAYING
    }

    boolean running = true;



    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;

        running = false;
        this.unregisterReceiver(receiver);
    }
}
