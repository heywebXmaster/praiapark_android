package com.example.building.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.building.R;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.databinding.FragmentScanBinding;
import com.gyf.immersionbar.ImmersionBar;

import cn.bingoogolapple.qrcode.core.QRCodeView;

public class ScanFragment extends BaseFragment<FragmentScanBinding> implements QRCodeView.Delegate, ClickPresenter {

    public static ScanFragment newInstant() {
        return new ScanFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_scan;
    }

    @Override
    protected void setTitle() {
//        dataBinding.layoutHeader.setTitle(getString(R.string.title_scan));
    }

    @Override
    protected void initView() {
        ImmersionBar.with(getActivity())
                .statusBarColor(R.color.black)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.zxingview.setDelegate(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();
        dataBinding.zxingview.startCamera();
        dataBinding.zxingview.startSpot();
    }

    @Override
    public void onResume() {
        super.onResume();
        dataBinding.zxingview.showScanRect();
    }

    @Override
    public void onStop() {
        super.onStop();
        dataBinding.zxingview.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBinding.zxingview.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Bundle bundle = new Bundle();
        bundle.putString("scanResult", result);
        setResult(-1, bundle);
        pop();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onClick(View v) {
        pop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ImmersionBar.with(this).init();
    }
}
