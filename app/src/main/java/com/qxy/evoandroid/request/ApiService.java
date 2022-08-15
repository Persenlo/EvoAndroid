package com.qxy.evoandroid.request;

import com.qxy.evoandroid.model.FansInfo;
import com.qxy.evoandroid.model.FollowInfo;
import com.qxy.evoandroid.model.RankVersion;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.model.VideoRank;
import com.qxy.evoandroid.model.VideosInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
     * @return 榜单数据
     */
    @GET("/discovery/ent/rank/item/")
    Call<VideoRank> getVideoRank(@Header("access-token") String clientToken, @Query("type") int type, @Query("version") int version);

    /**
     *
     * @param clientToken  token
     * @param type         类型
     * @param count        数量
     * @return 榜单版本数据
     */
    @GET("/discovery/ent/rank/version/")
    Call<RankVersion> getRankVersion(@Header("access-token")String clientToken, @Query("type")int type, @Query("count")int count);

    /**
     *
     * @param accessToken   token
     * @param openId        openid
     * @param cursor        分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count         每页数量 最大不能超过20
     * @return
     */
    @GET("/fans/list/")
    Call<FansInfo> getFansInfo(@Header("access-token") String accessToken, @Query("open_id") String openId, @Query("cursor") String cursor, @Query("count") String count);

    /**
     *
     * @param accessToken   token
     * @param count         每页数量
     * @param openId        通过/oauth/access_token/获取，用户唯一标志
     * @param cursor        分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @return
     */
    @GET("/following/list/")
    Call<FollowInfo> getFollowInfo(@Header("access-token") String accessToken, @Query("count") String count, @Query("open_id") String openId, @Query("cursor") String cursor);

    /**
     *
     * @param accessToken   token
     * @param openId        openid
     * @param cursor        分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。目前最多只支持获取 4 页
     * @param count         每页数量
     * @return
     */
    @GET("/video/list/")
    Call<VideosInfo> getVideoInfo(@Header("access-token") String accessToken, @Query("open_id") String openId, @Query("cursor") String cursor, @Query("count") String count);

}
