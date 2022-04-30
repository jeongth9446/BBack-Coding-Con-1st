package com.example.bbackcodingcon.feature_memo.presentation.memos.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.presentation.memos.PasswordConfirmDialog
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun MemoItem(
    memo: Memo,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    onDeleteClick: () -> Unit,
    navController: NavController,
    scaffoldState: ScaffoldState
){
    val passwordDialogVisible = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    if(passwordDialogVisible.value) {
        PasswordConfirmDialog(memo.password,
            onSuccess = {
                navController.navigate(
                    com.example.bbackcodingcon.feature_memo.presentation.util.Screen.AddEditMemoScreen.route
                            + "?memoId=${memo.id}&memoColor=${memo.color}"
                )
            },
            onDismiss = {
                focusManager.clearFocus()
                passwordDialogVisible.value = false
            },
            onFailure = {
                focusManager.clearFocus()
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "비밀번호가 틀렸습니다."
                    )
                }
            }
        )
    }
    Surface(
        shape = RoundedCornerShape(cornerRadius),
        color = Color(memo.color),
        modifier = Modifier.clickable {
            if(memo.locked) {
                passwordDialogVisible.value = true
            } else {
                navController.navigate(
                    com.example.bbackcodingcon.feature_memo.presentation.util.Screen.AddEditMemoScreen.route
                            + "?memoId=${memo.id}&memoColor=${memo.color}"
                )
            }
        }
    ) {
        Box() {
            Text(
                text = Date(memo.timestamp).toString(),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 4.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(end = 32.dp)
            ) {
                Text(
                    text = if(memo.locked) "비밀 메모입니다." else  memo.title,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =  if(memo.locked) "" else memo.content,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete memo"
                )
            }
        }
    }
}