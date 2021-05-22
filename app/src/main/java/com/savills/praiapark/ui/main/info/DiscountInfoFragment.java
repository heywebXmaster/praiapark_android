package com.savills.praiapark.ui.main.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.DiscountInfoAdapter;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.bean.AroundInfoBean;
import com.savills.praiapark.bean.DiscountInfoBean;
import com.savills.praiapark.bean.TrafficInfoBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.databinding.FragmentListBinding;
import com.savills.praiapark.mvp.contract.InfoContract;
import com.savills.praiapark.mvp.presenter.InfoPresenter;
import com.savills.praiapark.ui.main.InfomationFragment;
import com.savills.praiapark.ui.main.PdfFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class DiscountInfoFragment extends BaseFragment<FragmentListBinding> implements InfoContract.InfoView {

    private InfoPresenter infoPresenter;
    private DiscountInfoAdapter infoAdapter;
    List<DiscountInfoBean> list;

    public static DiscountInfoFragment newInstant() {
        return new DiscountInfoFragment();
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
        infoAdapter = new DiscountInfoAdapter();
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
                DiscountInfoBean infoBean = list.get(position);
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
                infoPresenter.getDiscounts();
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
    }

    @Override
    public void showDiscounts(List<DiscountInfoBean> list) {
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
