package com.dson.support.dsonbase;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dson.support.dsonbase.aidl.IAidlCallback;
import com.dson.support.dsonbase.aidl.IAidlCtrl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dell on 2018/3/14.
 */


public abstract class DsonbaseAidlServer extends Service {
    private static final String TAG = "DsonbaseAidlServer";
    private int mVersion = 1;
    boolean isBind;
    private HashMap<String, IAidlCallback> callBacks = new HashMap();
    private IAidlCtrl.Stub mBinder = new IAidlCtrl.Stub() {
        public String doAction(int action, String format, String data, IAidlCallback callback) throws RemoteException {
            return DsonbaseAidlServer.this.doAidlAction(action, format, data, callback);
        }

        public int getVersion() throws RemoteException {
            return DsonbaseAidlServer.this.mVersion;
        }

        public void setCallback(String cliendId, IAidlCallback callback) throws RemoteException {
            Log.d("DsonbaseAidlServer", ">>>>>>mBaseCallback callback:" + callback + "   cliendId:" + cliendId);
            DsonbaseAidlServer.this.callBacks.put(cliendId, callback);
        }
    };

    @Nullable
    public IBinder onBind(Intent intent) {
        isBind = true;
        return this.mBinder;
    }

    public abstract String doAidlAction(int var1, String var2, String var3, IAidlCallback var4);

    public DsonbaseAidlServer() {
    }

    public DsonbaseAidlServer(int version) {
        this.mVersion = version;
    }



    public boolean onUnbind(Intent intent) {
        Log.d("DsonbaseAidlServer", "onUnbind");
        isBind = false;
        return super.onUnbind(intent);
    }

    protected void notifyCallBackDataChange(int action,int callCode, String data1, String data2) {
        if (!isBind) {
            Log.e("DsonbaseAidlServer","unbind,cannot notify by aidl");
            return;
        }
        Iterator iter = this.callBacks.entrySet().iterator();

        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            IAidlCallback callback = (IAidlCallback)entry.getValue();

            try {
                if(callback != null) {
                    callback.onResult(action,callCode, data1, data2);
                }
            } catch (RemoteException var8) {
                var8.printStackTrace();
            }
        }

    }
}

