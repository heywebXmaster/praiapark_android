package com.savills.praiapark.net;


import com.savills.praiapark.bean.AnnouncementBean;
import com.savills.praiapark.bean.AroundInfoBean;
import com.savills.praiapark.bean.BaseEntity;
import com.savills.praiapark.bean.BookingBean;
import com.savills.praiapark.bean.ClauseBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.bean.NoticeBean;
import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.bean.ProfileBean;
import com.savills.praiapark.bean.TrafficInfoBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.config.HttpConfig;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface APIFunction {

    @FormUrlEncoded
    @POST(HttpConfig.REGISTER)
    Observable<BaseEntity<ProfileBean>> register(@Field("username") String username,
                                                 @Field("password") String password,
                                                 @Field("householderCode") String householderCode);

    @FormUrlEncoded
    @POST(HttpConfig.LOGIN)
    Observable<BaseEntity<ProfileBean>> login(@Field("username") String username,
                                              @Field("password") String password);

    @FormUrlEncoded
    @POST(HttpConfig.FORGET)
    Observable<BaseEntity<String>> forget(@Field("username") String username,
                                          @Field("password") String password);

    @FormUrlEncoded
    @POST(HttpConfig.USER_PROFILE)
    Observable<BaseEntity<ProfileBean>> getProfile(@Field("username") String username);

    @FormUrlEncoded
    @POST(HttpConfig.UPDATE_PROFILE)
    Observable<BaseEntity<String>> updateProfile(@Field("username") String username,
                                                 @Field("nickname") String nickname,
                                                 @Field("phoneNumber") String phoneNumber,
                                                 @Field("avatar") String avatar);

    @Multipart
    @POST(HttpConfig.UPDATE_PROFILE)
    Observable<BaseEntity<String>> updateProfile(@PartMap Map<String, RequestBody> requestBodyMap,
                                                 @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST(HttpConfig.RESET_PASSWORD)
    Observable<BaseEntity<String>> resetPassword(@Field("username") String username,
                                                 @Field("oldPassword") String oldPassword,
                                                 @Field("newPassword") String newPassword);


    @FormUrlEncoded
    @POST(HttpConfig.CONTACT_US)
    Observable<BaseEntity<String>> contactUs(@Field("username") String username,
                                             @Field("householderCode") String householderCode,
                                             @Field("title") String title,
                                             @Field("content") String content,
                                             @Field("nickname") String nickname,
                                             @Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST(HttpConfig.GET_CLAUSE)
    Observable<BaseEntity<ClauseBean>> getClause(@Field("username") String username,
                                                 @Field("key") String key);

    @FormUrlEncoded
    @POST(HttpConfig.GET_CLAUSE_KEYS)
    Observable<BaseEntity<List<ClauseBean>>> getClauseKeys(@Field("username") String username);

    @FormUrlEncoded
    @POST(HttpConfig.GET_PDFS)
    Call<BaseEntity<List<PdfBean>>> getPdfs(@Field("username") String username);

    @FormUrlEncoded
    @POST(HttpConfig.GET_MESSAGES)
    Call<BaseEntity<NoticeBean>> getMessages(@Field("username") String username,
                                             @Field("page") int page,
                                             @Field("limit") int limit);

    @FormUrlEncoded
    @POST(HttpConfig.ANNOUNCEMENT)
    Call<BaseEntity<AnnouncementBean>> getAnnouncements(@Field("username") String username,
                                                        @Field("page") int page,
                                                        @Field("limit") int limit);

    @FormUrlEncoded
    @POST(HttpConfig.GET_INFO)
    Call<BaseEntity<List<UnitInfoBean>>> getInfo(@Field("username") String username);

    @FormUrlEncoded
    @POST(HttpConfig.GET_BUS_INFO)
    Call<BaseEntity<List<TrafficInfoBean>>> getBusInfo(@Field("username") String username);

    @FormUrlEncoded
    @POST(HttpConfig.GET_TEL)
    Call<BaseEntity<List<AroundInfoBean>>> getTel(@Field("username") String username);

    @FormUrlEncoded
    @POST(HttpConfig.INIT_TOKEN)
    Observable<BaseEntity<String>> initToken(@Field("username") String username,
                                             @Field("pushToken") String regId,
                                             @Field("deviceType") int deviceType);

    @FormUrlEncoded
    @POST(HttpConfig.DEVICES_LIST)
    Call<BaseEntity<List<DevicesBean>>> getDevicesList(@Field("username") String username);

    @FormUrlEncoded
    @POST(HttpConfig.UPLOAD_BOOKING)
    Observable<BaseEntity<String>> uploadBooking(@Field("username") String username,
                                                 @Field("nickname") String nickname,
                                                 @Field("phoneNumber") String phoneNumber,
                                                 @Field("facilityId") int facilityId,
                                                 @Field("date") String date,
                                                 @Field("fromTime") int fromTime,
                                                 @Field("toTime") int toTime,
                                                 @Field("amount") String amount);

    @FormUrlEncoded
    @POST(HttpConfig.GET_BOOKING)
    Call<BaseEntity<List<BookingBean>>> getBookingByDate(@Field("username") String username,
                                                         @Field("facilityId") int facilityId,
                                                         @Field("date") String date);

    @FormUrlEncoded
    @POST(HttpConfig.CHECK_BOOKING_TIME)
    Observable<BaseEntity<String>> checkBookingTime(@Field("username") String username,
                                                 @Field("facilityId") int facilityId,
                                                 @Field("date") String date,
                                                 @Field("fromTime") int fromTime,
                                                 @Field("toTime") int toTime);
}
