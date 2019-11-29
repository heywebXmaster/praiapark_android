package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.ClauseBean;
import com.savills.praiapark.mvp.BaseView;

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
