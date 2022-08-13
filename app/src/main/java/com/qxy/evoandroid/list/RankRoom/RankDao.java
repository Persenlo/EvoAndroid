package com.qxy.evoandroid.list.RankRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RankDao {
    @Insert
    void insertData(Rank... ranks);

    @Update
    void updateData(Rank... ranks);

    @Delete
    void deleteData(Rank... ranks);

    @Query("DELETE FROM rankTable")
    void deleteAllRanks();

    @Query("SELECT *FROM rankTable")
    List<Rank> getAll();

    @Query("SELECT *FROM rankTable WHERE rank_type=:type AND rank_version=:version")
    Rank getPointRank(int type,int version);

}
