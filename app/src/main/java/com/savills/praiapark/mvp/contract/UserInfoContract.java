package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.ProfileBean;
import com.savills.praiapark.mvp.BaseView;

public interface UserInfoContract {

    interface UserInfoPresenter {
        void getProfile();

        void updateProfile(String nickname, String phoneNumber, String avatar);

        void resetPassword(String oldPassword, String newPassword);
    }

    interface UserInfoView extends BaseView {
        void showProfile(ProfileBean profileBean);

        void updateProfileSuccess();

        void resetPasswordSuccess();
    }

}
