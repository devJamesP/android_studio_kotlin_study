package com.devjamesp.bookreviewactivity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.devjamesp.bookreviewactivity.dao.HistoryDao
import com.devjamesp.bookreviewactivity.dao.ReviewDao
import com.devjamesp.bookreviewactivity.model.History
import com.devjamesp.bookreviewactivity.model.Review

@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}

fun getAppDatabase(context: Context): AppDatabase {

    /** 새로운 DB파일을 생성하게 되면 버전을 올려주거나 기존 앱을 삭제하고 다시 실행하든지 해야한다.
    버전을 올리려면 마이그레이션이 필요하다!! */
    var migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE `REVIEW` (`id` INTEGER, `review` TEXT," + "PRIMARY KEY(`id`))")
        }
    }

    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "BookSearchDB"
    ).addMigrations(migration_1_2)
        .build()
}



