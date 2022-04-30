package com.example.bbackcodingcon.feature_memo.domain.use_case

import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.repository.MemoRepository
import com.example.bbackcodingcon.feature_memo.domain.util.MemoOrder
import com.example.bbackcodingcon.feature_memo.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetMemos(
    private val repository : MemoRepository
) {

    operator fun invoke(
        search: String,
        memoOrder: MemoOrder = MemoOrder.Date(OrderType.Descending)
    ): Flow<List<Memo>> {
        return repository.getMemos(search).map { memos ->
            when(memoOrder.orderType) {
                is OrderType.Ascending -> {
                    when(memoOrder) {
                        is MemoOrder.Title -> memos.sortedBy { it.title.lowercase() }
                        is MemoOrder.Date -> memos.sortedBy { it.timestamp }
                        is MemoOrder.Color -> memos.sortedBy { it.color }

                    }
                }
                is OrderType.Descending -> {
                    when(memoOrder) {
                        is MemoOrder.Title -> memos.sortedByDescending { it.title.lowercase() }
                        is MemoOrder.Date -> memos.sortedByDescending { it.timestamp }
                        is MemoOrder.Color -> memos.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}