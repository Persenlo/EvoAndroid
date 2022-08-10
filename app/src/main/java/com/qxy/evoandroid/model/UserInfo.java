package com.qxy.evoandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * "/oauth/userinfo/"接口回调信息
 */
public class UserInfo implements Serializable {


    @SerializedName("data")
    private DataDTO data;
    @SerializedName("message")
    private String message;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataDTO implements Serializable {
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("avatar_larger")
        private String avatarLarger;
        @SerializedName("captcha")
        private String captcha;
        @SerializedName("city")
        private String city;
        @SerializedName("client_key")
        private String clientKey;
        @SerializedName("country")
        private String country;
        @SerializedName("desc_url")
        private String descUrl;
        @SerializedName("description")
        private String description;
        @SerializedName("district")
        private String district;
        @SerializedName("e_account_role")
        private String eAccountRole;
        @SerializedName("error_code")
        private Integer errorCode;
        @SerializedName("gender")
        private Integer gender;
        @SerializedName("log_id")
        private String logId;
        @SerializedName("nickname")
        private String nickname;
        @SerializedName("open_id")
        private String openId;
        @SerializedName("province")
        private String province;
        @SerializedName("union_id")
        private String unionId;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatarLarger() {
            return avatarLarger;
        }

        public void setAvatarLarger(String avatarLarger) {
            this.avatarLarger = avatarLarger;
        }

        public String getCaptcha() {
            return captcha;
        }

        public void setCaptcha(String captcha) {
            this.captcha = captcha;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getClientKey() {
            return clientKey;
        }

        public void setClientKey(String clientKey) {
            this.clientKey = clientKey;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDescUrl() {
            return descUrl;
        }

        public void setDescUrl(String descUrl) {
            this.descUrl = descUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getEAccountRole() {
            return eAccountRole;
        }

        public void setEAccountRole(String eAccountRole) {
            this.eAccountRole = eAccountRole;
        }

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }
    }
}
