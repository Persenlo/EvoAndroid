package com.qxy.evoandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoRank {
    @SerializedName("data")
    private DataDTO data;
    @SerializedName("extra")
    private ExtraDTO extra;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public ExtraDTO getExtra() {
        return extra;
    }

    public void setExtra(ExtraDTO extra) {
        this.extra = extra;
    }

    public static class DataDTO {
        @SerializedName("active_time")
        private String activeTime;
        @SerializedName("description")
        private String description;
        @SerializedName("error_code")
        private String errorCode;
        @SerializedName("list")
        private List<ListDTO> list;

        public String getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(String activeTime) {
            this.activeTime = activeTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO {
            @SerializedName("discussion_hot")
            private String discussionHot;
            @SerializedName("hot")
            private String hot;
            @SerializedName("id")
            private String id;
            @SerializedName("influence_hot")
            private String influenceHot;
            @SerializedName("maoyan_id")
            private String maoyanId;
            @SerializedName("name")
            private String name;
            @SerializedName("name_en")
            private String nameEn;
            @SerializedName("poster")
            private String poster;
            @SerializedName("release_date")
            private String releaseDate;
            @SerializedName("search_hot")
            private String searchHot;
            @SerializedName("topic_hot")
            private String topicHot;
            @SerializedName("type")
            private String type;
            @SerializedName("actors")
            private List<String> actors;
            @SerializedName("areas")
            private List<String> areas;
            @SerializedName("directors")
            private List<String> directors;
            @SerializedName("tags")
            private List<String> tags;

            public String getDiscussionHot() {
                return discussionHot;
            }

            public void setDiscussionHot(String discussionHot) {
                this.discussionHot = discussionHot;
            }

            public String getHot() {
                return hot;
            }

            public void setHot(String hot) {
                this.hot = hot;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getInfluenceHot() {
                return influenceHot;
            }

            public void setInfluenceHot(String influenceHot) {
                this.influenceHot = influenceHot;
            }

            public String getMaoyanId() {
                return maoyanId;
            }

            public void setMaoyanId(String maoyanId) {
                this.maoyanId = maoyanId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNameEn() {
                return nameEn;
            }

            public void setNameEn(String nameEn) {
                this.nameEn = nameEn;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public String getReleaseDate() {
                return releaseDate;
            }

            public void setReleaseDate(String releaseDate) {
                this.releaseDate = releaseDate;
            }

            public String getSearchHot() {
                return searchHot;
            }

            public void setSearchHot(String searchHot) {
                this.searchHot = searchHot;
            }

            public String getTopicHot() {
                return topicHot;
            }

            public void setTopicHot(String topicHot) {
                this.topicHot = topicHot;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<String> getActors() {
                return actors;
            }

            public void setActors(List<String> actors) {
                this.actors = actors;
            }

            public List<String> getAreas() {
                return areas;
            }

            public void setAreas(List<String> areas) {
                this.areas = areas;
            }

            public List<String> getDirectors() {
                return directors;
            }

            public void setDirectors(List<String> directors) {
                this.directors = directors;
            }

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }
        }
    }

    public static class ExtraDTO {
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

        public void setDescription(String description) {
            this.description = description;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getLogid() {
            return logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }

        public String getNow() {
            return now;
        }

        public void setNow(String now) {
            this.now = now;
        }

        public String getSubDescription() {
            return subDescription;
        }

        public void setSubDescription(String subDescription) {
            this.subDescription = subDescription;
        }

        public String getSubErrorCode() {
            return subErrorCode;
        }

        public void setSubErrorCode(String subErrorCode) {
            this.subErrorCode = subErrorCode;
        }
    }
}
