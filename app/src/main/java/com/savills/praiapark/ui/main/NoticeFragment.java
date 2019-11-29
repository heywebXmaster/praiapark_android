package com.savills.praiapark.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.adapter.NoticeAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.AnnouncementBean;
import com.savills.praiapark.bean.NoticeBean;
import com.savills.praiapark.databinding.FragmentNoticeBinding;
import com.savills.praiapark.mvp.contract.NoticeContract;
import com.savills.praiapark.mvp.presenter.NoticePresenter;
import com.savills.praiapark.util.LogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends BaseFragment<FragmentNoticeBinding> implements ClickPresenter, NoticeContract.NoticeView {

    private NoticePresenter noticePresenter;
    List<NoticeBean.NoticeItemBean> list = new ArrayList<>();
    private NoticeAdapter noticeAdapter;
    private int page = 1;

    public static NoticeFragment newInstant() {
        return new NoticeFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_notice));
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        dataBinding.refreshLayout.autoRefresh();
    }

    @Override
    protected void initView() {
        noticePresenter = new NoticePresenter(this);
        noticeAdapter = new NoticeAdapter();
        dataBinding.recyclerView.setAdapter(noticeAdapter);
        noticeAdapter.setItemClickListener(new ItemClickListener() {

            @Override
            public void onClick(int position) {
                LogUtil.d(position + "");
            }
        });
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                list.clear();
                page = 1;
                noticeAdapter.clearDatas();
                noticePresenter.getMessages(page);
            }
        });
        dataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                page++;
                noticePresenter.getMessages(page);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
        }
    }

    @Override
    public void showMessages(List<NoticeBean.NoticeItemBean> list) {
        this.list.addAll(list);
        noticeAdapter.addItems(list);
        noticeAdapter.notifyDataSetChanged();
        processRefreshLayoutStatus(page,list.size(),dataBinding.refreshLayout);
    }

    @Override
    public void showAnnouncements(List<AnnouncementBean.AnnouncementItemBean> list) {

    }

    @Override
    public void showLoadError() {
        super.showLoadError();
        processRefreshLayoutError(page, dataBinding.refreshLayout);
    }
}
