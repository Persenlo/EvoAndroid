package com.qxy.evoandroid.request;

import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.model.VideoData;
import com.qxy.evoandroid.model.VideoRank;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * 获取Access_Token
     * client_secret 应用唯一标识对应的密钥
     * code	string	 授权码
     * grant_type	 写死"authorization_code"即可
     * client_key	 应用唯一标识
     */
    @FormUrlEncoded
    @POST("/oauth/access_token/")
    Call<ResponseBody> getToken(@Field("client_secret") String clientSecret, @Field("code") String code,@Field("grant_type") String type,@Field("client_key") String clientKey);


    /**
     *
     * @param clientKey：    应用唯一标识
     * @param clientSecret: 应用唯一标识对应的密钥
     * @param grantType:    "client_credential"
     * @return
     */
    @FormUrlEncoded
    @POST("/oauth/client_token/")
    Call<ResponseBody> getClientToken(@Field("client_key") String clientKey,@Field("client_secret") String clientSecret,@Field("grant_type") String grantType);

    /**
     * @param headToken token
     * @param token     token
     * @param openId    openId标志
     * @return 用户信息
     */
    @FormUrlEncoded
    @POST("/oauth/userinfo/")
    Call<UserInfo> getUserInfo(@Header("access-token") String headToken, @Field("access_token") String token, @Field("open_id") String openId);


    /**
     *
     * @param clientToken   token
     * @param type          类型
     * @param version       版本
     * @return
     */
    @GET("/discovery/ent/rank/item/")
    Call<VideoRank> getVideoRank(@Header("access-token") String clientToken, @Query("type") int type, @Query("version") int version);


    /**
     *
     * @param clientToken   token
     * @param openId        openid
     * @param body          lists
     * @return
     */
    @POST("/video/data/")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Call<VideoData> getVideoData(@Header("access-token") String accessToken, @Query("open_id") String openId, @Body RequestBody body);


    @GET("/web/api/v2/aweme/iteminfo/")
    Call<ResponseBody> getVideoId(@Query("item_ids") String itemId);

}
