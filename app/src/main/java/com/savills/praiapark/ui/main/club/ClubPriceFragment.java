package com.savills.praiapark.ui.main.club;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.adapter.UnitInfoAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.databinding.FragmentListBinding;
import com.savills.praiapark.mvp.contract.ClubInfoContract;
import com.savills.praiapark.mvp.presenter.ClubInfoPresenter;
import com.savills.praiapark.ui.main.ClubServiceFragment;
import com.savills.praiapark.ui.main.PdfFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

public class ClubPriceFragment extends BaseFragment<FragmentListBinding> implements ClubInfoContract.ClubInfoView {

    private UnitInfoAdapter unitInfoAdapter;

    public static ClubPriceFragment newInstant() {
        return new ClubPriceFragment();
    }

    private ClubInfoPresenter infoPresenter;
    List<UnitInfoBean> list;

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
        infoPresenter = new ClubInfoPresenter(this);
        unitInfoAdapter = new UnitInfoAdapter();
        dataBinding.recyclerView.setAdapter(unitInfoAdapter);
    }

    @Override
    protected void setListener() {
        unitInfoAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                UnitInfoBean infoBean = list.get(position);
                ((ClubServiceFragment) getParentFragment()).startBrotherFragment(PdfFragment.newInstant(infoBean.getTitle(),
                        infoBean.getPdf()));
            }
        });
        dataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                if (list != null) {
                    list.clear();
                }
                unitInfoAdapter.clearDatas();
                infoPresenter.getClubPriceList();
            }
        });
        dataBinding.refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (list == null) {
            dataBinding.refreshLayout.autoRefresh();
        }
    }

    @Override
    public void showDevicesList(List<DevicesBean> list) {

    }

    @Override
    public void showClubRuleList(List<UnitInfoBean> list) {

    }

    @Override
    public void showClubPriceList(List<UnitInfoBean> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.addAll(list);
        unitInfoAdapter.addItems(list);
        unitInfoAdapter.notifyDataSetChanged();
        dataBinding.refreshLayout.finishRefresh();
    }


}
