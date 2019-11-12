package com.example.building.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import com.example.building.R;
import com.example.building.adapter.base.BaseRecyclerAdapter;
import com.example.building.adapter.base.CommonHolder;
import com.example.building.bean.AroundInfoBean;

public class AroundInfoAdapter extends BaseRecyclerAdapter<AroundInfoBean> {

    private ItemClickListener clickListener;

    public void setCallClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CommonHolder<AroundInfoBean> setViewHolder(ViewGroup parent) {
        return new AroundInfoHolder(parent.getContext(), parent);
    }

    class AroundInfoHolder extends CommonHolder<AroundInfoBean> {

        AppCompatTextView tvTitle;
        AppCompatTextView tvPhoneNumber;
        AppCompatImageView ivCall;


        public AroundInfoHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_info_around);
            tvTitle = findView(R.id.tvTitle);
            tvPhoneNumber = findView(R.id.tvPhoneNumber);
            ivCall = findView(R.id.ivCall);
        }

        @Override
        public void bindData(AroundInfoBean aroundInfoBean) {
            tvTitle.setText(aroundInfoBean.getTitle());
            tvPhoneNumber.setText(getContext().getString(R.string.text_contact_us_mobile) + aroundInfoBean.getPhoneNumber());
            ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

}
