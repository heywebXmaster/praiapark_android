package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.PdfBean;

public class PdfAdapter extends BaseRecyclerAdapter<PdfBean> {
    private ItemClickListener clickListener;

    @Override
    public CommonHolder<PdfBean> setViewHolder(ViewGroup parent) {
        return new UnitInfoHolder(parent.getContext(), parent);
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    class UnitInfoHolder extends CommonHolder<PdfBean> {

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
        public void bindData(PdfBean pdfBean) {
            int position = getAdapterPosition();
            divider.setVisibility(View.VISIBLE);
            tvTitle.setText(pdfBean.getTitle());
            int margin = ConvertUtils.dp2px(15f);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) layoutContent.getLayoutParams();
            if (position == 0) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_first);
                layoutParams.setMargins(margin, margin, margin, 0);
            } else if (position == getAllData().size() - 1) {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_last);
                layoutParams.setMargins(margin, 0, margin, margin);
                divider.setVisibility(View.GONE);
            } else {
                layoutContent.setBackgroundResource(R.drawable.sp_info_unit_default);
                layoutParams.setMargins(margin, 0, margin, 0);
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
