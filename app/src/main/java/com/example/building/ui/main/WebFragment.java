package com.example.building.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.building.R;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.databinding.FragmentWebBinding;
import com.example.building.util.LogUtil;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

public class WebFragment extends BaseFragment<FragmentWebBinding> implements ClickPresenter {

    private String title;
    private String url;

    public static WebFragment newInstant(String title,String url) {
        Bundle bundle = new Bundle();
        bundle.putString(COMMON_TITLE, title);
        bundle.putString(COMMON_URL, url);
        WebFragment webFragment = new WebFragment();
        webFragment.setArguments(bundle);
        return webFragment;
    }


    private AgentWeb agentWeb;
    private int WEB_LAYOUT_WIDTH = -1;
    public static final String COMMON_URL = "common_url";
    public static final String COMMON_TITLE = "common_title";


    @Override
    protected int setViewId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void setTitle() {
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString(COMMON_URL);
            title = bundle.getString(COMMON_TITLE);
            if (!StringUtils.isEmpty(title)) {
                dataBinding.layoutHeader.setTitle(title);
            }
        }
        LogUtil.i(url);
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(dataBinding.frameLayout, new RelativeLayout.LayoutParams(WEB_LAYOUT_WIDTH, WEB_LAYOUT_WIDTH))
                .useDefaultIndicator(ColorUtils.getColor(R.color.colorDrawable), 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK) //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1) //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
    }

    @Override
    public void onClick(View v) {
        pop();
    }


    @Override
    public void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
    @Override
    public void onDestroyView() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }
}
