package com.dsn.andy.bgmlauncher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.dsn.andy.bgmlauncher.fragment.HistoryFragment;
import com.dsn.andy.bgmlauncher.fragment.HomeFragment;
import com.dsn.andy.bgmlauncher.fragment.LocalFragment;
import com.dsn.andy.bgmlauncher.fragment.LoveFragment;
import com.dsn.andy.bgmlauncher.fragment.MarketFragment;

/**
 * Created by dell on 2017/9/1.
 */

public class AccountActivity extends FragmentActivity implements View.OnClickListener {

    HomeFragment homeFragment;
    HistoryFragment historyFragment;
    LoveFragment loveFragment;
    MarketFragment marketFragment;
    LocalFragment localFragment;


    Button curBtn;
    Fragment curFragment;

    Button history;
    Button love;
    Button local;
    Button market;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        homeFragment = new HomeFragment();
        historyFragment = new HistoryFragment();
        loveFragment = new LoveFragment();
        marketFragment = new MarketFragment();
        localFragment = new LocalFragment();

        initViews();
    }

    void initViews(){
        history = (Button) findViewById(R.id.history);
        love = (Button)findViewById(R.id.love);
        local = (Button)findViewById(R.id.local);
        market = (Button)findViewById(R.id.market);

        history.setOnClickListener(this);
        love.setOnClickListener(this);
        local.setOnClickListener(this);
        market.setOnClickListener(this);

        local.performClick();
    }


    @Override
    public void onClick(View v) {
        if(curBtn == v){
            return;
        }
        curBtn = (Button)v;
        switch (v.getId())
        {
            case R.id.local:
                replaceFragment(localFragment);
                break;
            case R.id.love:
                replaceFragment(loveFragment);
                break;
            case R.id.history:
                replaceFragment(historyFragment);
                break;
            case R.id.market:
                replaceFragment(marketFragment);
                break;
        }
    }

    void replaceFragment(Fragment fragment){
        curFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragments,fragment).commit();
    }


}
