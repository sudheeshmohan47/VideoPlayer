package com.sample.videoplayer.foundation.appstate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    navController,
    coroutineScope
) {
    VideoPlayerAppState(navController)
}

class VideoPlayerAppState(val navController: NavHostController)
