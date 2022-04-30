package com.example.bbackcodingcon.feature_memo.presentation.memos

import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.util.MemoOrder

sealed class MemosEvent {
    data class Order(val memoOrder: MemoOrder): MemosEvent()
    data class DeleteMemo(val memo: Memo): MemosEvent()
    object RestoreMemo: MemosEvent()
    object ToggleOrderSection: MemosEvent()
}
