package com.example.bbackcodingcon.feature_memo.presentation.add_edit_memo

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bbackcodingcon.feature_memo.domain.model.Memo
import com.example.bbackcodingcon.feature_memo.presentation.add_edit_memo.components.TransparentHintTextField
import com.example.bbackcodingcon.feature_memo.presentation.memos.PasswordSaveDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditMemoScreen(
    navController: NavController,
    memoColor: Int,
    viewModel: AddEditMemoViewModel = hiltViewModel()
) {
    val titleState = viewModel.memoTitle.value
    val contentState = viewModel.memoContent.value

    val scaffoldState = rememberScaffoldState()

    val memoBackgroundAnimatable = remember {
        Animatable(
            Color(if(memoColor != -1) memoColor else viewModel.memoColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    val isEditMode = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {  event ->
            when(event) {
                is AddEditMemoViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditMemoViewModel.UiEvent.SaveMemo -> {
                    navController.navigateUp()
                }
            }
        }
    }

    if(viewModel.passwordState.value)
        PasswordSaveDialog(viewModel, {viewModel.passwordState.value = false})

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if(viewModel.locked.value) {
                        isEditMode.value = false
                        viewModel.passwordState.value = true
                    }
                    else {
                        viewModel.onEvent(AddEditMemoEvent.SaveMemo)
                    }
                }, backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save memo")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(memoBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Memo.memoColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.memoColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    memoBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditMemoEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("현재 글자 수 : ${contentState.text.length}")
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = if(viewModel.locked.value) Color.Blue else Color.Gray,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(AddEditMemoEvent.ChangeLockState(!viewModel.locked.value))
                    }
                )
            }
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditMemoEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMemoEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditMemoEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditMemoEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}