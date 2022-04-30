package com.example.bbackcodingcon.feature_memo.domain.use_case

import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.repository.MemoRepository

class GetMemo (
    private val repository: MemoRepository
) {

    suspend operator fun invoke(id: Int): Memo? {
        return repository.getMemoById(id)
    }
}