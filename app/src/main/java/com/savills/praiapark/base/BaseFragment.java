package com.savills.praiapark.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.mvp.BaseView;
import com.savills.praiapark.net.RxNetManager;
import com.savills.praiapark.ui.main.MainActivity;
import com.savills.praiapark.util.NetWorkUtil;
import com.savills.praiapark.util.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public abstract class BaseFragment<V extends ViewDataBinding> extends SwipeBackFragment implements BaseView {

    public V dataBinding;
    public View view;
    public Context mContext;
    private MaterialDialog materialDialog;

    @SuppressLint("CheckResult")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
        mContext = getActivity();
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.initLang();
        initView();
        setTitle();
        setListener();
    }

    private void initLang() {
        String lang = LocalSaveData.getInstance().getLang();
        if (StringUtils.isEmpty(lang)) {
            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = getResources().getConfiguration().getLocales().get(0);
            } else {
                locale = getResources().getConfiguration().locale;
            }
            String systemLang = locale.getLanguage() + "-" + locale.getCountry();
            if (systemLang.contains("TW") || systemLang.contains("HK")) {
                setLang(LocalSaveData.TAG.LANG_TW);
            } else if (systemLang.contains(LocalSaveData.TAG.LANG_EN)) {
                setLang(LocalSaveData.TAG.LANG_EN);
            } else {
                setLang(LocalSaveData.TAG.LANG_CN);
            }
        } else {
            setLang(lang);
        }
    }

    public void setLang(String lang) {
        LocalSaveData.getInstance().saveLang(lang);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        if (lang.equals(LocalSaveData.TAG.LANG_CN)) {
            config.locale = Locale.CHINESE;
        } else if (lang.equals(LocalSaveData.TAG.LANG_TW)) {
            config.locale = Locale.TAIWAN;
        } else {
            config.locale = Locale.UK;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, setViewId(), container, false);
        return attachToSwipeBack(dataBinding.getRoot());
    }

    protected abstract int setViewId();

    protected abstract void setTitle();

    protected abstract void initView();

    protected abstract void setListener();

    public void registEventBus() {
        EventBus.getDefault().register(this);
    }

    public void postEventMsg(Object msg) {
        EventBus.getDefault().post(msg);
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showToast(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showLoading() {
        showLoading(R.string.msg_dialog_loading);
    }

    @Override
    public void showLoading(int loadingMsg) {
        materialDialog = new MaterialDialog.Builder(mContext)
                .content(getString(loadingMsg))
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .widgetColor(getResources().getColor(R.color.colorDrawable))
                .show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing())
            materialDialog.dismiss();
    }

    public void startNewFragment(SwipeBackFragment targetFragment) {
        start(targetFragment, SupportFragment.SINGLETOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxNetManager.getInstance().cancelAllDisposable();//取消訂閱防止內存泄漏
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void startFragmentForResult(BaseFragment fragment, Bundle bundle, int requestCode) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        startForResult(fragment, requestCode);
    }

    public void setResult(int result, Bundle bundle) {
        setFragmentResult(result, bundle);
    }

    private int scrollToPosition = 0;

    public void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);
                    }
                });
    }



    public void processRefreshLayoutStatus(int page, int listSize, RefreshLayout refreshLayout) {
        if (page == 1) {
            if (listSize < 10) {
                refreshLayout.finishRefreshWithNoMoreData();
            } else {
                if(NetWorkUtil.isNetworkAvailable(mContext)){
                    refreshLayout.finishRefresh();
                }else{
                    refreshLayout.finishRefreshWithNoMoreData();
                }
            }
        } else {
            if (listSize < 10) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                refreshLayout.finishLoadMore();
            }
        }
    }

    @Override
    public void showLoadError() {
        ToastUtils.setBgColor(Color.RED);
        ToastUtils.setMsgColor(Color.WHITE);
        ToastUtils.showShort(getString(R.string.ConnectException));
    }

    public void processRefreshLayoutError(int page, RefreshLayout refreshLayout){
        if(page==1){
            refreshLayout.finishRefresh();
        }else{
            refreshLayout.finishLoadMore();
        }
    }
}
