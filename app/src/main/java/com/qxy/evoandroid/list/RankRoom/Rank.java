package com.qxy.evoandroid.list.RankRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.qxy.evoandroid.model.VideoRank;

import java.util.List;

@Entity(tableName = "rankTable")
public class Rank {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "rank_type")//排行榜类型
    private int type;
    @ColumnInfo(name = "rank_version")//排行榜版本
    private int version;
    @ColumnInfo(name = "rank_active_time")//榜单生成时间
    private String time;
    @ColumnInfo(name = "video_rank")
    private VideoRank videoRank;
    @ColumnInfo(name = "rank_detail")
    private List<RankDetail> rankDetails;
    @ColumnInfo(name = "rank_last_time")//最后一次访问榜单时间
    private String lastTime;


    public Rank() {
    }

    public Rank(int type, int version, String time, VideoRank videoRank, String lastTime) {
        this.type = type;
        this.version = version;
        this.time = time;
        this.videoRank = videoRank;
        this.lastTime = lastTime;
    }

    public Rank(int type, int version, String time, String lastTime, List<RankDetail> rankDetails) {
        this.type = type;
        this.version = version;
        this.time = time;
        this.lastTime=lastTime;
        this.rankDetails = rankDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<RankDetail> getRankDetails() {
        return rankDetails;
    }

    public void setRankDetails(List<RankDetail> rankDetails) {
        this.rankDetails = rankDetails;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public VideoRank getVideoRank() {
        return videoRank;
    }

    public void setVideoRank(VideoRank videoRank) {
        this.videoRank = videoRank;
    }
}
