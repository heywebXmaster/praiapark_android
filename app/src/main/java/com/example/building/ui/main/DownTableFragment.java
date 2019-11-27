package com.example.building.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.building.R;
import com.example.building.adapter.ItemClickListener;
import com.example.building.adapter.PdfAdapter;
import com.example.building.aop.annotation.SingleClick;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.bean.PdfBean;
import com.example.building.databinding.FragmentDownloadTableBinding;
import com.example.building.mvp.contract.SettingContract;
import com.example.building.mvp.presenter.SettingPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class DownTableFragment extends BaseFragment<FragmentDownloadTableBinding> implements ClickPresenter, SettingContract.SettingView {

    private SettingPresenter settingPresenter;
    private PdfAdapter pdfAdapter;
    private List<PdfBean> list = new ArrayList<>();

    public static DownTableFragment newInstant() {
        return new DownTableFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_download_table;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_download_table));
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        dataBinding.layoutHeader.ivRight.setVisibility(View.VISIBLE);
        dataBinding.layoutHeader.ivRight.setImageResource(R.mipmap.icon_message);
        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
        pdfAdapter = new PdfAdapter();
        dataBinding.recyclerView.setAdapter(pdfAdapter);
        settingPresenter = new SettingPresenter(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        dataBinding.refreshLayout.autoRefresh();
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.setPresenter(this);
        pdfAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                PdfBean pdfBean=list.get(position);
                ((MainFragment) getParentFragment()).
                        startBrotherFragment(PdfFragment.newInstant(pdfBean.getTitle(),
                                pdfBean.getPdf()));
            }
        });
        dataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                list.clear();
                pdfAdapter.clearDatas();
                settingPresenter.getPdfs();
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

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                ((MainFragment) getParentFragment()).onOpenDrawer();
                break;
            case R.id.ivRight:
                ((MainFragment) getParentFragment()).startBrotherFragment(NoticeFragment.newInstant());
                break;
        }
    }


    @Override
    public void contactUsSuccess() {

    }

    @Override
    public void showPdfs(List<PdfBean> list) {
        this.list.addAll(list);
        pdfAdapter.addItems(list);
        pdfAdapter.notifyDataSetChanged();
        dataBinding.refreshLayout.finishRefresh();
    }
}
