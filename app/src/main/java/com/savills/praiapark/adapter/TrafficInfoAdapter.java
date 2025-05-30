package com.savills.praiapark.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.TrafficInfoBean;

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
