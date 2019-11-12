package com.example.building.ui.user;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.example.building.R;
import com.example.building.aop.annotation.SingleClick;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.bean.PdfBean;
import com.example.building.databinding.FragmentContactUsBinding;
import com.example.building.mvp.contract.SettingContract;
import com.example.building.mvp.presenter.SettingPresenter;
import com.example.building.ui.main.MainFragment;
import com.example.building.ui.main.WebFragment;

import java.util.List;

public class ContactUsFragment extends BaseFragment<FragmentContactUsBinding> implements ClickPresenter, TextWatcher, SettingContract.SettingView {

    private SettingPresenter settingPresenter;

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
//        final SpannableStringBuilder style = new SpannableStringBuilder();
//        style.append(getString(R.string.text_contact_us_desc_detail));
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                ((MainFragment) getParentFragment()).startBrotherFragment(WebFragment.newInstant("http://baidu.com","百度"));
//            }
//        };
//        style.setSpan(clickableSpan, 37, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        dataBinding.tvDescDetail.setText(style);
//        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorDrawable));
//        style.setSpan(foregroundColorSpan, 37, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        dataBinding.tvDescDetail.setMovementMethod(LinkMovementMethod.getInstance());
//        dataBinding.tvDescDetail.setText(style);
        settingPresenter = new SettingPresenter(this);
    }

    @Override
    protected void setListener() {
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
