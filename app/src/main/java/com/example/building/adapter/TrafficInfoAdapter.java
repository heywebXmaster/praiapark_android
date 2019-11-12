package com.example.building.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.building.R;
import com.example.building.adapter.base.BaseRecyclerAdapter;
import com.example.building.adapter.base.CommonHolder;
import com.example.building.bean.TrafficInfoBean;

public class TrafficInfoAdapter extends BaseRecyclerAdapter<TrafficInfoBean> {
    private ItemClickListener clickListener;

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CommonHolder<TrafficInfoBean> setViewHolder(ViewGroup parent) {
        return new TrafficInfoHolder(parent.getContext(), parent);
    }

    class TrafficInfoHolder extends CommonHolder<TrafficInfoBean> {
        AppCompatTextView tvTitle;
        AppCompatTextView tvFrom;
        AppCompatTextView tvTo;
        LinearLayout layoutContent;

        public TrafficInfoHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_info_traffic);
            layoutContent=findView(R.id.layoutContent);
            tvTitle = findView(R.id.tvTitle);
            tvFrom = findView(R.id.tvFrom);
            tvTo = findView(R.id.tvTo);

        }

        @Override
        public void bindData(TrafficInfoBean trafficInfoBean) {
            tvTitle.setText(trafficInfoBean.getTitle());
            tvFrom.setText(trafficInfoBean.getFrom());
            tvTo.setText(trafficInfoBean.getTo());
            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

}
