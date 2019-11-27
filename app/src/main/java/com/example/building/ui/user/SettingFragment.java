package com.example.building.ui.user;

import android.view.View;

import com.example.building.BuildConfig;
import com.example.building.R;
import com.example.building.aop.annotation.SingleClick;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.config.AppConfig;
import com.example.building.databinding.FragmentSettingBinding;
import com.example.building.ui.main.HtmlFragment;
import com.example.building.ui.main.MainFragment;

public class SettingFragment extends BaseFragment<FragmentSettingBinding> implements ClickPresenter {


    public static SettingFragment newInstant() {
        return new SettingFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_nav_setting));
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        dataBinding.setVersion(BuildConfig.VERSION_NAME);
        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.setPresenter(this);
    }

    @Override
    protected void setListener() {
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                ((MainFragment) getParentFragment()).onOpenDrawer();
                break;
            case R.id.layoutProfile://个人资料
                ((MainFragment) getParentFragment()).startBrotherFragment(UserProfileFragment.newInstant());
                break;
            case R.id.layoutLang:
                ((MainFragment) getParentFragment()).startBrotherFragment(SettingLangFragment.newInstant());
                break;
            case R.id.layoutAbout://關於
                ((MainFragment) getParentFragment()).startBrotherFragment(HtmlFragment.newInstant(AppConfig.ABOUT_US,false));
                break;
            case R.id.layoutDeclare://隐私政策
                ((MainFragment) getParentFragment()).startBrotherFragment(HtmlFragment.newInstant(AppConfig.PRIVACY_POLICY,false));
                break;
            case R.id.layoutProvision://条款细则
                ((MainFragment) getParentFragment()).startBrotherFragment(HtmlFragment.newInstant(AppConfig.TERM,false));
                break;
        }
    }

}
