package com.example.bbackcodingcon.feature_memo.domain.use_case

import com.example.bbackcodingcon.feature_memo.domain.model.InvalidMemoException
import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.repository.MemoRepository

class AddMemo(
    private val repository: MemoRepository
) {

    @Throws(InvalidMemoException::class)
    suspend operator fun invoke(memo: Memo) {
        if(memo.title.isBlank()) {
            throw InvalidMemoException("The title of the memo can't be empty.")
        }
        if(memo.content.isBlank()) {
            throw InvalidMemoException("The content of the memo can't be empty.")
        }
        repository.addMemo(memo)
    }
}