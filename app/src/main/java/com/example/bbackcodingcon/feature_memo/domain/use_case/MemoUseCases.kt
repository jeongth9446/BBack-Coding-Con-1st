package com.example.bbackcodingcon.feature_memo.domain.use_case

data class MemoUseCases(
    val getMemos: GetMemos,
    val delMemo: DelMemo,
    val addMemo: AddMemo,
    val getMemo: GetMemo
) {
}