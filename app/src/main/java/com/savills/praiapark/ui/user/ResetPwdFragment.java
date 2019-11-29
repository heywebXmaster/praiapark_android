package com.savills.praiapark.ui.user;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.ProfileBean;
import com.savills.praiapark.databinding.FragmentResetPwdBinding;
import com.savills.praiapark.mvp.contract.UserInfoContract;
import com.savills.praiapark.mvp.presenter.UserInfoPresenter;
import com.savills.praiapark.ui.main.LoginFragment;
import com.savills.praiapark.ui.main.MainFragment;

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
//        if (!newPwd.equals(confirmPwd)) {
//            showToast(getString(R.string.text_register_pwd_not_same));
//            return;
//        }
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
        pop();
    }
}
