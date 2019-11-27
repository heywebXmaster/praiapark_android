package com.example.building.ui.main;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.building.R;
import com.example.building.adapter.MyPagerAdapter;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.databinding.FragmentInfomationBinding;
import com.example.building.ui.main.info.AroundInfoFragment;
import com.example.building.ui.main.info.TrafficInfoFragment;
import com.example.building.ui.main.info.UnitInfoFragment;

import java.util.ArrayList;
import java.util.List;


public class InfomationFragment extends BaseFragment<FragmentInfomationBinding> implements ClickPresenter {

    public static InfomationFragment newInstant() {
        return new InfomationFragment();
    }

    int[] titles = {
            R.string.text_info_title_unit,
            R.string.text_info_title_traffic,
            R.string.text_info_title_around};

    @Override
    protected int setViewId() {
        return R.layout.fragment_infomation;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_nav_info));
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
        dataBinding.layoutHeader.ivRight.setVisibility(View.VISIBLE);
        dataBinding.layoutHeader.ivRight.setImageResource(R.mipmap.icon_message);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(UnitInfoFragment.newInstant());
        fragmentList.add(TrafficInfoFragment.newInstant());
        fragmentList.add(AroundInfoFragment.newInstant());
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(mContext, getChildFragmentManager(), fragmentList, titles);
        dataBinding.viewpager.setOffscreenPageLimit(3);
        dataBinding.viewpager.setAdapter(pagerAdapter);
        dataBinding.slidingTabLayout.setViewPager(dataBinding.viewpager);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                ((MainFragment) getParentFragment()).onOpenDrawer();
                break;
            case R.id.ivRight:
                ((MainFragment) getParentFragment()).startBrotherFragment(NoticeFragment.newInstant());
                break;
        }
    }

    public void startBrotherFragment(BaseFragment fragment){
        ((MainFragment) getParentFragment()).startBrotherFragment(fragment);
    }
}
