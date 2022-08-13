package com.qxy.evoandroid.list;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.room.Room;

import com.qxy.evoandroid.list.RankRoom.Rank;
import com.qxy.evoandroid.list.RankRoom.RankDao;
import com.qxy.evoandroid.list.RankRoom.RankDataBase;
import com.qxy.evoandroid.list.RankRoom.SpinnerDao;
import com.qxy.evoandroid.list.RankRoom.SpinnerData;
import com.qxy.evoandroid.model.RankVersion;
import com.qxy.evoandroid.model.VideoRank;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//排行版缓存仓库
public class CacheRepository {
    private final RankDao rankDao;
    private final List<String> keyList=new ArrayList<>();
    private final SpinnerDao spinnerDao;

    public CacheRepository(Context context) {
        //获取DAO
        RankDataBase rankDataBase=Room.databaseBuilder(context.getApplicationContext(),RankDataBase.class,"rank_cache_database")
                    .allowMainThreadQueries()
                    .build();
        rankDao = rankDataBase.getRankDao();
        spinnerDao = rankDataBase.getSpinnerDao();
        cleanOutDatedCache();
    }

    //保存某一榜单某一版本具体数据
    public void SaveCache(VideoRank videoRank,int version,int type){
        //符合保存条件就保存
        if(needToSave(type,version)) {
            //榜单生成时间
            String time = videoRank.getData().getActiveTime();

            //保存当前时间
            String lastTime = getNowTime();

            //添加到数据库
            Rank rank = new Rank(type, version, time,videoRank,lastTime);
            rankDao.insertData(rank);
        }
    }

    public boolean isSpinnerEmpty(){
        return spinnerDao.getData()==null;
    }


    //每日更新历史记录列表
    public void updateSpinner(RankVersion rankVersion) {
        if(isSpinnerEmpty()){
            SpinnerData spinnerData=new SpinnerData(getNowTime(),rankVersion);
            spinnerDao.insertData(spinnerData);
        }
        if(!spinnerDao.getData().getLastDay().equals(getNowTime())){
            SpinnerData spinnerData=new SpinnerData(getNowTime(),rankVersion);
            spinnerDao.updateData(spinnerData);
        }
    }

    //获取历史纪录列表
    public RankVersion getSpinnerData(){
        return spinnerDao.getData().getRankVersion();
    }

    //判断是否需要保存某一榜单某一版本数据
    public boolean needToSave(int type,int version){
        getRankMap();
        //是否达到保存上线
        if(keyList.size()>=10) return false;
        //是否保存过该榜单
        String key= type +"-"+ version;
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
    public void cleanOutDatedCache(){
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
        String key= type +"-"+ version;
        return keyList.contains(key);
    }

    //获取某一榜单某一版本具体数据
    public Rank getPointRankData(int type,int version){
        return rankDao.getPointRank(type,version);
    }

    //更新榜单最后一次访问时间
    public void UpdateRankLastTime(int type,int version){
        if (isExistRankCache(type,version)) rankDao.getPointRank(type,version).setLastTime(getNowTime());
    }

    //获取当前时间“yyyy-MM-dd”String类型
    private String getNowTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    //判断是否联网
    public boolean isNet(Context context){
        if(context!=null){
            //获取连接管理器
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取网络状态
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null) return networkInfo.isAvailable();
        }
        return false;
    }


}
