package com.savills.praiapark.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.savills.praiapark.R;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.databinding.FragmentPdfViewBinding;
import com.savills.praiapark.util.LogUtil;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;


public class PdfFragment extends BaseFragment<FragmentPdfViewBinding> implements ClickPresenter {

    private String title;
    private String url;
    private int pageCount = 0;

    public static PdfFragment newInstant(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(COMMON_TITLE, title);
        bundle.putString(COMMON_URL, url);
        PdfFragment pdfFragment = new PdfFragment();
        pdfFragment.setArguments(bundle);
        return pdfFragment;
    }

    public static final String COMMON_URL = "common_url";
    public static final String COMMON_TITLE = "common_title";

    @Override
    protected int setViewId() {
        return R.layout.fragment_pdf_view;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(COMMON_TITLE);
            url = bundle.getString(COMMON_URL);
            LogUtil.e(url);
            if (!StringUtils.isEmpty(title)) {
                dataBinding.layoutHeader.setTitle(title);
            }
        }
        dataBinding.layoutHeader.tvright.setText(R.string.text_pdf_share);
        dataBinding.layoutHeader.tvright.setVisibility(View.VISIBLE);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (url.endsWith("pdf")) {
            dataBinding.layoutContent.setVisibility(View.VISIBLE);
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            downloadSource();
                        } else {
                            pop();
                        }
                    });
        } else {
            dataBinding.scaleImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(url).into(dataBinding.scaleImageView);
        }

    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
    }

    private void downloadSource() {
        String dirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/";
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.tvright:
                share();
                break;
        }
    }

    private void share() {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, title +"\n"+ url);
            intent.setType("text/plain");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
