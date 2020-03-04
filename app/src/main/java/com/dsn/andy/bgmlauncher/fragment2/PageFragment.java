package com.dsn.andy.bgmlauncher.fragment2;

import android.support.v4.app.Fragment;

import com.dsn.andy.bgmlauncher.MainActivity2;

/**
 * Created by dell on 2018/9/26.
 */

public abstract class PageFragment extends Fragment {

    protected MainActivity2 activity;

    public abstract  void refresh();

    public abstract PageFragment setActivity(MainActivity2 activity);
}
