package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.UnitInfoBean;

public class UnitInfoAdapter extends BaseRecyclerAdapter<UnitInfoBean> {
    private ItemClickListener clickListener;

    @Override
    public CommonHolder<UnitInfoBean> setViewHolder(ViewGroup parent) {
        return new UnitInfoHolder(parent.getContext(), parent);
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class UnitInfoHolder extends CommonHolder<UnitInfoBean> {

        LinearLayout layoutContent;
        View divider;
        AppCompatTextView tvTitle;

        public UnitInfoHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_info_unit);
            layoutContent = findView(R.id.layoutContent);
            tvTitle = findView(R.id.tvTitle);
            divider = findView(R.id.divider);
        }

        @Override
        public void bindData(UnitInfoBean unitInfoBean) {
            int position = getAdapterPosition();
            divider.setVisibility(View.VISIBLE);
            tvTitle.setText(unitInfoBean.getTitle());
            if (position == 0) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_first);
            } else if (position == getAllData().size() - 1) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_last);
                divider.setVisibility(View.GONE);
            } else {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_default);
            }
            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
