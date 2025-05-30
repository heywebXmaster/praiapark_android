package com.savills.praiapark.ui.main.club;

import android.Manifest;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.ItemClickListener;
import com.savills.praiapark.adapter.UnitInfoAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.databinding.FragmentClubPriceBinding;
import com.savills.praiapark.databinding.FragmentListBinding;
import com.savills.praiapark.databinding.FragmentPdfViewBinding;
import com.savills.praiapark.mvp.contract.ClubInfoContract;
import com.savills.praiapark.mvp.presenter.ClubInfoPresenter;
import com.savills.praiapark.ui.main.ClubServiceFragment;
import com.savills.praiapark.ui.main.PdfFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClubPriceFragment extends BaseFragment<FragmentClubPriceBinding> implements ClubInfoContract.ClubInfoView {

    public static ClubPriceFragment newInstant() {
        return new ClubPriceFragment();
    }

    private ClubInfoPresenter infoPresenter;
    private String url;
    private int pageCount = 0;

    @Override
    protected int setViewId() {
        return R.layout.fragment_club_price;
    }

    @Override
    protected void setTitle() {

    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        infoPresenter = new ClubInfoPresenter(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        infoPresenter.getClubPrice();
    }

    @Override
    public void showDevicesList(List<DevicesBean> list) {

    }

    @Override
    public void showClubRuleList(List<UnitInfoBean> list) {

    }

    @Override
    public void showClubPrice(PdfBean pdfBean) {
        if(!StringUtils.isEmpty(pdfBean.getPdf())){
            url=pdfBean.getPdf();
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

    @Override
    public void showParseIconList(List<DevicesBean> list) {
        
    }

    private void downloadSource() {
        String dirPath = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/";
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
}
