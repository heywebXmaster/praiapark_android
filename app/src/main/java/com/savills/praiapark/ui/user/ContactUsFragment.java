package com.savills.praiapark.ui.user;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.databinding.FragmentContactUsBinding;
import com.savills.praiapark.mvp.contract.SettingContract;
import com.savills.praiapark.mvp.presenter.SettingPresenter;
import com.savills.praiapark.ui.main.MainFragment;
import com.savills.praiapark.ui.main.WebFragment;
import com.savills.praiapark.widget.ImageDialog;

import java.util.List;

public class ContactUsFragment extends BaseFragment<FragmentContactUsBinding> implements ClickPresenter, TextWatcher, SettingContract.SettingView {

    private SettingPresenter settingPresenter;
    private ImageDialog dialog;

    public static ContactUsFragment newInstant() {
        return new ContactUsFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_contact_us;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_nav_contact));
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        dataBinding.layoutHeader.ivBack.setImageResource(R.mipmap.icon_menu);
        dataBinding.layoutHeader.tvright.setEnabled(false);
        dataBinding.layoutHeader.tvright.setVisibility(View.VISIBLE);
        dataBinding.descWeb.getSettings().setDefaultFontSize(10);
        String lang = LocalSaveData.getInstance().getLang();
        switch (lang) {
            case LocalSaveData.TAG.LANG_CN:
                dataBinding.descWeb.loadUrl("file:///android_asset/index_cn.html");
                break;
            case LocalSaveData.TAG.LANG_TW:
                dataBinding.descWeb.loadUrl("file:///android_asset/index.html");
                break;
            case LocalSaveData.TAG.LANG_EN:
                dataBinding.descWeb.loadUrl("file:///android_asset/index_en.html");
                break;
        }

        settingPresenter = new SettingPresenter(this);
    }

    @Override
    protected void setListener() {
        dataBinding.setPresenter(this);
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.etTitle.addTextChangedListener(this);
        dataBinding.etContent.addTextChangedListener(this);
        dataBinding.etName.addTextChangedListener(this);
        dataBinding.etMobile.addTextChangedListener(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                hideSoftInput();
                ((MainFragment) getParentFragment()).onOpenDrawer();
                break;
            case R.id.tvright:
                contactUs();
                break;
            case R.id.ivMap:
                if (dialog == null) {
                    dialog = new ImageDialog();
                }
                dialog.show(getFragmentManager(), "image");
                break;
        }
    }

    private void contactUs() {
        String title = dataBinding.etTitle.getText().toString().trim();
        String content = dataBinding.etContent.getText().toString().trim();
        String nickName = dataBinding.etName.getText().toString().trim();
        String mobile = dataBinding.etMobile.getText().toString().trim();
        settingPresenter.contactUs(title, content, nickName, mobile);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!StringUtils.isEmpty(dataBinding.etTitle.getText().toString().trim()) &&
                !StringUtils.isEmpty(dataBinding.etContent.getText().toString().trim()) &&
                !StringUtils.isEmpty(dataBinding.etName.getText().toString().trim()) &&
                !StringUtils.isEmpty(dataBinding.etMobile.getText().toString().trim())) {
            dataBinding.layoutHeader.tvright.setEnabled(true);
        } else {
            dataBinding.layoutHeader.tvright.setEnabled(false);
        }
    }

    @Override
    public void contactUsSuccess() {
        dataBinding.etTitle.setText("");
        dataBinding.etContent.setText("");
        dataBinding.etName.setText("");
        dataBinding.etMobile.setText("");
        dataBinding.layoutContent.setFocusable(true);
        dataBinding.layoutContent.setFocusableInTouchMode(true);
        dataBinding.layoutContent.requestFocus();
    }

    @Override
    public void showPdfs(List<PdfBean> list) {

    }

}
