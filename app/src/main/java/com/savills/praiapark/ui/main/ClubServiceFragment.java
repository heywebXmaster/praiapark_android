package com.savills.praiapark.ui.main;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.MyPagerAdapter;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.databinding.FragmentClubServiceBinding;
import com.savills.praiapark.ui.main.club.BookingDevicesFragment;
import com.savills.praiapark.ui.main.club.ClubPriceFragment;
import com.savills.praiapark.ui.main.club.ClubRuleFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ClubServiceFragment extends BaseFragment<FragmentClubServiceBinding> implements ClickPresenter {

    public static ClubServiceFragment newInstant() {
        return new ClubServiceFragment();
    }

    int[] titles = {
            R.string.text_club_service_title_order,
            R.string.text_club_service_title_rule,
            R.string.text_club_service_title_price_list};

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
        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        Flowable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        List<Fragment> fragmentList = new ArrayList<>();
                        fragmentList.add(BookingDevicesFragment.newInstant());
                        fragmentList.add(ClubRuleFragment.newInstant());
                        fragmentList.add(ClubPriceFragment.newInstant());
                        MyPagerAdapter pagerAdapter = new MyPagerAdapter(mContext, getChildFragmentManager(), fragmentList, titles);
                        dataBinding.viewpager.setOffscreenPageLimit(3);
                        dataBinding.viewpager.setAdapter(pagerAdapter);
                        dataBinding.slidingTabLayout.setVisibility(View.VISIBLE);
                        dataBinding.slidingTabLayout.setViewPager(dataBinding.viewpager);
                    }
                });

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
//            case R.id.layoutAdd:
//                ((MainFragment) getParentFragment()).startBrotherFragment(BookingCalendarFragment.newInstant());
//                break;
        }
    }

    public void startBrotherFragment(BaseFragment fragment){
        ((MainFragment) getParentFragment()).startBrotherFragment(fragment);
    }
}
