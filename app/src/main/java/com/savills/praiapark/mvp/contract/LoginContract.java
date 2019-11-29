package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.mvp.BaseView;

public interface LoginContract {
    interface LoginPresenter {

        void register(String username, String password, String householderCode);

        void login(String username, String password);

        void forget(String username, String password);

    }

    interface LoginView extends BaseView {
        void registerSuccess();

        void loginSuccess();

        void forgetSuccess();
    }
}
