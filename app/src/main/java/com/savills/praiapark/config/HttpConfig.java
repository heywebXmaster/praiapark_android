package com.savills.praiapark.config;

public class HttpConfig {

    public static final String GOOGLE_PDF_LINK = "https://docs.google.com/viewer?embedded=true&url=";

    public static final String APP_ID = "123422343234";

    public static final String APP_SECRET = "QWqw123!@#$";

    public static final String BASE_URL = "https://praiapark-savills.com";
//    public static final String BASE_URL = "http://47.74.184.20";

    //登录
    public static final String LOGIN = "/api/login";

    //注册
    public static final String REGISTER = "/api/register";

    //忘记密码
    public static final String FORGET = "/api/forget";

    //获取资料
    public static final String USER_PROFILE = "/api/getProfile";

    //修改资料
    public static final String UPDATE_PROFILE = "/api/updateProfile";

    //通告列表
    public static final String ANNOUNCEMENT = "/api/getAnnouncements";

    //修改密码
    public static final String RESET_PASSWORD = "/api/resetPassword";

    //联系我们
    public static final String CONTACT_US = "/api/contactUs";

    //获取条款
    public static final String GET_CLAUSE = "/api/getClause";

    //获取条款KEY
    public static final String GET_CLAUSE_KEYS = "/api/getKeysOfClause";

    //获取pdf
    public static final String GET_PDFS = "/api/getPdfs";

    //获取通知
    public static final String GET_MESSAGES = "/api/getMessages";

    //单位资讯
    public static final String GET_INFO = "/api/getInfos";

    //交通资讯
    public static final String GET_BUS_INFO = "/api/getBusInfos";

    //周边资讯
    public static final String GET_TEL = "/api/getTels";

    //上載推送token
    public static final String INIT_TOKEN = "/api/uploadPushToken";

}
