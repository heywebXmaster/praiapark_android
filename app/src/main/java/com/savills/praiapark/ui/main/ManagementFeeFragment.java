package com.savills.praiapark.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.savills.praiapark.R;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.databinding.FragmentPdfViewBinding;
import com.savills.praiapark.mvp.contract.SettingContract;
import com.savills.praiapark.mvp.presenter.SettingPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

public class ManagementFeeFragment extends BaseFragment<FragmentPdfViewBinding> implements ClickPresenter, SettingContract.SettingView {

    private SettingPresenter settingPresenter;
    private String url;
    private int pageCount = 0;
    public static ManagementFeeFragment newInstant() {
        return new ManagementFeeFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_pdf_view;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_nav_fee));
        dataBinding.layoutHeader.ivRight.setVisibility(View.VISIBLE);
        dataBinding.layoutHeader.ivRight.setImageResource(R.mipmap.icon_message);
        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
    }

    @Override
    protected void initView() {
        settingPresenter = new SettingPresenter(this);
        setSwipeBackEnable(false);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        settingPresenter.getFee();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                ((MainFragment) getParentFragment()).onOpenDrawer();
                break;
            case R.id.ivRight:
                ((MainFragment) getParentFragment()).startBrotherFragment(NoticeFragment.newInstant());
                break;
        }
    }

    @Override
    public void contactUsSuccess() {

    }

    @Override
    public void showPdfs(List<PdfBean> list) {

    }

    @Override
    public void showFee(PdfBean pdfBean) {
        if(!StringUtils.isEmpty(pdfBean.getManagementFee())){
            url=pdfBean.getManagementFee();
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            downloadSource();
                        }
                    });
        }
    }

    private void downloadSource() {
        String dirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath() + "/";
        File file = new File(dirPath);
        if (file.exists()) {
            FileUtils.deleteAllInDir(file);
        }

        String filePath = dirPath + System.currentTimeMillis() + ".pdf";
        FileDownloader.getImpl().create(url)
                .setPath(filePath, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setForceReDownload(true)
                .setTag(1)
                .setListener(downloadListener)
                .start();
    }

    FileDownloadListener downloadListener = new FileDownloadListener() {

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            showLoading();
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void completed(BaseDownloadTask task) {
            hideLoading();
            File file = new File(task.getTargetFilePath());
            dataBinding.layoutContent.setVisibility(View.VISIBLE);
            dataBinding.pdfView.fromFile(file)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            pageCount = nbPages;
                            dataBinding.setIndex(1 + "/" + nbPages);
                            dataBinding.tvIndex.setVisibility(View.VISIBLE);
                        }
                    })
                    .onPageScroll(new OnPageScrollListener() {
                        @Override
                        public void onPageScrolled(int page, float positionOffset) {
                            dataBinding.setIndex((page + 1) + "/" + pageCount);
                        }
                    })
                    .spacing(10) // in dp
                    .load();
        }


        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            hideLoading();
            showLoadError();
        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };
}
