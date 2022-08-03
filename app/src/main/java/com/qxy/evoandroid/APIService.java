package com.qxy.evoandroid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
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
}
