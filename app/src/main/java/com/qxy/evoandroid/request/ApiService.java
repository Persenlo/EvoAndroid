package com.qxy.evoandroid.request;

import com.qxy.evoandroid.model.UserInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
     * @param headToken token
     * @param token     token
     * @param openId    openId标志
     * @return 用户信息
     */
    @FormUrlEncoded
    @POST("/oauth/userinfo/")
    Call<UserInfo> getUserInfo(@Header("access-token") String headToken, @Field("access_token") String token, @Field("open_id") String openId);


}
