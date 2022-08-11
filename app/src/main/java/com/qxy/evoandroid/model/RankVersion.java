package com.qxy.evoandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RankVersion {
    private VersionData data;
    private VersionExtra extra;

    public VersionData getData() {
        return data;
    }

    public VersionExtra getExtra() {
        return extra;
    }

    public static class VersionData{
        @SerializedName("cursor")
        private String cursor;
        @SerializedName("description")
        private String description;
        @SerializedName("error_code")
        private String errorCode;
        @SerializedName("has_more")
        private String hasMore;
        @SerializedName("list")
        private List<VersionListDTO> list;

        public String getCursor() {
            return cursor;
        }

        public String getDescription() {
            return description;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public String getHasMore() {
            return hasMore;
        }

        public List<VersionListDTO> getList() {
            return list;
        }

        public static class VersionListDTO{
            @SerializedName("active_time")
            private String activeTime;
            @SerializedName("end_time")
            private String endTime;
            @SerializedName("start_time")
            private String startTime;
            @SerializedName("type")
            private String type;
            @SerializedName("version")
            private String version;

            public String getActiveTime() {
                return activeTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public String getStartTime() {
                return startTime;
            }

            public String getType() {
                return type;
            }

            public String getVersion() {
                return version;
            }
        }
    }

    public static class VersionExtra{
        @SerializedName("description")
        private String description;
        @SerializedName("error_code")
        private String errorCode;
        @SerializedName("logid")
        private String logid;
        @SerializedName("now")
        private String now;
        @SerializedName("sub_description")
        private String subDescription;
        @SerializedName("sub_error_code")
        private String subErrorCode;

        public String getDescription() {
            return description;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public String getLogid() {
            return logid;
        }

        public String getNow() {
            return now;
        }

        public String getSubDescription() {
            return subDescription;
        }

        public String getSubErrorCode() {
            return subErrorCode;
        }
    }
}
