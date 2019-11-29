package com.savills.praiapark.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.AnnouncementAdapter;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.AnnouncementBean;
import com.savills.praiapark.bean.NoticeBean;
import com.savills.praiapark.databinding.FragmentHomeBinding;
import com.savills.praiapark.mvp.contract.NoticeContract;
import com.savills.praiapark.mvp.presenter.NoticePresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements ClickPresenter, NoticeContract.NoticeView {

    private NoticePresenter noticePresenter;
    private AnnouncementAdapter announcementAdapter;
    List<AnnouncementBean.AnnouncementItemBean> list = new ArrayList<>();
    int page = 1;

    public static HomeFragment newInstant() {
        return new HomeFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_broadcast));
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
        dataBinding.layoutHeader.ivRight.setVisibility(View.VISIBLE);
        dataBinding.layoutHeader.ivRight.setImageResource(R.mipmap.icon_message);
        int screenWidth = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(60);
        announcementAdapter = new AnnouncementAdapter(screenWidth / 3 * 2);
        dataBinding.recyclerView.setAdapter(announcementAdapter);
        announcementAdapter.setItemClickListener(new ItemClickListener() {

            @Override
            public void onClick(int position) {
                AnnouncementBean.AnnouncementItemBean announcementItemBean = list.get(position);
                if (announcementItemBean.getIsPdf() == 1) {
                    ((MainFragment) getParentFragment()).
                            startBrotherFragment(PdfFragment.newInstant(announcementItemBean.getTitle(),
                                    announcementItemBean.getPdf()));
                } else {
                    ((MainFragment) getParentFragment()).
                            startBrotherFragment(HtmlFragment.newInstant(announcementItemBean.getTitle(),
                                    announcementItemBean.getContent()));
                }
            }
        });
        noticePresenter = new NoticePresenter(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        dataBinding.refreshLayout.autoRefresh();
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                list.clear();
                page = 1;
                dataBinding.refreshLayout.setNoMoreData(false);
                announcementAdapter.clearDatas();
                noticePresenter.getAnnouncements(page);
            }
        });
        dataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                page++;
                noticePresenter.getAnnouncements(page);
            }
        });
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
    public void showMessages(List<NoticeBean.NoticeItemBean> list) {

    }

    @Override
    public void showAnnouncements(List<AnnouncementBean.AnnouncementItemBean> list) {
        this.list.addAll(list);
        announcementAdapter.addItems(list);
        announcementAdapter.notifyDataSetChanged();
        processRefreshLayoutStatus(page, list.size(), dataBinding.refreshLayout);
    }

    @Override
    public void showLoadError() {
        super.showLoadError();
        processRefreshLayoutError(page, dataBinding.refreshLayout);
    }
}
