package com.geniusgithub.mediarender.player;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;

import com.geniusgithub.mediarender.center.DlnaMediaModel;
import com.geniusgithub.mediarender.util.CommonLog;
import com.geniusgithub.mediarender.util.LogFactory;



public abstract class AbstractMediaPlayEngine implements IBasePlayEngine, OnCompletionListener, 
												OnPreparedListener, OnErrorListener{
	
	private static final CommonLog log = LogFactory.createLog();
	
	protected MediaPlayer   mMediaPlayer;					
	protected DlnaMediaModel mMediaInfo;							   								
	protected Context 		mContext;
	protected int 			mPlayState;   
	
	protected PlayerEngineListener mPlayerEngineListener;
	
	protected abstract boolean prepareSelf();
	protected abstract boolean prepareComplete(MediaPlayer mp);
	
	
	protected  void defaultParam()
	{
		mMediaPlayer = new MediaPlayer();		
		mMediaPlayer.setOnCompletionListener(this);	
		mMediaPlayer.setOnPreparedListener(this);
		mMediaInfo = null;
		mPlayState = PlayState.MPS_NOFILE;

		
	}
	
	public AbstractMediaPlayEngine(Context context){
	
		mContext = context;
		defaultParam();	
	}
	
	public void setPlayerListener(PlayerEngineListener listener){
		mPlayerEngineListener = listener;
	}

	final String TAG = "andy";
	public AudioManager.OnAudioFocusChangeListener mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
		@Override
		public void onAudioFocusChange(int focusChange) {
			switch (focusChange){
				case AudioManager.AUDIOFOCUS_LOSS:
					//长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时，
					//会触发此回调事件，例如播放QQ音乐，网易云音乐等
					//通常需要暂停音乐播放，若没有暂停播放就会出现和其他音乐同时输出声音
//                    showTip( "AUDIOFOCUS_LOSS1111111");
//					if(mMediaPlayer != null) {
//						mMediaPlayer.pause();
//					}

					//释放焦点，该方法可根据需要来决定是否调用
					//若焦点释放掉之后，将不会再自动获得
//                    mAudioManager.abandonAudioFocus(mAudioFocusChange);
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
					//短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，
					//会触发此回调事件，例如播放短视频，拨打电话等。
					//通常需要暂停音乐播放
//					if(mMediaPlayer != null) {
//						mMediaPlayer.pause();
//					}

					Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT111111");
					break;
				case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
					//短暂性丢失焦点并作降音处理
					Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK111111");
					break;
				case AudioManager.AUDIOFOCUS_GAIN:
					//当其他应用申请焦点之后又释放焦点会触发此回调
					//可重新播放音乐
					Log.d(TAG, "AUDIOFOCUS_GAIN111111");
//                    showTip("AUDIO FOCUS");

//						if(mMediaPlayer != null) {
//							mMediaPlayer.start();
//						}

					break;
			}
		}
	};
	//AudioManager mAudioManager;
	void requestFocus(){
//		//1 初始化AudioManager对象
//		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
//		//2 申请焦点
//		mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
	}

	void lossFocus(){
//		if(mAudioManager!=null){
//			mAudioManager.abandonAudioFocus(mAudioFocusChange);
//		}
	}

	@Override
	public void play() {

		switch (mPlayState) {
		case PlayState.MPS_PAUSE:
			mMediaPlayer.start();
			mPlayState = PlayState.MPS_PLAYING;
			performPlayListener(mPlayState);

			requestFocus();
			break;
		case PlayState.MPS_STOP:
			prepareSelf();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void pause() {
		
		switch (mPlayState) {
		case PlayState.MPS_PLAYING:			
			mMediaPlayer.pause();
			mPlayState = PlayState.MPS_PAUSE;
			performPlayListener(mPlayState);

			lossFocus();


			break;
		default:
			break;
		}
	
	}

	@Override
	public void stop() {
		if (mPlayState != PlayState.MPS_NOFILE){
			mMediaPlayer.reset();
			mPlayState = PlayState.MPS_STOP;
			performPlayListener(mPlayState);
		}
	}
	
	
	@Override
	public void skipTo(int time) {
		
		switch (mPlayState) {
			case PlayState.MPS_PLAYING:
			case PlayState.MPS_PAUSE:				
				int time2 = reviceSeekValue(time);
				mMediaPlayer.seekTo(time2);
				break;
			default:
				break;
		}
	
	}
	
	
	public void exit(){
		stop();
		mMediaPlayer.release();
		mMediaInfo = null;
		mPlayState = PlayState.MPS_NOFILE;

		//mAudioManager.abandonAudioFocus(mAudioFocusChange);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
	
		prepareComplete(mp);
	}
	
	

	@Override
	public void onCompletion(MediaPlayer mp) {
		log.e("onCompletion...");
		if (mPlayerEngineListener != null){
			mPlayerEngineListener.onTrackPlayComplete(mMediaInfo);
		}
		
	}

	public boolean isPlaying() {
		return mPlayState == PlayState.MPS_PLAYING;
	}

	public boolean isPause(){
		return mPlayState == PlayState.MPS_PAUSE;
	}
	
	public void playMedia(DlnaMediaModel mediaInfo){
		
		if (mediaInfo != null){
			mMediaInfo = mediaInfo;
			prepareSelf();
		}
	}
		
	public int getCurPosition()
	{
		if (mPlayState == PlayState.MPS_PLAYING || mPlayState == PlayState.MPS_PAUSE)
		{
			return mMediaPlayer.getCurrentPosition();
		}
			
		return 0;
	}
	
	public int getDuration(){
		
		switch(mPlayState){
			case PlayState.MPS_PLAYING:
			case PlayState.MPS_PAUSE:
			case PlayState.MPS_PARECOMPLETE:
				return mMediaPlayer.getDuration();
		}
	
		return 0;
	}
	
	public int getPlayState()
	{
		return mPlayState;
	}

	protected void performPlayListener(int playState)
	{
		if (mPlayerEngineListener != null){
			switch(playState){
				case PlayState.MPS_INVALID:
					mPlayerEngineListener.onTrackStreamError(mMediaInfo);
					break;
				case PlayState.MPS_STOP:
					mPlayerEngineListener.onTrackStop(mMediaInfo);
					break;
				case PlayState.MPS_PLAYING:
					mPlayerEngineListener.onTrackPlay(mMediaInfo);
					break;
				case PlayState.MPS_PAUSE:
					mPlayerEngineListener.onTrackPause(mMediaInfo);
					break;
				case PlayState.MPS_PARESYNC:
					mPlayerEngineListener.onTrackPrepareSync(mMediaInfo);
					break;
			}
		}
	}	
	
	private int reviceSeekValue(int value)
	{
		if (value < 0)
		{
			value = 0;
		}
		
		if (value > mMediaPlayer.getDuration())
		{
			value = mMediaPlayer.getDuration();
		}
		
		return value;
	}
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {


		log.e("onError --> what = " + what);
		
		return false;
	}

	

}
