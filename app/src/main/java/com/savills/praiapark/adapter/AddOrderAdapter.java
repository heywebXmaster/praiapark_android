package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.OrderBean;

public class AddOrderAdapter extends BaseRecyclerAdapter<OrderBean> {
    @Override
    public CommonHolder<OrderBean> setViewHolder(ViewGroup parent) {
        return new OrderHolder(parent.getContext(), parent);
    }

    class OrderHolder extends CommonHolder<OrderBean> {

        AppCompatTextView tvTime;
        AppCompatTextView tvState;
        AppCompatTextView tvDate;
        View divider;


        public OrderHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_order);
            tvTime = findView(R.id.tvTime);
            tvState = findView(R.id.tvState);
            tvDate = findView(R.id.tvDate);
            divider = findView(R.id.divider);
        }

        @Override
        public void bindData(OrderBean orderBean) {
            divider.setVisibility(getAdapterPosition() == getAllData().size() - 1 ? View.GONE : View.VISIBLE);
        }
    }
}
