package com.example.bbackcodingcon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bbackcodingcon.feature_memo.presentation.add_edit_memo.AddEditMemoScreen
import com.example.bbackcodingcon.feature_memo.presentation.memos.MemosScreen
import com.example.bbackcodingcon.feature_memo.presentation.util.Screen
import com.example.bbackcodingcon.ui.theme.BBackCodingConTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BBackCodingConTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MemosScreen.route
                    ) {
                        composable(route = Screen.MemosScreen.route) {
                            MemosScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditMemoScreen.route +
                                    "?memoId={memoId}&memoColor={memoColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "memoId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "memoColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("memoColor") ?: -1
                            AddEditMemoScreen(
                                navController = navController,
                                memoColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
