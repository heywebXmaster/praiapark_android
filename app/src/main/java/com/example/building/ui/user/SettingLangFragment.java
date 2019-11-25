package com.example.building.ui.user;

import android.view.View;

import com.example.building.R;
import com.example.building.aop.annotation.SingleClick;
import com.example.building.base.BaseFragment;
import com.example.building.base.ClickPresenter;
import com.example.building.config.LocalSaveData;
import com.example.building.databinding.FragmentSettingLangBinding;
import com.example.building.ui.main.MainActivity;
import com.example.building.ui.main.MainFragment;

public class SettingLangFragment extends BaseFragment<FragmentSettingLangBinding> implements ClickPresenter {

    private String lang;

    public static SettingLangFragment newInstant() {
        return new SettingLangFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_setting_lang;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_setting_lang));
    }

    @Override
    protected void initView() {
        dataBinding.layoutHeader.tvright.setVisibility(View.VISIBLE);
        lang = LocalSaveData.getInstance().getLang();
        switch (lang) {
            case LocalSaveData.TAG.LANG_CN:
                dataBinding.ivLangCn.setVisibility(View.VISIBLE);
                break;
            case LocalSaveData.TAG.LANG_TW:
                dataBinding.ivLangTw.setVisibility(View.VISIBLE);
                break;
            case LocalSaveData.TAG.LANG_EN:
                dataBinding.ivLangEn.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.setPresenter(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.tvright:
                LocalSaveData.getInstance().saveLang(lang);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setLang(lang);
                startWithPopTo(MainFragment.newInstant(), MainFragment.class, true);
                break;
            case R.id.layoutLangCn:
                clearCheck();
                dataBinding.ivLangCn.setVisibility(View.VISIBLE);
                lang = LocalSaveData.TAG.LANG_CN;
                break;
            case R.id.layoutLangTw:
                clearCheck();
                dataBinding.ivLangTw.setVisibility(View.VISIBLE);
                lang = LocalSaveData.TAG.LANG_TW;
                break;
            case R.id.layoutLangEn:
                clearCheck();
                dataBinding.ivLangEn.setVisibility(View.VISIBLE);
                lang = LocalSaveData.TAG.LANG_EN;
                break;
        }
    }

    private void clearCheck() {
        dataBinding.ivLangCn.setVisibility(View.GONE);
        dataBinding.ivLangTw.setVisibility(View.GONE);
        dataBinding.ivLangEn.setVisibility(View.GONE);
    }
}
