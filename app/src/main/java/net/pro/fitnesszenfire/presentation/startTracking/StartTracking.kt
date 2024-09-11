package net.pro.fitnesszenfire.presentation.startTracking

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import net.pro.fitnesszenfire.presentation.selectActivity.SelectActivityViewModel
import net.pro.fitnesszenfire.ui.theme.backgroundLight


@Composable
fun StartTracking (
    navController: NavHostController,
    viewModel: SelectActivityViewModel = hiltViewModel()
) {



    val context = LocalContext.current as Activity
    context.window.statusBarColor = backgroundLight.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()
}