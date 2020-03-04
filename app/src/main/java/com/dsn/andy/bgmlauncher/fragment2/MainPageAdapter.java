package com.dsn.andy.bgmlauncher.fragment2;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * Created by dell on 2018/9/21.


 */

public class MainPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mfragmentList;

    FragmentManager fm;

    public MainPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.mfragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }




    @Override
    public int getCount() {
        return mfragmentList.size();
    }

    public void remove(Fragment fragment){
        mfragmentList.remove(fragment);
        notifyDataSetChanged();
    }

    public void add(Fragment fragment){
        if(!mfragmentList.contains(fragment)) {
            mfragmentList.add(fragment);
        }
        notifyDataSetChanged();
    }

    public void clear(){
        mfragmentList.clear();
    }



    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
