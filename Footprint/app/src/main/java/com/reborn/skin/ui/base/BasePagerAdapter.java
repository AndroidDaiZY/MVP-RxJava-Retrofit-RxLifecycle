package com.reborn.skin.ui.base;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by 戴震宇 on 2018/8/9 0009.
 *  fragment pager 基类
 */
public class BasePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public BasePagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

}
