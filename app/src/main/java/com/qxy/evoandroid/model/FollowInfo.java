package com.qxy.evoandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * "/following/list/"接口回调信息
 */
public class FollowInfo {

    @SerializedName("extra")
    private ExtraDTO extra;
    @SerializedName("data")
    private DataDTO data;

    public ExtraDTO getExtra() {
        return extra;
    }

    public void setExtra(ExtraDTO extra) {
        this.extra = extra;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class ExtraDTO {
        @SerializedName("error_code")
        private Integer errorCode;
        @SerializedName("description")
        private String description;
        @SerializedName("sub_error_code")
        private Integer subErrorCode;
        @SerializedName("sub_description")
        private String subDescription;
        @SerializedName("logid")
        private String logid;
        @SerializedName("now")
        private Long now;

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getSubErrorCode() {
            return subErrorCode;
        }

        public void setSubErrorCode(Integer subErrorCode) {
            this.subErrorCode = subErrorCode;
        }

        public String getSubDescription() {
            return subDescription;
        }

        public void setSubDescription(String subDescription) {
            this.subDescription = subDescription;
        }

        public String getLogid() {
            return logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }

        public Long getNow() {
            return now;
        }

        public void setNow(Long now) {
            this.now = now;
        }
    }

    public static class DataDTO {
        @SerializedName("error_code")
        private Integer errorCode;
        @SerializedName("description")
        private String description;
        @SerializedName("cursor")
        private String cursor;
        @SerializedName("has_more")
        private Boolean hasMore;
        @SerializedName("list")
        private List<ListDTO> list;

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCursor() {
            return cursor;
        }

        public void setCursor(String cursor) {
            this.cursor = cursor;
        }

        public Boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO {
            @SerializedName("open_id")
            private String openId;
            @SerializedName("union_id")
            private String unionId;
            @SerializedName("nickname")
            private String nickname;
            @SerializedName("avatar")
            private String avatar;
            @SerializedName("city")
            private String city;
            @SerializedName("province")
            private String province;
            @SerializedName("country")
            private String country;
            @SerializedName("gender")
            private Integer gender;

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }

            public String getUnionId() {
                return unionId;
            }

            public void setUnionId(String unionId) {
                this.unionId = unionId;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public Integer getGender() {
                return gender;
            }

            public void setGender(Integer gender) {
                this.gender = gender;
            }
        }
    }
}
