package com.example.bbackcodingcon.feature_memo.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bbackcodingcon.feature_memo.domain.model.Memo

@Database(
    entities = [Memo::class],
    version = 1
)
abstract class MemoDatabase: RoomDatabase() {

    abstract val memoDao: MemoDao

    companion object {
        const val DATABASE_NAME = "memos_db"
    }
}