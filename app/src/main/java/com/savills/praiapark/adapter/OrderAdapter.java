package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.OrderBean;

public class OrderAdapter extends BaseRecyclerAdapter<OrderBean> {

    private ItemClickListener clickListener;

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CommonHolder<OrderBean> setViewHolder(ViewGroup parent) {
        return new OrderHolder(parent.getContext(), parent);
    }

    class OrderHolder extends CommonHolder<OrderBean> {

        AppCompatTextView tvOrderType;
        View divider;
        LinearLayout layoutContent;

        public OrderHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_order_list);
            tvOrderType = findView(R.id.tvOrderType);
            divider = findView(R.id.divider);
            layoutContent = findView(R.id.layoutContent);
        }

        @Override
        public void bindData(OrderBean orderBean) {
            int position = getAdapterPosition();
            int margin = ConvertUtils.dp2px(15f);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) layoutContent.getLayoutParams();
            if (position == 0) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_first);
                layoutParams.setMargins(margin, margin, margin, 0);
            } else if (position == getAllData().size() - 1) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_last);
                layoutParams.setMargins(margin, 0, margin, margin);
            } else {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_default);
                layoutParams.setMargins(margin, 0, margin, 0);
            }
            layoutContent.setLayoutParams(layoutParams);
            divider.setVisibility(position == getAllData().size() - 1 ? View.GONE : View.VISIBLE);
            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(position);
                }
            });
        }
    }
}
