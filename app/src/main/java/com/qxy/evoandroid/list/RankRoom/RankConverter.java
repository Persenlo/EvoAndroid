package com.qxy.evoandroid.list.RankRoom;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.qxy.evoandroid.model.VideoRank;

public class RankConverter {

    //Gson方法把json格式的string转成List
    @TypeConverter
    public static VideoRank revert(String rankData){
        return new Gson().fromJson(rankData,VideoRank.class);
    }


    //把List转成json格式的string
    @TypeConverter
    public static String converter(VideoRank videoRank){
        return new Gson().toJson(videoRank);
    }
}
