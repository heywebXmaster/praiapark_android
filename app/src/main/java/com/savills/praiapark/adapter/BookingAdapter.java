package com.savills.praiapark.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.savills.praiapark.R;
import com.savills.praiapark.adapter.base.BaseRecyclerAdapter;
import com.savills.praiapark.adapter.base.CommonHolder;
import com.savills.praiapark.bean.BookingBean;

public class BookingAdapter extends BaseRecyclerAdapter<BookingBean> {

    @Override
    public CommonHolder<BookingBean> setViewHolder(ViewGroup parent) {
        return new OrderHolder(parent.getContext(), parent);
    }

    class OrderHolder extends CommonHolder<BookingBean> {

        AppCompatTextView tvTime;
        AppCompatTextView tvState;
        AppCompatTextView tvDate;
        View divider;


        public OrderHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_order);
            tvTime = findView(R.id.tvTime);
            tvState = findView(R.id.tvState);
            tvDate = findView(R.id.tvDate);
            divider = findView(R.id.divider);
        }

        @Override
        public void bindData(BookingBean bookingBean) {
            divider.setVisibility(getAdapterPosition() == getAllData().size() - 1 ? View.GONE : View.VISIBLE);
            tvTime.setText(bookingBean.getFromTime()+":00 ~ "+bookingBean.getToTime()+":00");
            tvState.setText(bookingBean.getIsPay()==0?R.string.text_order_record_status_prepay:R.string.text_order_record_status_paid);
            tvDate.setText(bookingBean.getCreatedAt());
        }
    }
}
