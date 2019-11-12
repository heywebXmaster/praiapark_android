package com.example.building.ui.main.service;

import com.example.building.R;
import com.example.building.adapter.OrderRecordAdapter;
import com.example.building.base.BaseFragment;
import com.example.building.bean.OrderRecordBean;
import com.example.building.databinding.FragmentListBinding;

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
