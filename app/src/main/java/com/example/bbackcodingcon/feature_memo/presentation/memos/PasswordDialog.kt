package com.example.bbackcodingcon.feature_memo.presentation.memos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.bbackcodingcon.feature_memo.presentation.add_edit_memo.AddEditMemoEvent
import com.example.bbackcodingcon.feature_memo.presentation.add_edit_memo.AddEditMemoViewModel


@Composable
fun PasswordSaveDialog(viewModel: AddEditMemoViewModel, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = {onDismiss()}) {
        Column(modifier = Modifier.background(Color.White).padding(24.dp)) {
            Text("비밀번호를 입력하세요.")

            TextField(
                value = viewModel.memoPassword.value,
                onValueChange = {
                    viewModel.onEvent(AddEditMemoEvent.ChangePassword(it))
                },
                visualTransformation = PasswordVisualTransformation(),
                maxLines = 1,
            )

            Button(onClick = {
                viewModel.onEvent(AddEditMemoEvent.SaveMemo)
                viewModel.passwordState.value = false
            }) {
                Text("저장")
            }
        }
    }
}

@Composable
fun PasswordConfirmDialog(memokPassword: String, onSuccess: ()->Unit, onDismiss: ()-> Unit, onFailure: ()->Unit) {
    val tempPassword: MutableState<String> = remember { mutableStateOf("") }

    Dialog(onDismissRequest = {onDismiss()}) {
        Column(modifier = Modifier.background(Color.White).padding(24.dp)) {
            Text("비밀번호를 입력하세요.")
            TextField(
                value = tempPassword.value,
                onValueChange = {
                    tempPassword.value = it
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation(),
                maxLines = 1,
            )

            Button(onClick = {
                if(memokPassword == tempPassword.value)
                    onSuccess()
                else onFailure()
            }) {
                Text("확인")
            }
        }
    }
}