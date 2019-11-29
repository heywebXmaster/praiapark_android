package com.savills.praiapark.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.AnnouncementBean;

public class AnnouncementAdapter extends BaseRecyclerAdapter<AnnouncementBean.AnnouncementItemBean> {
    private ItemClickListener clickListener;
    private int height;

    public AnnouncementAdapter(int height) {
        this.height = height;
    }


    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CommonHolder<AnnouncementBean.AnnouncementItemBean> setViewHolder(ViewGroup parent) {
        return new BroadHolder(parent.getContext(), parent);
    }

    class BroadHolder extends CommonHolder<AnnouncementBean.AnnouncementItemBean> {
        LinearLayout layoutBroadcast;
        AppCompatTextView tvTitle;
        AppCompatTextView tvContent;
        AppCompatImageView ivImg;
        AppCompatTextView tvOriginal;
        AppCompatTextView tvDate;

        public BroadHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_broadcast);
            layoutBroadcast = findView(R.id.layoutBroadcast);
            tvTitle = findView(R.id.tvTitle);
            tvContent = findView(R.id.tvContent);
            ivImg = findView(R.id.ivImg);
            tvOriginal = findView(R.id.tvOriginal);
            tvDate = findView(R.id.tvDate);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivImg.getLayoutParams();
            layoutParams.height = height;
            ivImg.setLayoutParams(layoutParams);
        }

        @Override
        public void bindData(AnnouncementBean.AnnouncementItemBean itemBean) {
            tvTitle.setText(itemBean.getTitle());
//            tvContent.setVisibility(View.GONE);
//            if (itemBean.getIsPdf() != 1 && !StringUtils.isEmpty(itemBean.getContent())) {
//                tvContent.setVisibility(View.VISIBLE);
//                tvContent.setText(Html.fromHtml(itemBean.getContent()));
//            }
//            tvOriginal.setText("第一太平戴維斯物業管理有限公司");
            tvDate.setText(itemBean.getPublishDate());
            if (StringUtils.isEmpty(itemBean.getThumbnail())) {
                ivImg.setVisibility(View.GONE);
            } else {
                ivImg.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(itemBean.getThumbnail()).into(ivImg);
            }
            layoutBroadcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

}
