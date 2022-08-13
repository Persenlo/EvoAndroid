package com.qxy.evoandroid.list;

import android.content.Context;

import androidx.room.Room;

import com.qxy.evoandroid.list.RankRoom.Rank;
import com.qxy.evoandroid.list.RankRoom.RankDao;
import com.qxy.evoandroid.list.RankRoom.RankDataBase;
import com.qxy.evoandroid.list.RankRoom.RankDetail;
import com.qxy.evoandroid.model.VideoRank;
import com.qxy.evoandroid.model.VideoRank.DataDTO.ListDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CacheRepository {
    private final RankDao rankDao;
    private List<String> keyList=new ArrayList<>();

    public CacheRepository(Context context) {
        //获取DAO
        RankDataBase rankDataBase= Room.databaseBuilder(context,RankDataBase.class,"rank_cache_database")
                .allowMainThreadQueries()
                .build();
        rankDao = rankDataBase.getRankDao();
    }

    //保存某一榜单某一版本具体数据
    public void SaveCache(VideoRank videoRank,int version,int type){
        //符合保存条件就保存
        if(needToSave(type,version)) {
            //榜单生成时间
            String time = videoRank.getData().getActiveTime();

            //保存当前时间
            String lastTime = getNowTime();

//            List<RankDetail> rankDetails = new ArrayList<>();
//
//            //榜单具体信息
//            for (int i = 0; i < videoRank.getData().getList().size(); i++) {
//                ListDTO data = videoRank.getData().getList().get(i);
//                //排名
//                StringBuilder rank = new StringBuilder();
//                if (i == 0 || i == 1 || i == 2) rank.append("Top");
//                rank.append(i + 1);
//                //海报url
//                String url = data.getPoster();
//                //设置名称
//                StringBuilder name = new StringBuilder();
//                name.append(data.getName());
//                if (!data.getNameEn().equals(""))
//                    name.append("(").append(data.getNameEn()).append(")");
//                //设置发行时间+地域
//                StringBuilder area = new StringBuilder();
//                area.append(data.getReleaseDate());
//                if (data.getAreas() != null) {
//                    for (String s : data.getAreas()) {
//                        area.append("/").append(s);
//                    }
//                }
//                //设置Tags
//                StringBuilder tags = new StringBuilder();
//                if (data.getTags() != null) {
//                    for (int j = 0; j < data.getTags().size(); j++) {
//                        tags.append(data.getTags().get(j));
//                        if (j != data.getTags().size() - 1) tags.append("/");
//                    }
//                }
//                //设置Actors/Directors
//                StringBuilder people = new StringBuilder();
//                if (data.getActors() != null) {
//                    for (int j = 0; j < data.getActors().size(); j++) {
//                        people.append(data.getActors().get(j));
//                        if (j != data.getActors().size() - 1) people.append("/");
//                    }
//                } else if (data.getDirectors() != null) {
//                    people.append("导演:");
//                    for (int j = 0; j < data.getDirectors().size(); j++) {
//                        people.append(data.getDirectors().get(j));
//                        if (j != data.getDirectors().size() - 1) people.append("/");
//                    }
//                }
//                //热度
//                String hot = "总热度：" + data.getHot();
//                String DiscussionHot = "讨论热度：" + data.getDiscussionHot();
//                String TopicHot = "主题热度：" + data.getTopicHot();
//                String SearchHot = "搜索热度：" + data.getSearchHot();
//                String InfluenceHot = "影响热度：" + data.getInfluenceHot();
//
//                RankDetail rankDetail = new RankDetail(rank.toString(), url, name.toString(), area.toString(), tags.toString(), people.toString(), hot,
//                        DiscussionHot, TopicHot, SearchHot, InfluenceHot);
//
//                rankDetails.add(rankDetail);
//
//            }

            //添加到数据库
            Rank rank = new Rank(type, version, time,videoRank,lastTime);
            rankDao.insertData(rank);
        }
    }

    //判断是否需要保存某一榜单某一版本数据
    public boolean needToSave(int type,int version){
        getRankMap();
        //是否达到保存上线
        if(keyList.size()>=10) return false;
        //是否保存过该榜单
        String key=String.valueOf(type)+"-"+String.valueOf(version);
        return !keyList.contains(key);
    }

    //获取已保存榜单数据
    private void getRankMap(){
        for(Rank rank:rankDao.getAll()){
            String key=rank.getType()+"-"+rank.getVersion();
            if(!keyList.contains(key)) keyList.add(key);
        }
    }

    //清除所有缓存
    public void cleanAllCache(){
        rankDao.deleteAllRanks();
    }

    //清除过期缓存
    private void cleanOutDatedCache(){
        for(Rank rank:rankDao.getAll()){
            // 日期格式化
            DateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate=null;
            try {
                startDate = simpleFormat.parse(getNowTime());
                endDate = simpleFormat.parse(rank.getLastTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            int days = (int) ((endTime - startTime) / (1000 * 60 * 60 * 24));

            //超过10天未访问，数据库删除对应Rank
            if(days>10) rankDao.deleteData(rank);
        }
    }

    //判断是否存在某一榜单某一版本缓存数据
    public boolean isExistRankCache(int type,int version){
        getRankMap();
        String key=String.valueOf(type)+"-"+String.valueOf(version);
        return keyList.contains(key);
    }

    //获取某一榜单某一版本具体数据
    public Rank getPointRankData(int type,int version){
        return rankDao.getPointRank(type,version);
    }

    //更新榜单最后一次访问时间
    public void UpdateRankLastTime(int type,int version){
        rankDao.getPointRank(type,version).setLastTime(getNowTime());
    }

    //获取当前时间“yyyy-MM-dd”String类型
    private String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }


}
