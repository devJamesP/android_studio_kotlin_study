package com.devjamesp.bookreviewactivity.dao

import androidx.room.*
import com.devjamesp.bookreviewactivity.model.History

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAll(): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history : History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    fun delete(keyword: String)
}