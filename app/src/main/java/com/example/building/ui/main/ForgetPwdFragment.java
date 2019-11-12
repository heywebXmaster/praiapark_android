package com.example.building.ui.main;

import android.view.View;

import com.example.building.R;
import com.example.building.aop.annotation.SingleClick;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.databinding.FragmentForgetPwdBinding;
import com.example.building.mvp.contract.LoginContract;
import com.example.building.mvp.presenter.LoginPresenter;

public class ForgetPwdFragment extends BaseFragment<FragmentForgetPwdBinding> implements ClickPresenter, LoginContract.LoginView {

    private LoginPresenter loginPresenter;

    public static ForgetPwdFragment newInstant() {
        return new ForgetPwdFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_forget_pwd;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void setListener() {
        dataBinding.setPresenter(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        hideSoftInput();
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.tvSubmit:
                forget();
                break;
        }
    }

    private void forget() {
        String account = dataBinding.etAccount.getText().toString().trim();
        String pwd = dataBinding.etNewPwd.getText().toString().trim();
        String confirmPwd = dataBinding.etConfirmPwd.getText().toString().trim();
        if (!pwd.equals(confirmPwd)) {
            showToast(getString(R.string.text_register_pwd_not_same));
            return;
        }
        loginPresenter.forget(account, confirmPwd);
    }


    @Override
    public void registerSuccess() {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void forgetSuccess() {
        pop();
    }


}
