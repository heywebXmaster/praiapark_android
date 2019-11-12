package com.example.building.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.building.R;
import com.example.building.adapter.base.BaseRecyclerAdapter;
import com.example.building.adapter.base.CommonHolder;
import com.example.building.bean.OrderRecordBean;

public class OrderRecordAdapter extends BaseRecyclerAdapter<OrderRecordBean> {
    @Override
    public CommonHolder<OrderRecordBean> setViewHolder(ViewGroup parent) {
        return new OrderRecordHolder(parent.getContext(), parent);
    }

    class OrderRecordHolder extends CommonHolder<OrderRecordBean> {

        public OrderRecordHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_order_record);
        }

        @Override
        public void bindData(OrderRecordBean orderRecordBean) {

        }
    }
}
