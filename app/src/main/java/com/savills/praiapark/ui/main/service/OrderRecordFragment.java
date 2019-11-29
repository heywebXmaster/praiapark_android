package com.savills.praiapark.ui.main.service;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.OrderRecordAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.bean.OrderRecordBean;
import com.savills.praiapark.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderRecordFragment extends BaseFragment<FragmentListBinding> {

    public static OrderRecordFragment newInstant() {
        return new OrderRecordFragment();
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
        List<OrderRecordBean> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new OrderRecordBean());
        }
        OrderRecordAdapter recordAdapter = new OrderRecordAdapter();
        recordAdapter.setDataList(list);
        dataBinding.recyclerView.setAdapter(recordAdapter);
        recordAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {

    }
}
