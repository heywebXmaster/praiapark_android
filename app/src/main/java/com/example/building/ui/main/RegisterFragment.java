package com.example.building.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.building.R;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.databinding.FragmentRegisterBinding;
import com.example.building.mvp.contract.LoginContract;
import com.example.building.mvp.presenter.LoginPresenter;
import com.example.building.util.LogUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> implements ClickPresenter, LoginContract.LoginView {

    private LoginPresenter loginPresenter;

    public static RegisterFragment newInstant() {
        return new RegisterFragment();
    }

    private String userCode;
    private String encodeKey = "R0kP";
    private String encodeKey2 = "G12Y";
    private String encodeKey3 = "Ji9m";

    @Override
    protected int setViewId() {
        return R.layout.fragment_register;
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

    @Override
    public void onClick(View v) {
        hideSoftInput();
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.layoutAccountNum:
                openScan();
                break;
            case R.id.tvSubmit:
                register();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void openScan() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        startFragmentForResult(ScanFragment.newInstant(), null, -1);
                    }
                });
    }

    private void register() {
        String account = dataBinding.etAccount.getText().toString().trim();
        String pwd = dataBinding.etPwd.getText().toString().trim();
        String confirmPwd = dataBinding.etConfirmPwd.getText().toString().trim();
        if (!pwd.equals(confirmPwd)) {
            showToast(getString(R.string.text_register_pwd_not_same));
            return;
        }
        loginPresenter.register(account, confirmPwd, userCode);
    }

    private void setWidgetWidth() {
        int contentHeight = SizeUtils.getMeasuredHeight(dataBinding.layoutAccountNum);
        LogUtil.d(contentHeight+"");
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) dataBinding.rvAccountNum.getLayoutParams();
//        layoutParams.width = contentHeight + ConvertUtils.dp2px(20.0f);
        layoutParams.height = contentHeight;
        dataBinding.rvAccountNum.setLayoutParams(layoutParams);
        dataBinding.rvAccountNum.postInvalidate();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == -1 && resultCode == -1) {
            userCode = data.getString("scanResult");
            String scancode = userCode.replace(encodeKey,"-")
                    .replace(encodeKey2,"-")
                    .replace(encodeKey3,"-");
            dataBinding.setUserCode(scancode);
            dataBinding.tvAccountNumHint.setText(R.string.text_register_user_account);
        }
    }

    @Override
    public void registerSuccess() {
        startWithPopTo(MainFragment.newInstant(), LoginFragment.class, true);
    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void forgetSuccess() {

    }


}
