package com.example.bbackcodingcon.feature_memo.presentation.memos

import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.util.MemoOrder
import com.example.bbackcodingcon.feature_memo.domain.util.OrderType

data class MemosState(
    val memos: List<Memo> = emptyList(),
    val memoOrder: MemoOrder = MemoOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
