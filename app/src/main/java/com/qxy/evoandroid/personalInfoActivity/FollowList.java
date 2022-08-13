package com.qxy.evoandroid.personalInfoActivity;

import com.google.gson.annotations.SerializedName;
import com.qxy.evoandroid.model.UserInfo;

import java.io.Serializable;
import java.sql.Struct;

public class FollowList implements Serializable {
    @SerializedName("data")
    private FollowList.DataDTO data;
    @SerializedName("message")
    private String message;

    public FollowList.DataDTO getData() {
        return data;
    }

    public void setData(FollowList.DataDTO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataDTO implements Serializable {
        @SerializedName("error_code")
        private Integer FerrorCode;
        @SerializedName("list")
        private Struct List;

        public Integer getFerrorCode() {
            return FerrorCode;
        }

        public void setFerrorCode(Integer ferrorCode) {
            FerrorCode = ferrorCode;
        }


        public Struct getList() {
            return List;
        }

        public void setList(Struct list) {
            List = list;
        }
    }
}
