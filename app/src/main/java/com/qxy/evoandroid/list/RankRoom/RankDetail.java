package com.qxy.evoandroid.list.RankRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity(tableName = "rankDetailTable")
public class RankDetail {
//    @PrimaryKey(autoGenerate = true)
//    private int id;

    //@ColumnInfo(name = "rank_rank")//当前榜单排行
    private String rank;
    //@ColumnInfo(name = "poster")//海报url
    private String url;
    //@ColumnInfo(name = "names")//名称
    private String names;
    //@ColumnInfo(name = "areas")//上映时间+地域
    private String areas;
   // @ColumnInfo(name = "tags")//tags
    private String tags;
    //@ColumnInfo(name = "actors")//演员/导演列表
    private String actors;
    //@ColumnInfo(name = "hot")
    private String hot;
   // @ColumnInfo(name = "DiscussionHot")
    private String DiscussionHot;
   // @ColumnInfo(name = "TopicHot")
    private String TopicHot;
   // @ColumnInfo(name = "SearchHot")
    private String SearchHot;
  //  @ColumnInfo(name = "InfluenceHot")
    private String InfluenceHot;

    public RankDetail() {
    }

    public RankDetail(String rank, String url, String names, String areas, String tags, String actors,
                      String hot, String discussionHot, String topicHot, String searchHot, String influenceHot) {
        this.rank = rank;
        this.url = url;
        this.names = names;
        this.areas = areas;
        this.tags = tags;
        this.actors = actors;
        this.hot = hot;
        DiscussionHot = discussionHot;
        TopicHot = topicHot;
        SearchHot = searchHot;
        InfluenceHot = influenceHot;
    }



    public String getRank() {
        return rank;
    }

    public String getUrl() {
        return url;
    }

    public String getNames() {
        return names;
    }

    public String getAreas() {
        return areas;
    }

    public String getTags() {
        return tags;
    }

    public String getActors() {
        return actors;
    }

    public String getHot() {
        return hot;
    }

    public String getDiscussionHot() {
        return DiscussionHot;
    }

    public String getTopicHot() {
        return TopicHot;
    }

    public String getSearchHot() {
        return SearchHot;
    }

    public String getInfluenceHot() {
        return InfluenceHot;
    }

}
