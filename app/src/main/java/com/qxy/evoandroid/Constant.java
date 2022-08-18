package com.qxy.evoandroid;

/**
 * 常量类
 */
public class Constant {

    //密钥
    static final public String CLIENT_KEY = "awibl7nzc7dgmgqw";
    static final public String CLIENT_SECRET = "1610e7b6800cd97b169f681758d704ba";
    static final public String GRANT_TYPE = "authorization_code";
    static final public String CLIENT_TYPE = "client_credential";

    static final public String DOUYIN_OPENAPI = "https://open.douyin.com/";

    //榜单请求类型
    static final public int TYPE_MOVIE = 1;//电影
    static final public int TYPE_TELEPLAY = 2;//电视剧
    static final public int TYPE_VARIETY = 3;//综艺

    //消息
    static final public int TOKEN_GET_COMPLETE = 10001;
    static final public int TOKEN_GET_FAIL = 10002;
}
