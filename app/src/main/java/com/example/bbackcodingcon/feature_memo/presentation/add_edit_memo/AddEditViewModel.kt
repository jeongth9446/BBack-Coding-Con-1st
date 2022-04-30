package com.example.bbackcodingcon.feature_memo.presentation.add_edit_memo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbackcodingcon.feature_memo.domain.model.InvalidMemoException
import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.domain.use_case.MemoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditMemoViewModel @Inject constructor(
    private val memoUseCases: MemoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _memoTitle = mutableStateOf(
        MemoTextFieldState(
        hint = "제목을 입력하세요."
    )
    )
    val memoTitle: State<MemoTextFieldState> = _memoTitle

    private val _memoContent = mutableStateOf(
        MemoTextFieldState(
        hint = "메모 내용을 입력하세요."
    )
    )
    val memoContent: State<MemoTextFieldState> = _memoContent

    private val _memoColor = mutableStateOf<Int>(Memo.memoColors.random().toArgb())
    val memoColor: State<Int> = _memoColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentMemoId: Int ?= null

    private val _locked = mutableStateOf(false)
    val locked: State<Boolean> = _locked

    private val _memoPassword = mutableStateOf("")
    val memoPassword: State<String> = _memoPassword

    val passwordState = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>("memoId")?.let { memoId ->
            if(memoId != -1) {
                viewModelScope.launch {
                    memoUseCases.getMemo(memoId)?.also { memo ->
                        currentMemoId = memo.id
                        _memoTitle.value = memoTitle.value.copy(
                            text = memo.title,
                            isHintVisible = false
                        )
                        _memoContent.value = memoContent.value.copy(
                            text = memo.content,
                            isHintVisible = false
                        )
                        _memoColor.value = memo.color
                        _memoPassword.value = memo.password
                        _locked.value = memo.locked
                    }
                }
            }
        }
    }
    fun onEvent(event: AddEditMemoEvent) {
        when(event) {
            is AddEditMemoEvent.EnteredTitle -> {
                _memoTitle.value = _memoTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditMemoEvent.ChangeTitleFocus -> {
                _memoTitle.value = _memoTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            memoTitle.value.text.isBlank()
                )
            }
            is AddEditMemoEvent.EnteredContent -> {
                _memoContent.value = _memoContent.value.copy(
                    text = event.value
                )
            }
            is AddEditMemoEvent.ChangeContentFocus -> {
                _memoContent.value = memoContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _memoContent.value.text.isBlank()
                )
            }
            is AddEditMemoEvent.ChangeColor -> {
                _memoColor.value = event.color
            }
            is AddEditMemoEvent.ChangePassword -> {
                _memoPassword.value = event.value
            }
            is AddEditMemoEvent.ChangeLockState -> {
                _locked.value = event.value
            }
            is AddEditMemoEvent.SaveMemo ->  {
                viewModelScope.launch {
                    try {
                        memoUseCases.addMemo(
                            Memo(
                                title = memoTitle.value.text,
                                content = memoContent.value.text,
                                timestamp =  System.currentTimeMillis(),
                                color = memoColor.value,
                                id = currentMemoId,
                                password = memoPassword.value,
                                locked = locked.value,
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveMemo)
                    }
                    catch(e: InvalidMemoException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save memo."
                            )
                        )
                    }
                }
            }
        }
    }
    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveMemo: UiEvent()
    }
}