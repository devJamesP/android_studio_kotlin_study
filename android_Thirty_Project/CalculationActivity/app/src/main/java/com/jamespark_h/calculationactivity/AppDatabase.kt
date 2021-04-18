package com.jamespark_h.calculationactivity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jamespark_h.calculationactivity.dao.HistoryDao
import com.jamespark_h.calculationactivity.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}