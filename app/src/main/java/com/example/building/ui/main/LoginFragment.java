package com.example.building.ui.main;

import android.view.View;

import com.example.building.R;
import com.example.building.aop.annotation.SingleClick;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.config.AppConfig;
import com.example.building.databinding.FragmentLoginBinding;
import com.example.building.mvp.contract.LoginContract;
import com.example.building.mvp.presenter.LoginPresenter;
import com.example.building.util.LogUtil;
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
        ImmersionBar.with(getActivity())
                .statusBarColor(R.color.black)
                .fitsSystemWindows(true)
                .init();
        startWithPop(MainFragment.newInstant());
    }

    @Override
    public void forgetSuccess() {

    }
}
