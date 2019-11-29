package com.savills.praiapark.ui.main.service;

import com.savills.praiapark.R;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.databinding.FragmentListBinding;

public class ClubRuleFragment extends BaseFragment<FragmentListBinding> {

    @Override
    protected int setViewId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
    }

    @Override
    protected void setListener() {

    }
}
