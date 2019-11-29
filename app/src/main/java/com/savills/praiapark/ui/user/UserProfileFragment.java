package com.savills.praiapark.ui.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.ProfileBean;
import com.savills.praiapark.databinding.FragmentUserProfileBinding;
import com.savills.praiapark.mvp.contract.UserInfoContract;
import com.savills.praiapark.mvp.presenter.UserInfoPresenter;
import com.savills.praiapark.util.KeyboardStateObserver;
import com.savills.praiapark.util.LogUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

public class UserProfileFragment extends BaseFragment<FragmentUserProfileBinding> implements ClickPresenter, UserInfoContract.UserInfoView {

    private UserInfoPresenter infoPresenter;
    private ProfileBean profileBean;
    private String pictureSelectPath = "";
    private String encodeKey = "R0kP";
    private String encodeKey2 = "G12Y";
    private String encodeKey3 = "Ji9m";

    public static UserProfileFragment newInstant() {
        return new UserProfileFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_user_profile;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        infoPresenter.getProfile();
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.text_setting_profile));
    }

    @Override
    protected void initView() {
        infoPresenter = new UserInfoPresenter(this);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.tvright.setEnabled(false);
        dataBinding.layoutHeader.tvright.setVisibility(View.VISIBLE);
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.setPresenter(this);
        KeyboardStateObserver.getKeyboardStateObserver(getActivity()).setKeyboardVisibilityListener(new KeyboardStateObserver.OnKeyboardVisibilityListener() {
            @Override
            public void onKeyboardShow() {
                dataBinding.stview.setVisibility(View.VISIBLE);
                dataBinding.scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        dataBinding.scrollview.scrollTo(0, dataBinding.layoutContent.getBottom());
                    }
                });
            }

            @Override
            public void onKeyboardHide() {
                dataBinding.stview.setVisibility(View.GONE);
                dataBinding.scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        dataBinding.scrollview.scrollTo(0, 0);
                    }
                });
            }
        });
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                hideSoftInput();
                pop();
                break;
            case R.id.tvright:
                updateProfile();
                break;
            case R.id.civHeader:
                pictureSelector();
                break;
            case R.id.layoutChangePwd:
                startNewFragment(ResetPwdFragment.newInstant());
        }
    }

    @SuppressLint("CheckResult")
    private void pictureSelector() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        PictureSelector.create(this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .imageSpanCount(4)
                                .minSelectNum(1)
                                .previewImage(true)
                                .imageFormat(PictureMimeType.PNG)
                                .forResult(99);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("onActivityResult" + requestCode + "," + resultCode);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            pictureSelectPath = selectList.get(0).getPath();
            Glide.with(mContext).load(pictureSelectPath).into(dataBinding.civHeader);
        }
    }

    private void updateProfile() {
        LogUtil.e(profileBean.toString());
        infoPresenter.updateProfile(profileBean.getNickname(), profileBean.getPhoneNumber(), pictureSelectPath);
    }

    @Override
    public void showProfile(ProfileBean profileBean) {
        String hcode = profileBean.getHouseholderCode();
        String newHcode = hcode.replace(encodeKey,"-")
                .replace(encodeKey2,"-")
                .replace(encodeKey3,"-");
        profileBean.setHouseholderCode(newHcode);
        this.profileBean = profileBean;

        dataBinding.layoutHeader.tvright.setEnabled(true);
        dataBinding.setProfile(profileBean);
        if (!StringUtils.isEmpty(profileBean.getAvatar())) {
            LogUtil.e(profileBean.getAvatar());
            Glide.with(mContext).load(profileBean.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//磁盘不缓存
                    .skipMemoryCache(true)
                    .into(dataBinding.civHeader);
        }
    }

    @Override
    public void updateProfileSuccess() {
        dataBinding.layoutContent.setFocusable(true);
        dataBinding.layoutContent.setFocusableInTouchMode(true);
        dataBinding.layoutContent.requestFocus();
    }

    @Override
    public void resetPasswordSuccess() {

    }
}
