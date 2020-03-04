// IAidlCallback.aidl
package com.dson.support.dsonbase.aidl;

// Declare any non-default types here with import statements

interface IAidlCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onResult(int action,int code, String data1, String data2);
}
