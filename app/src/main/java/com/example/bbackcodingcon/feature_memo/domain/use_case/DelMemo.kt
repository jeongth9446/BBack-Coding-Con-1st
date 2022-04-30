package com.example.bbackcodingcon.feature_memo.domain.use_case

import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.repository.MemoRepository

class DelMemo(
    private val repository: MemoRepository
) {

    suspend operator fun invoke(memo: Memo) {
        repository.delMemo(memo)
    }
}