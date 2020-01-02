package com.savills.praiapark.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.ClubPriceBean;
import com.savills.praiapark.bean.ClubRuleBean;

public class ClubPriceAdapter extends BaseRecyclerAdapter<ClubPriceBean> {
    @Override
    public CommonHolder<ClubPriceBean> setViewHolder(ViewGroup parent) {
        return null;
    }

    class ClubPriceHolder extends CommonHolder<ClubPriceBean> {

        public ClubPriceHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_order);

        }

        @Override
        public void bindData(ClubPriceBean bookingBean) {

        }
    }
}
