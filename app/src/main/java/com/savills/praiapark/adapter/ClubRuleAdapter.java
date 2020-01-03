package com.savills.praiapark.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.ClubRuleBean;

public class ClubRuleAdapter extends BaseRecyclerAdapter<ClubRuleBean> {
    @Override
    public CommonHolder<ClubRuleBean> setViewHolder(ViewGroup parent) {
        return null;
    }

    class ClubRuleHolder extends CommonHolder<ClubRuleBean> {




        public ClubRuleHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_order);

        }

        @Override
        public void bindData(ClubRuleBean bookingBean) {

        }
    }
}
