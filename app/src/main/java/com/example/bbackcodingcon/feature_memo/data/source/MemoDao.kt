package com.example.bbackcodingcon.feature_memo.data.source

import androidx.room.*
import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import kotlinx.coroutines.flow.Flow

@Dao //Dao?
interface MemoDao {

    @Query("SELECT * FROM memo WHERE title LIKE '%' || :search || '%' or content LIKE '%' || :search || '%'")
    fun getMemos(search: String): Flow<List<Memo>>

    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun getMemoById(id: Int): Memo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMemo(memo: Memo)

    @Delete
    suspend fun delMemo(memo: Memo)
}