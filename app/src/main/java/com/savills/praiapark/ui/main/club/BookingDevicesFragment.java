package com.savills.praiapark.ui.main.club;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.adapter.DevicesAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.bean.BookingBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.databinding.FragmentListBinding;
import com.savills.praiapark.mvp.contract.BookingContract;
import com.savills.praiapark.mvp.presenter.BookingPresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;

public class BookingDevicesFragment extends BaseFragment<FragmentListBinding> implements BookingContract.OrderView {

    private DevicesAdapter devicesAdapter;
    private BookingPresenter bookingPresenter;
    List<DevicesBean> list;

    public static BookingDevicesFragment newInstant() {
        return new BookingDevicesFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setTitle() {
        setSwipeBackEnable(false);
        devicesAdapter = new DevicesAdapter();
        dataBinding.recyclerView.setAdapter(devicesAdapter);
        bookingPresenter = new BookingPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        devicesAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });
        dataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                if (list != null) {
                    list.clear();
                }
                devicesAdapter.clearDatas();
                bookingPresenter.getDevicesList();
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
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.addAll(list);
        devicesAdapter.addItems(list);
        devicesAdapter.notifyDataSetChanged();
        dataBinding.refreshLayout.finishRefresh();
    }

    @Override
    public void uploadBookingSuccess() {

    }

    @Override
    public void showBookingList(List<BookingBean> list) {

    }
}
