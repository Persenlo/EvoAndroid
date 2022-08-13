package com.qxy.evoandroid.list.RankRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SpinnerDao {
    @Insert
    void insertData(SpinnerData... spinnerDatas);

    @Update
    void updateData(SpinnerData... spinnerDatas);

    @Query("DELETE FROM historyTime")
    void deleteData();

    @Query("SELECT *FROM historyTime")
    SpinnerData getData();

}
