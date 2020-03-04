package com.dsn.andy.bgmlauncher.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dsn.andy.bgmlauncher.AccountActivity;
import com.dsn.andy.bgmlauncher.R;

/**
 * Created by dell on 2017/10/23.
 */

public class HomeFragment extends Fragment {

    AccountActivity activity;
    public HomeFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View layout = (LinearLayout)LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);

        return layout;
    }
}
