package com.example.bbackcodingcon.feature_memo.presentation.memos.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bbackcodingcon.feature_memo.domain.util.MemoOrder
import com.example.bbackcodingcon.feature_memo.domain.util.OrderType
import com.example.bbackcodingcon.feature_memo.presentation.memos.MemosViewModel


@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    memoOrder: MemoOrder = MemoOrder.Date(OrderType.Descending),
    onOrderChange: (MemoOrder) -> Unit,
    viewModel: MemosViewModel
) {
    Column(
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("다음을 포함하는 메모만 표시 : ")
            TextField(
                value = viewModel.searchText.value,
                onValueChange = {
                    viewModel.searchText.value = it
                },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("정렬 기준 : ")
            DefaultRadioButton(
                text = "제목",
                selected = memoOrder is MemoOrder.Title,
                onSelect = { onOrderChange(MemoOrder.Title(memoOrder.orderType)) }
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultRadioButton(
                text = "날짜",
                selected = memoOrder is MemoOrder.Date,
                onSelect = { onOrderChange(MemoOrder.Date(memoOrder.orderType)) }
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultRadioButton(
                text = "색상",
                selected = memoOrder is MemoOrder.Color,
                onSelect = { onOrderChange(MemoOrder.Color(memoOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("정렬 방식 : ")
            DefaultRadioButton(
                text = "오름차순정렬",
                selected = memoOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(memoOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultRadioButton(
                text = "내림차순정렬",
                selected = memoOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(memoOrder.copy(OrderType.Descending))  }
            )
        }
    }
}