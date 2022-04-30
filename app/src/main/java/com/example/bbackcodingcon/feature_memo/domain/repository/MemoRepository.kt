package com.example.bbackcodingcon.feature_memo.domain.repository

import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import kotlinx.coroutines.flow.Flow


interface MemoRepository {

    fun getMemos(search: String): Flow<List<Memo>>

    suspend fun getMemoById(id: Int): Memo?

    suspend fun addMemo(memo: Memo)

    suspend fun delMemo(memo: Memo)

}