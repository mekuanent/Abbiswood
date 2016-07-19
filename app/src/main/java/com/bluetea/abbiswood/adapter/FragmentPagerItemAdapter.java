package com.bluetea.abbiswood.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

/**
 * Created by MEK on 6/10/2016.
 */
public class FragmentPagerItemAdapter extends FragmentPagerAdapter {
    private final FragmentManager fm;
    private final ArrayList<Model> models;

    public FragmentPagerItemAdapter(FragmentManager fm, ArrayList<Model> models) {
        super(fm);
        this.fm = fm;
        this.models = models;
    }

    @Override
    public Fragment getItem(int position) {
        return models.get(position).fragment;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return models.get(position).title;
    }

    public static class Model{
        public String title;
        public Fragment fragment;

        public Model(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }

}
