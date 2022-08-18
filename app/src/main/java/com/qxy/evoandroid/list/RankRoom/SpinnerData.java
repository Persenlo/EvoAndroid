package com.qxy.evoandroid.list.RankRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.qxy.evoandroid.model.RankVersion;

@Entity(tableName = "historyTime")
public class SpinnerData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "last_update_day")
    private String lastDay;
    @ColumnInfo(name = "version_data")
    private RankVersion rankVersion;

    @Ignore
    public SpinnerData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpinnerData(String lastDay, RankVersion rankVersion) {
        this.lastDay = lastDay;
        this.rankVersion = rankVersion;
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public RankVersion getRankVersion() {
        return rankVersion;
    }

    public void setRankVersion(RankVersion rankVersion) {
        this.rankVersion = rankVersion;
    }
}
