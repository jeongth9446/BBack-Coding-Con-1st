package com.example.bbackcodingcon.feature_memo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bbackcodingcon.ui.theme.*

@Entity //Room을 사용해야 함.
data class Memo(
    val title: String,
    val content: String,
    val timestamp : Long,
    val color: Int,
    val password: String,
    val locked: Boolean,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val memoColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}


class InvalidMemoException(message: String): Exception(message)