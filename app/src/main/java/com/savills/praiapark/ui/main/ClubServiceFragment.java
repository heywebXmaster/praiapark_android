package com.savills.praiapark.ui.main;

import android.view.View;

import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.databinding.FragmentClubServiceBinding;

public class ClubServiceFragment extends BaseFragment<FragmentClubServiceBinding> implements ClickPresenter {

    public static ClubServiceFragment newInstant() {
        return new ClubServiceFragment();
    }

    int[] titles = {
            R.string.text_club_service_title_acord,
            R.string.text_club_service_title_map,
            R.string.text_club_service_title_rule};

    @Override
    protected int setViewId() {
        return R.layout.fragment_club_service;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_nav_service));
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        dataBinding.setPresenter(this);
        dataBinding.layoutHeader.ivRight.setVisibility(View.VISIBLE);
        dataBinding.layoutHeader.ivRight.setImageResource(R.mipmap.icon_message);
//        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
//        dataBinding.layoutHeader.ivRight.setVisibility(View.VISIBLE);
//        dataBinding.layoutHeader.ivRight.setImageResource(R.mipmap.icon_add);
//        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(new OrderRecordFragment());
//        fragmentList.add(new ClubMapFragment());
//        fragmentList.add(new ClubRuleFragment());
//        MyPagerAdapter pagerAdapter = new MyPagerAdapter(mContext, getChildFragmentManager(), fragmentList, titles);
//        dataBinding.viewpager.setAdapter(pagerAdapter);
//        dataBinding.slidingTabLayout.setViewPager(dataBinding.viewpager);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                ((MainFragment) getParentFragment()).onOpenDrawer();
                break;
            case R.id.ivRight:
                ((MainFragment) getParentFragment()).startBrotherFragment(NoticeFragment.newInstant());
                break;
//            case R.id.ivRight:
//                ((MainFragment) getParentFragment()).startBrotherFragment(AddOrderFragment.newInstant());
//                break;
            case R.id.layoutAdd:
                ((MainFragment) getParentFragment()).startBrotherFragment(OrderCalendarFragment.newInstant());
                break;
        }
    }
}
