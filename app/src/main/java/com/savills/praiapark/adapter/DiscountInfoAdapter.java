package com.savills.praiapark.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.DiscountInfoBean;
import com.savills.praiapark.bean.UnitInfoBean;

public class DiscountInfoAdapter extends BaseRecyclerAdapter<DiscountInfoBean> {
    private ItemClickListener clickListener;

    @Override
    public CommonHolder<DiscountInfoBean> setViewHolder(ViewGroup parent) {
        return new DiscountInfoHolder(parent.getContext(), parent);
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class DiscountInfoHolder extends CommonHolder<DiscountInfoBean> {

        LinearLayout layoutContent;
        View divider;
        AppCompatTextView tvTitle;

        public DiscountInfoHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_info_unit);
            layoutContent = findView(R.id.layoutContent);
            tvTitle = findView(R.id.tvTitle);
            divider = findView(R.id.divider);
        }

        @Override
        public void bindData(DiscountInfoBean discountInfoBean) {
            int position = getAdapterPosition();
            int margin = ConvertUtils.dp2px(15f);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) layoutContent.getLayoutParams();
            divider.setVisibility(View.VISIBLE);
            tvTitle.setText(discountInfoBean.getTitle());
            if (position == 0) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_first);
                layoutParams.setMargins(margin, 0, margin, 0);
                if(getAllData().size()==1){
                    divider.setVisibility(View.GONE);
                }
            } else if (position == getAllData().size() - 1) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_last);
                layoutParams.setMargins(margin, 0, margin, margin);
                divider.setVisibility(View.GONE);
            } else {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_default);
                layoutParams.setMargins(margin, 0, margin, 0);
            }
            layoutContent.setLayoutParams(layoutParams);
            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
