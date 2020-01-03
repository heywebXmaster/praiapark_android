package com.savills.praiapark.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.App;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.bean.ClauseBean;
import com.savills.praiapark.bean.PushTokenEvent;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.databinding.FragmentMainBinding;
import com.savills.praiapark.mvp.contract.ClauseContract;
import com.savills.praiapark.mvp.contract.PushTokenInitContract;
import com.savills.praiapark.mvp.presenter.ClausePresenter;
import com.savills.praiapark.mvp.presenter.PushTokenInitPresenter;
import com.savills.praiapark.ui.user.ContactUsFragment;
import com.savills.praiapark.ui.user.SettingFragment;
import com.savills.praiapark.util.LogUtil;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainFragment extends BaseFragment<FragmentMainBinding> implements View.OnClickListener {

    private SupportFragment[] fragmentList = new SupportFragment[7];
    private int preIndex = 0;
    private FrameLayout layoutNotice;
    private FrameLayout layoutFee;
    private FrameLayout layoutService;
    private FrameLayout layoutApply;
    private FrameLayout layoutContact;
    private FrameLayout layoutInfo;
    private FrameLayout layoutSetting;
    private FrameLayout layoutExit;
    private LinearLayout ll_nav_header;
    private PushTokenInitPresenter initPresenter;

    public enum FragmentEnum {
        HomeFragment(0),
        ManagementFeeFragment(1),
        ClubServiceFragment(2),
        DownTableFragment(3),
        ContactUsFragment(4),
        InfomationFragment(5),
        SettingFragment(6);

        private int value;

        private FragmentEnum(int value) {    //    必须是private的，否则编译错误
            this.value = value;
        }

    }

    public static MainFragment newInstant() {
        return new MainFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        SupportFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            fragmentList[FragmentEnum.HomeFragment.value] = HomeFragment.newInstant();
            fragmentList[FragmentEnum.ManagementFeeFragment.value] = ManagementFeeFragment.newInstant();
            fragmentList[FragmentEnum.ClubServiceFragment.value] = ClubServiceFragment.newInstant();
            fragmentList[FragmentEnum.DownTableFragment.value] = DownTableFragment.newInstant();
            fragmentList[FragmentEnum.ContactUsFragment.value] = ContactUsFragment.newInstant();
            fragmentList[FragmentEnum.InfomationFragment.value] = InfomationFragment.newInstant();
            fragmentList[FragmentEnum.SettingFragment.value] = SettingFragment.newInstant();
            loadMultipleRootFragment(R.id.frameLayout, 0,
                    fragmentList[FragmentEnum.HomeFragment.value],
                    fragmentList[FragmentEnum.ManagementFeeFragment.value],
                    fragmentList[FragmentEnum.ClubServiceFragment.value],
                    fragmentList[FragmentEnum.DownTableFragment.value],
                    fragmentList[FragmentEnum.ContactUsFragment.value],
                    fragmentList[FragmentEnum.InfomationFragment.value],
                    fragmentList[FragmentEnum.SettingFragment.value]);
        } else {
            fragmentList[FragmentEnum.HomeFragment.value] = firstFragment;
            fragmentList[FragmentEnum.ManagementFeeFragment.value] = findChildFragment(ManagementFeeFragment.class);
            fragmentList[FragmentEnum.ClubServiceFragment.value] = findChildFragment(ClubServiceFragment.class);
            fragmentList[FragmentEnum.DownTableFragment.value] = findChildFragment(DownTableFragment.class);
            fragmentList[FragmentEnum.ContactUsFragment.value] = findChildFragment(ContactUsFragment.class);
            fragmentList[FragmentEnum.InfomationFragment.value] = findChildFragment(InfomationFragment.class);
            fragmentList[FragmentEnum.SettingFragment.value] = findChildFragment(SettingFragment.class);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), dataBinding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        dataBinding.navView.setItemIconTintList(null);
        ConstraintLayout header = (ConstraintLayout) dataBinding.navView.getHeaderView(0);
        ll_nav_header = (LinearLayout) header.findViewById(R.id.ll_nav_header);
        layoutNotice = header.findViewById(R.id.layoutNotice);
        layoutFee = header.findViewById(R.id.layoutFee);
        layoutService = header.findViewById(R.id.layoutService);
        layoutApply = header.findViewById(R.id.layoutApply);
        layoutContact = header.findViewById(R.id.layoutContact);
        layoutInfo = header.findViewById(R.id.layoutInfo);
        layoutSetting = header.findViewById(R.id.layoutSetting);
        layoutExit = header.findViewById(R.id.layoutExit);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        LogUtil.w("fcm getInstanceId failed:" + task.getException());
                        return;
                    }
                    String token = task.getResult().getToken();
                    LogUtil.w("fcm getInstanceId complete:" + token);
                    PushTokenInitPresenter initPresenter = new PushTokenInitPresenter();
                    initPresenter.uploadPushToken(token);
                });
    }

    @Override
    protected void setListener() {
        layoutNotice.setOnClickListener(this);
        layoutFee.setOnClickListener(this);
        layoutService.setOnClickListener(this);
        layoutApply.setOnClickListener(this);
        layoutContact.setOnClickListener(this);
        layoutInfo.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        layoutExit.setOnClickListener(this);
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutNotice:
                setShowFragmentAndSetPreIndex(FragmentEnum.HomeFragment.value);
                break;
            case R.id.layoutFee:
                setShowFragmentAndSetPreIndex(FragmentEnum.ManagementFeeFragment.value);
                break;
            case R.id.layoutService:
                setShowFragmentAndSetPreIndex(FragmentEnum.ClubServiceFragment.value);
                break;
            case R.id.layoutApply:
                setShowFragmentAndSetPreIndex(FragmentEnum.DownTableFragment.value);
                break;
            case R.id.layoutContact:
                setShowFragmentAndSetPreIndex(FragmentEnum.ContactUsFragment.value);
                break;
            case R.id.layoutInfo:
                setShowFragmentAndSetPreIndex(FragmentEnum.InfomationFragment.value);
                break;
            case R.id.layoutSetting:
                setShowFragmentAndSetPreIndex(FragmentEnum.SettingFragment.value);
                break;
            case R.id.layoutExit:
                LocalSaveData.getInstance().setLogin(false);
                startWithPop(LoginFragment.newInstant());
                break;
        }
        onCloseDrawer();
    }

    public void setShowFragmentAndSetPreIndex(int currentIndex) {
        showHideFragment(fragmentList[currentIndex], fragmentList[preIndex]);
        preIndex = currentIndex;
    }

    @Override
    public boolean onBackPressedSupport() {
        if (dataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            dataBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    public void onCloseDrawer() {
        if (dataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            dataBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void onOpenDrawer() {
        if (!dataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            dataBinding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void startBrotherFragment(BaseFragment fragment) {
        start(fragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

}
