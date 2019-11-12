package com.example.building.ui.user;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.example.building.R;
import com.example.building.aop.annotation.SingleClick;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.bean.ProfileBean;
import com.example.building.databinding.FragmentResetPwdBinding;
import com.example.building.mvp.contract.UserInfoContract;
import com.example.building.mvp.presenter.UserInfoPresenter;
import com.example.building.ui.main.LoginFragment;
import com.example.building.ui.main.MainFragment;

public class ResetPwdFragment extends BaseFragment<FragmentResetPwdBinding> implements ClickPresenter, TextWatcher, UserInfoContract.UserInfoView {

    private UserInfoPresenter infoPresenter;

    public static ResetPwdFragment newInstant() {
        return new ResetPwdFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_reset_pwd;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_reset_pwd));
    }

    @Override
    protected void initView() {
        dataBinding.layoutHeader.tvright.setEnabled(false);
        dataBinding.layoutHeader.tvright.setVisibility(View.VISIBLE);
        infoPresenter = new UserInfoPresenter(this);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.etOldPwd.addTextChangedListener(this);
        dataBinding.etNewPwd.addTextChangedListener(this);
        dataBinding.etConfirmPwd.addTextChangedListener(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.tvright:
                resetPwd();
                break;

        }
    }

    private void resetPwd() {
        String oldPwd = dataBinding.etOldPwd.getText().toString();
        String newPwd = dataBinding.etNewPwd.getText().toString();
        String confirmPwd = dataBinding.etConfirmPwd.getText().toString();
        if (!newPwd.equals(confirmPwd)) {
            showToast(getString(R.string.text_register_pwd_not_same));
            return;
        }
        infoPresenter.resetPassword(oldPwd,confirmPwd);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!StringUtils.isEmpty(dataBinding.etOldPwd.getText().toString()) &&
                !StringUtils.isEmpty(dataBinding.etNewPwd.getText().toString()) &&
                !StringUtils.isEmpty(dataBinding.etConfirmPwd.getText().toString())) {
            dataBinding.layoutHeader.tvright.setEnabled(true);
        } else {
            dataBinding.layoutHeader.tvright.setEnabled(false);
        }
    }

    @Override
    public void showProfile(ProfileBean profileBean) {

    }

    @Override
    public void updateProfileSuccess() {

    }

    @Override
    public void resetPasswordSuccess() {
        startWithPopTo(LoginFragment.newInstant(), MainFragment.class,true);
    }
}
