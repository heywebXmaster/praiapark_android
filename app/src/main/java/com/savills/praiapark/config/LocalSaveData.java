package com.savills.praiapark.config;


import com.blankj.utilcode.util.SPUtils;

public class LocalSaveData {

    private static LocalSaveData instance;

    public synchronized static LocalSaveData getInstance() {
        if (null == instance) {
            synchronized (LocalSaveData.class) {
                if (null == instance) {
                    instance = new LocalSaveData();
                }
            }
        }
        return instance;
    }

    public static class TAG {
        public static final String IS_LOGIN = "is_login";
        public static final String USER_NAME = "user_name";
        public static final String HOUSE_HOLDER_CODE = "house_holder_code";

        public static final String LANG = "lang";
        public static final String LANG_CN = "zh-CN";
        public static final String LANG_TW = "zh-TW";
        public static final String LANG_EN = "en";
    }

    public void setLogin(boolean isLogin) {
        if (!isLogin) {
            setUserName("");
        }
        SPUtils.getInstance().put(TAG.IS_LOGIN, isLogin);
    }

    public boolean isLogin() {
        return SPUtils.getInstance().getBoolean(TAG.IS_LOGIN);
    }

    public void saveLang(String lang) {
        SPUtils.getInstance().put(TAG.LANG, lang);
    }

    public String getLang() {
        return SPUtils.getInstance().getString(TAG.LANG);
    }

    public void setUserName(String userName) {
        SPUtils.getInstance().put(TAG.USER_NAME, userName);
    }

    public String getUserName() {
        return SPUtils.getInstance().getString(TAG.USER_NAME);
    }

    public void setHouseHolderCode(String houseHolderCode) {
        SPUtils.getInstance().put(TAG.HOUSE_HOLDER_CODE, houseHolderCode);
    }

    public String getHouseHolderCode() {
        return SPUtils.getInstance().getString(TAG.HOUSE_HOLDER_CODE);
    }

}
