package com.example.building.ui.main.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.building.R;
import com.example.building.adapter.ItemClickListener;
import com.example.building.adapter.UnitInfoAdapter;
import com.example.building.base.BaseFragment;
import com.example.building.bean.AroundInfoBean;
import com.example.building.bean.TrafficInfoBean;
import com.example.building.bean.UnitInfoBean;
import com.example.building.databinding.FragmentListBinding;
import com.example.building.mvp.contract.InfoContract;
import com.example.building.mvp.presenter.InfoPresenter;
import com.example.building.ui.main.InfomationFragment;
import com.example.building.ui.main.PdfFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class UnitInfoFragment extends BaseFragment<FragmentListBinding> implements InfoContract.InfoView {

    private InfoPresenter infoPresenter;
    private UnitInfoAdapter infoAdapter;
    List<UnitInfoBean> list;

    public static UnitInfoFragment newInstant() {
        return new UnitInfoFragment();
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
        infoAdapter = new UnitInfoAdapter();
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
                UnitInfoBean infoBean = list.get(position);
                ((InfomationFragment) getParentFragment()).startBrotherFragment(PdfFragment.newInstant(infoBean.getTitle(),
                        infoBean.getPdf()));
            }
        });
        dataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                if (list != null) {
                    list.clear();
                }
                infoAdapter.clearDatas();
                infoPresenter.getInfos();
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
    public void showInfos(List<UnitInfoBean> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.addAll(list);
        infoAdapter.addItems(list);
        infoAdapter.notifyDataSetChanged();
        dataBinding.refreshLayout.finishRefresh();
    }

    @Override
    public void showTels(List<AroundInfoBean> list) {

    }
}
