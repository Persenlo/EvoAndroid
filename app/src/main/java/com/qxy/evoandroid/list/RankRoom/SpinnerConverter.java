package com.qxy.evoandroid.list.RankRoom;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.qxy.evoandroid.model.RankVersion;

public class SpinnerConverter {
    //Gson方法把json格式的string转成List
    @TypeConverter
    public static RankVersion revert(String versionData){
        return new Gson().fromJson(versionData,RankVersion.class);
    }


    //把List转成json格式的string
    @TypeConverter
    public static String converter(RankVersion rankVersion){
        return new Gson().toJson(rankVersion);
    }
}
