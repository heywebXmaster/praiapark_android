package com.savills.praiapark.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.OrderRecordBean;

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
