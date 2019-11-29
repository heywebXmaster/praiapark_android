package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.NoticeBean;

public class NoticeAdapter extends BaseRecyclerAdapter<NoticeBean.NoticeItemBean> {

    private ItemClickListener clickListener;

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CommonHolder<NoticeBean.NoticeItemBean> setViewHolder(ViewGroup parent) {
        return new NoticeHolder(parent.getContext(), parent);
    }

    class NoticeHolder extends CommonHolder<NoticeBean.NoticeItemBean> {
        LinearLayout layoutNotice;
        AppCompatTextView tvTitle;
        AppCompatTextView tvDate;
        AppCompatTextView tvContent;

        public NoticeHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_notice);
            layoutNotice = findView(R.id.layoutNotice);
            tvTitle = findView(R.id.tvTitle);
            tvDate = findView(R.id.tvDate);
            tvContent = findView(R.id.tvContent);
        }

        @Override
        public void bindData(NoticeBean.NoticeItemBean noticeBean) {
            tvTitle.setText(noticeBean.getTitle());
            tvContent.setText(noticeBean.getContent());
            tvDate.setText(noticeBean.getUpdated_at());
            layoutNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
