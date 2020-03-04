package com.dsn.andy.bgmlauncher;

import android.app.Activity;
import android.os.Bundle;

import com.dsn.andy.bgmlauncher.view.CommomDialog;

/**
 * Created by dell on 2017/10/12.
 */

public class AfterSaleServiceActivity extends Activity {
    CommomDialog wificonnectDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale);
    }
}
