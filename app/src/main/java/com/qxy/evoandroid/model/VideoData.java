package com.qxy.evoandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * "/video/data/"返回数据
 */
public class VideoData {

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
        @SerializedName("error_code")
        private String errorCode;
        @SerializedName("description")
        private String description;
        @SerializedName("list")
        private List<ListDTO> list;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO {
            @SerializedName("item_id")
            private String itemId;
            @SerializedName("statistics")
            private StatisticsDTO statistics;
            @SerializedName("title")
            private String title;
            @SerializedName("video_status")
            private String videoStatus;
            @SerializedName("cover")
            private String cover;
            @SerializedName("create_time")
            private String createTime;
            @SerializedName("is_reviewed")
            private Boolean isReviewed;
            @SerializedName("video_id")
            private String videoId;
            @SerializedName("is_top")
            private Boolean isTop;
            @SerializedName("media_type")
            private String mediaType;
            @SerializedName("share_url")
            private String shareUrl;

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public StatisticsDTO getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsDTO statistics) {
                this.statistics = statistics;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVideoStatus() {
                return videoStatus;
            }

            public void setVideoStatus(String videoStatus) {
                this.videoStatus = videoStatus;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Boolean isIsReviewed() {
                return isReviewed;
            }

            public void setIsReviewed(Boolean isReviewed) {
                this.isReviewed = isReviewed;
            }

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public Boolean isIsTop() {
                return isTop;
            }

            public void setIsTop(Boolean isTop) {
                this.isTop = isTop;
            }

            public String getMediaType() {
                return mediaType;
            }

            public void setMediaType(String mediaType) {
                this.mediaType = mediaType;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public static class StatisticsDTO {
                @SerializedName("share_count")
                private String shareCount;
                @SerializedName("comment_count")
                private String commentCount;
                @SerializedName("digg_count")
                private String diggCount;
                @SerializedName("download_count")
                private String downloadCount;
                @SerializedName("forward_count")
                private String forwardCount;
                @SerializedName("play_count")
                private String playCount;

                public String getShareCount() {
                    return shareCount;
                }

                public void setShareCount(String shareCount) {
                    this.shareCount = shareCount;
                }

                public String getCommentCount() {
                    return commentCount;
                }

                public void setCommentCount(String commentCount) {
                    this.commentCount = commentCount;
                }

                public String getDiggCount() {
                    return diggCount;
                }

                public void setDiggCount(String diggCount) {
                    this.diggCount = diggCount;
                }

                public String getDownloadCount() {
                    return downloadCount;
                }

                public void setDownloadCount(String downloadCount) {
                    this.downloadCount = downloadCount;
                }

                public String getForwardCount() {
                    return forwardCount;
                }

                public void setForwardCount(String forwardCount) {
                    this.forwardCount = forwardCount;
                }

                public String getPlayCount() {
                    return playCount;
                }

                public void setPlayCount(String playCount) {
                    this.playCount = playCount;
                }
            }
        }
    }

    public static class ExtraDTO {
        @SerializedName("logid")
        private String logid;
        @SerializedName("error_code")
        private String errorCode;
        @SerializedName("description")
        private String description;
        @SerializedName("sub_error_code")
        private String subErrorCode;
        @SerializedName("sub_description")
        private String subDescription;
        @SerializedName("now")
        private String now;

        public String getLogid() {
            return logid;
        }

        public void setLogid(String logid) {
            this.logid = logid;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSubErrorCode() {
            return subErrorCode;
        }

        public void setSubErrorCode(String subErrorCode) {
            this.subErrorCode = subErrorCode;
        }

        public String getSubDescription() {
            return subDescription;
        }

        public void setSubDescription(String subDescription) {
            this.subDescription = subDescription;
        }

        public String getNow() {
            return now;
        }

        public void setNow(String now) {
            this.now = now;
        }
    }
}
