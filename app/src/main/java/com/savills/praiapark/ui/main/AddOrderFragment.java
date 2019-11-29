package com.savills.praiapark.ui.main;

import android.view.View;

import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.databinding.FragmentAddOrderBinding;

public class AddOrderFragment extends BaseFragment<FragmentAddOrderBinding> implements ClickPresenter {

    public static AddOrderFragment newInstant() {
        return new AddOrderFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_add_order;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_add_order));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.setPresenter(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.tvSelectDevices:

                break;
            case R.id.tvStartTime:

                break;
            case R.id.tvEndTime:

                break;
            case R.id.tvAddOrder:

                break;


        }
    }
}
