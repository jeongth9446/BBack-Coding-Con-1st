package com.example.bbackcodingcon.feature_memo.data.repository

import com.example.bbackcodingcon.feature_memo.data.source.MemoDao
import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow


class MemoRepositoryImpl (
    private val dao: MemoDao
) : MemoRepository {

    override fun getMemos(search: String): Flow<List<Memo>> {
        return dao.getMemos(search)
    }

    override suspend fun getMemoById(id: Int): Memo? {
        return dao.getMemoById(id)
    }

    override suspend fun addMemo(memo: Memo) {
        dao.addMemo(memo)
    }

    override suspend fun delMemo(memo: Memo) {
        dao.delMemo(memo)
    }
}