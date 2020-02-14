package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.DevicesBean;

public class DevicesAdapter extends BaseRecyclerAdapter<DevicesBean> {

    private ItemClickListener clickListener;

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CommonHolder<DevicesBean> setViewHolder(ViewGroup parent) {
        return new OrderHolder(parent.getContext(), parent);
    }

    class OrderHolder extends CommonHolder<DevicesBean> {

        AppCompatTextView tvDeviceName;
        AppCompatImageView ivIcon;
        View divider;
        LinearLayout layoutContent;

        public OrderHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_order_list);
            tvDeviceName = findView(R.id.tvDeviceName);
            ivIcon = findView(R.id.ivIcon);
            divider = findView(R.id.divider);
            layoutContent = findView(R.id.layoutContent);
        }

        @Override
        public void bindData(DevicesBean devicesBean) {
            int position = getAdapterPosition();
            int margin = ConvertUtils.dp2px(15f);
            if (devicesBean.getIconId() != 0) {
                ivIcon.setImageResource(devicesBean.getIconId());
                ivIcon.setVisibility(View.VISIBLE);
            } else {
                ivIcon.setVisibility(View.GONE);
            }
            tvDeviceName.setText(devicesBean.getName());
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) layoutContent.getLayoutParams();
            if (position == 0) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_first);
                layoutParams.setMargins(margin, 0, margin, 0);
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
