package com.qxy.evoandroid.list.RankRoom;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Rank.class}, version = 1, exportSchema = false)
@TypeConverters({RankConverter.class})
public abstract class RankDataBase extends RoomDatabase {
    public abstract RankDao getRankDao();
}
