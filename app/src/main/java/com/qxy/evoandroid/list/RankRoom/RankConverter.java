package com.qxy.evoandroid.list.RankRoom;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.qxy.evoandroid.model.VideoRank;

import java.util.List;

public class RankConverter {


//    //Gson方法把json格式的string转成List
//    @TypeConverter
//    public static List<RankDetail> revert(String rankDatas){
//        return new Gson().fromJson(rankDatas,new TypeToken<List<RankDetail>>(){}.getType());
//    }
//
//
//    //把List转成json格式的string
//    @TypeConverter static String converter(List<RankDetail> rankDetails){
//        return new Gson().toJson(rankDetails);
//    }

    //Gson方法把json格式的string转成List
    @TypeConverter
    public static VideoRank revert(String rankData){
        return new Gson().fromJson(rankData,VideoRank.class);
    }


    //把List转成json格式的string
    @TypeConverter static String converter(VideoRank videoRank){
        return new Gson().toJson(videoRank);
    }
}
