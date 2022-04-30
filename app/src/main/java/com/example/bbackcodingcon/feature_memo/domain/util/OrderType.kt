package com.example.bbackcodingcon.feature_memo.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
