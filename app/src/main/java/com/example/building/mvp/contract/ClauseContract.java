package com.example.building.mvp.contract;

import com.example.building.bean.ClauseBean;
import com.example.building.mvp.BaseView;

import java.util.List;

public interface ClauseContract {
    interface ClausePresenter {
        void getKeysOfClause();

        void getClause(String key);

    }

    interface ClauseView extends BaseView {
        void getKeysOfClauseSuccess(List<ClauseBean> list);

        void showClause(ClauseBean clauseBean);
    }
}
