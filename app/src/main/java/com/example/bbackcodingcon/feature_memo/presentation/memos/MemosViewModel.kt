package com.example.bbackcodingcon.feature_memo.presentation.memos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.use_case.MemoUseCases
import com.example.bbackcodingcon.feature_memo.domain.util.MemoOrder
import com.example.bbackcodingcon.feature_memo.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MemosViewModel @Inject constructor(
    private val memoUseCases: MemoUseCases
): ViewModel() {

    private val _state = mutableStateOf(MemosState())
    val state: State<MemosState> = _state

    private var recentlyDeletedMemo: Memo? = null

    private var getMemosJob: Job? = null

    var searchText = mutableStateOf("")

    init {
        //기본 값은 최신 메모가 위에 표시되도록
        getMemos("", MemoOrder.Date(OrderType.Descending))
    }

    fun onEvent(search: String, event: MemosEvent) {
        when(event) {
            is MemosEvent.Order -> {
                getMemos(search, event.memoOrder)
            }
            is MemosEvent.DeleteMemo -> {
                viewModelScope.launch {
                    memoUseCases.delMemo( event.memo )
                    recentlyDeletedMemo = event.memo
                }
            }
            is MemosEvent.RestoreMemo -> {
                viewModelScope.launch {
                    memoUseCases.addMemo(recentlyDeletedMemo ?: return@launch)
                    recentlyDeletedMemo = null

                }
            }
            is MemosEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getMemos(search: String, memoOrder: MemoOrder) {
        getMemosJob?.cancel()
        memoUseCases.getMemos(search, memoOrder)
            .onEach { memos ->
                _state.value = state.value.copy(
                    memos = memos,
                    memoOrder = memoOrder
                )

            }.launchIn(viewModelScope)
    }
}