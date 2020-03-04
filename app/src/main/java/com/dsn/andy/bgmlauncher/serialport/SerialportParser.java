package com.dsn.andy.bgmlauncher.serialport;

import android.content.Context;

/**
 * Created by dell on 2018/8/9.
 */

public class SerialportParser {

    private static SerialportParser instance;
    private Context mContext;
    private PlayListener listener;
    private SerialportParser(Context context){
        mContext = context;
    }

    public static synchronized SerialportParser getInstance(Context context){
        if(instance == null){
            instance = new SerialportParser(context.getApplicationContext());
        }

        return instance;
    }

    public SerialportParser init(PlayListener listener){
        this.listener = listener;

        return this;
    }


    public void parse(byte[] buffer, int size){
        if(buffer[0] == (byte)0xa6 && buffer[1] == (byte)0xa6 && buffer[2] == (byte)0xa6){
            if(checkSum(buffer,size)){
                byte cmd = buffer[3];

                switch (cmd)
                {
                    case 0x20://播放
                        if(listener != null){
                            listener.onPlayOrPause(true);
                        }
                        break;
                    case 0x21://暂停
                        if(listener != null){
                            listener.onPlayOrPause(false);
                        }
                        break;
                    case 0x22://上一曲
                        if(listener != null){
                            listener.onNextOrPrev(false);
                        }
                        break;
                    case 0x23://下一曲
                        if(listener != null){
                            listener.onNextOrPrev(true);
                        }
                        break;

                    case 0x24://音量+
                        if(listener != null){
                            listener.onVolumeUpOrDown(true);
                        }
                        break;
                    case 0x25://音量-
                        if(listener != null){
                            listener.onVolumeUpOrDown(false);
                        }
                        break;
                    case 0x26:
                        if(listener != null){
                            if(size>=8) listener.onVolumeSet(buffer[8]);
                        }
                        break;
                    case 0x27:
                        if(listener != null){
                            listener.onShutdown();
                        }
                        break;
                    case 0x28:
                        if(listener != null){
                            listener.onReboot();
                        }
                        break;
                    case 0x29:
                        if(listener != null){
                            if(size>=8) listener.onPlayMode(buffer[8]);
                        }
                        break;
                    case 0x2a:
                        if(listener != null){
                            listener.onUp();
                        }
                        break;
                    case 0x2b:
                        if(listener != null){
                            listener.onDown();
                        }
                        break;
                    case 0x2c:
                        if(listener != null){
                            listener.onLeft();
                        }
                        break;
                    case 0x2d:
                        if(listener != null){
                            listener.onRight();
                        }
                        break;
                }
            }
        }else{
            listener.onRead(buffer,size);
        }

    }

    private boolean checkSum(byte[] buffer, int size){
        byte[] command = new byte[size];
        System.arraycopy(buffer,0,command,0,size);
        int sum =0;
        for(int i=0;i<command.length;i++)
        {
            if(i != 6) sum ^=command[i];
        }
        return command[6] == (byte)sum;
    }











}
