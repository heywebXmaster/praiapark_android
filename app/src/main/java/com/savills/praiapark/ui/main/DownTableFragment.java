package com.savills.praiapark.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.adapter.PdfAdapter;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.databinding.FragmentDownloadTableBinding;
import com.savills.praiapark.mvp.contract.SettingContract;
import com.savills.praiapark.mvp.presenter.SettingPresenter;
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

    @Override
    public void showFee(PdfBean pdfBean) {

    }
}
