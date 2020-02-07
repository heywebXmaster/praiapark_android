package com.savills.praiapark.ui.main;

import android.content.Intent;
import android.view.View;

import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.config.AppConfig;
import com.savills.praiapark.databinding.FragmentLoginBinding;
import com.savills.praiapark.mvp.contract.LoginContract;
import com.savills.praiapark.mvp.presenter.LoginPresenter;
import com.savills.praiapark.util.LogUtil;
import com.gyf.immersionbar.ImmersionBar;

import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> implements ClickPresenter, LoginContract.LoginView {

    private LoginPresenter loginPresenter;

    public static LoginFragment newInstant() {
        return new LoginFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        ImmersionBar.with(this).init();
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
            case R.id.tvForgetPwd:
                startNewFragment(ForgetPwdFragment.newInstant());
                break;
            case R.id.tvLogin:
                login();
                break;
            case R.id.tvRegister:
                startNewFragment(RegisterFragment.newInstant());
                break;
            case R.id.tvDeclare:
//                startNewFragment(HtmlFragment.newInstant(AppConfig.PRIVACY_POLICY,true));
                break;
        }
    }

    private void login() {
        String account = dataBinding.etAccount.getText().toString().trim();
        String pwd = dataBinding.etPwd.getText().toString().trim();
        loginPresenter.login(account, pwd);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void loginSuccess() {
        ImmersionBar.with(_mActivity)
                .statusBarColor(R.color.black)
                .fitsSystemWindows(true)
                .init();
        Intent intent=new Intent(_mActivity,MainActivity.class);
        startActivity(intent);
        _mActivity.finish();
    }

    @Override
    public void forgetSuccess() {

    }
}
