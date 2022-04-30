package com.example.bbackcodingcon.feature_memo.presentation.memos

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bbackcodingcon.feature_memo.presentation.memos.components.MemoItem
import com.example.bbackcodingcon.feature_memo.presentation.memos.components.OrderSection
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@Composable
fun MemosScreen(
    navController: NavController,
    viewModel: MemosViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.searchText.value) {
        viewModel.onEvent(viewModel.searchText.value, MemosEvent.Order(viewModel.state.value.memoOrder))
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(com.example.bbackcodingcon.feature_memo.presentation.util.Screen.AddEditMemoScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add memo")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "빡코딩콘 메모장",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(viewModel.searchText.value, MemosEvent.ToggleOrderSection)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically() ,
                exit =  fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    memoOrder = state.memoOrder,
                    onOrderChange = {
                        viewModel.onEvent(viewModel.searchText.value, MemosEvent.Order(it))
                    },
                    viewModel = viewModel
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.memos) { memo ->
                    MemoItem(
                        memo = memo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        onDeleteClick = {
                            viewModel.onEvent(viewModel.searchText.value, MemosEvent.DeleteMemo(memo))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "메모가 삭제되었습니다.",
                                    actionLabel = "실행 취소"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(viewModel.searchText.value, MemosEvent.RestoreMemo)
                                }
                            }
                        },
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
}