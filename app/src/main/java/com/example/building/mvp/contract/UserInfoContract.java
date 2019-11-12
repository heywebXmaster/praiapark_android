package com.example.building.mvp.contract;

import com.example.building.bean.ProfileBean;
import com.example.building.mvp.BaseView;

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
