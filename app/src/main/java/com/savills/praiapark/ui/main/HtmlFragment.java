package com.savills.praiapark.ui.main;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.ClauseBean;
import com.savills.praiapark.databinding.FragmengHtmlBinding;
import com.savills.praiapark.mvp.contract.ClauseContract;
import com.savills.praiapark.mvp.presenter.ClausePresenter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class HtmlFragment extends BaseFragment<FragmengHtmlBinding> implements ClauseContract.ClauseView, ClickPresenter {

    private String key;
    private ClausePresenter clausePresenter;
    private String title;
    private String content;
    private boolean fromMain = false;

    public static HtmlFragment newInstant(String key, boolean fromLogin) {
        Bundle bundle = new Bundle();
        bundle.putString(COMMON_KEY, key);
        bundle.putBoolean(COMMON_FROM_MAIN, fromLogin);
        HtmlFragment htmlFragment = new HtmlFragment();
        htmlFragment.setArguments(bundle);
        return htmlFragment;
    }

    public static HtmlFragment newInstant(String title, String content) {
        Bundle bundle = new Bundle();
        bundle.putString(COMMON_TITLE, title);
        bundle.putString(COMMON_CONTENT, content);
        HtmlFragment htmlFragment = new HtmlFragment();
        htmlFragment.setArguments(bundle);
        return htmlFragment;
    }

    public static final String COMMON_KEY = "common_key";
    public static final String COMMON_TITLE = "common_title";
    public static final String COMMON_CONTENT = "common_content";
    public static final String COMMON_FROM_MAIN = "common_from_main";

    @Override
    protected int setViewId() {
        return R.layout.fragmeng_html;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(getActivity())
                .statusBarColor(R.color.black)
                .fitsSystemWindows(true)
                .init();
        Bundle bundle = getArguments();
        if (bundle != null) {
            key = bundle.getString(COMMON_KEY);
            fromMain = bundle.getBoolean(COMMON_FROM_MAIN);
            title = bundle.getString(COMMON_TITLE);
            content = bundle.getString(COMMON_CONTENT);
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (StringUtils.isEmpty(key)) {
            loadView(title, content);
        } else {
            clausePresenter = new ClausePresenter(this);
//            clausePresenter.getKeysOfClause();
            clausePresenter.getClause(key);
        }
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
    }

    @Override
    public void getKeysOfClauseSuccess(List<ClauseBean> list) {

    }

    @Override
    public void showClause(ClauseBean clauseBean) {
        loadView(clauseBean.getTitle(), clauseBean.getContent());
    }

    private void loadView(String title, String content) {

        String html="<html><head><style>span,p,strong,a{color:#000000!important;} span,p,strong,a{background-color:transparent!important;}</style></head><body style=\"margin:0 auto; color:black!important; text-align: justify; font-family: arial; font-size:15px;padding:12px;\";>"+content+"</body></html>";

        dataBinding.layoutHeader.setTitle(title);
        dataBinding.webview.getSettings().setDefaultFontSize(12);
        dataBinding.webview.loadDataWithBaseURL(
                null,
                html,
                "text/html",
                "UTF-8",
                null);
    }

    @Override
    public void onClick(View v) {
        pop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fromMain)
            ImmersionBar.with(this).init();
    }
}
