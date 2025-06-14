package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentStatePagerAdapter;
import androidx.core.view.PagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private int[] titles;
    private Context context;

    public MyPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList, int[] titles) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(titles[position]);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
