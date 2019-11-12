package com.example.building.ui.main.service;

import com.example.building.R;
import com.example.building.base.BaseFragment;
import com.example.building.databinding.FragmentListBinding;

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
