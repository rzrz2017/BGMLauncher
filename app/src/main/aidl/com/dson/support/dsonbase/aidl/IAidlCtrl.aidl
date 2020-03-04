// IAidlCtrl.aidl
package com.dson.support.dsonbase.aidl;

import com.dson.support.dsonbase.aidl.IAidlCallback;

// Declare any non-default types here with import statements

interface IAidlCtrl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int getVersion();

    void setCallback(String var1, IAidlCallback var2);

    String doAction(int var1, String var2, String var3, IAidlCallback var4);
}
