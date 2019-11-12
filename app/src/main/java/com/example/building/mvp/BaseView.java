package com.example.building.mvp;

public interface BaseView {

    /**
     * 统一的错误信息处理方法
     *
     * @param msg
     */
    void showErrorMsg(String msg);

    /**
     * 统一的错误信息处理方法,Toast
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 顯示加載窗體
     * @param loadingMsg
     */
    void showLoading(int loadingMsg);

    /**
     * 顯示加載窗體
     */
    void showLoading();

    /**
     * 隱藏加載窗體
     */
    void hideLoading();

    void showLoadError();
}
