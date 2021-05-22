package com.savills.praiapark.ui.main.info;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.AroundInfoAdapter;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.bean.AroundInfoBean;
import com.savills.praiapark.bean.DiscountInfoBean;
import com.savills.praiapark.bean.TrafficInfoBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.databinding.FragmentListBinding;
import com.savills.praiapark.mvp.contract.InfoContract;
import com.savills.praiapark.mvp.presenter.InfoPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

public class AroundInfoFragment extends BaseFragment<FragmentListBinding> implements InfoContract.InfoView {

    private InfoPresenter infoPresenter;
    List<AroundInfoBean> list;
    private AroundInfoAdapter infoAdapter;

    public static AroundInfoFragment newInstant() {
        return new AroundInfoFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        infoAdapter = new AroundInfoAdapter();
        dataBinding.recyclerView.setAdapter(infoAdapter);
        infoPresenter = new InfoPresenter(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (list == null) {
            dataBinding.refreshLayout.autoRefresh();
        }
    }

    @Override
    protected void setListener() {
        infoAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                callPhoneNumber(position);
            }
        });
        dataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                if (list != null) {
                    list.clear();
                }
                infoAdapter.clearDatas();
                infoPresenter.getTels();
            }
        });
        dataBinding.refreshLayout.setEnableLoadMore(false);
//        dataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
//
//            }
//        });

    }

    @SuppressLint("CheckResult")
    private void callPhoneNumber(int position) {
        AroundInfoBean infoBean = list.get(position);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + infoBean.getPhoneNumber());
                        intent.setData(data);
                        getContext().startActivity(intent);
                    } else {
                        pop();
                    }
                });
    }

    @Override
    public void showLoadError() {
        super.showLoadError();
        dataBinding.refreshLayout.finishRefresh();
        dataBinding.refreshLayout.finishLoadMore();
    }

    @Override
    public void showBusInfos(List<TrafficInfoBean> list) {

    }

    @Override
    public void showDiscounts(List<DiscountInfoBean> list) {

    }

    @Override
    public void showInfos(List<UnitInfoBean> list) {

    }

    @Override
    public void showTels(List<AroundInfoBean> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.addAll(list);
        infoAdapter.addItems(list);
        infoAdapter.notifyDataSetChanged();
        dataBinding.refreshLayout.finishRefresh();
    }
}
