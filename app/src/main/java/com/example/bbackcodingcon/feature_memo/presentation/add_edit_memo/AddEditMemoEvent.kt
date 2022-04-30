package com.example.bbackcodingcon.feature_memo.presentation.add_edit_memo

import androidx.compose.ui.focus.FocusState

sealed class AddEditMemoEvent {
    data class EnteredTitle(val value: String): AddEditMemoEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditMemoEvent()
    data class EnteredContent(val value: String): AddEditMemoEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditMemoEvent()
    data class ChangeColor(val color: Int): AddEditMemoEvent()
    data class ChangeLockState(val value: Boolean): AddEditMemoEvent()
    data class ChangePassword(val value: String): AddEditMemoEvent()
    object SaveMemo: AddEditMemoEvent()
}
