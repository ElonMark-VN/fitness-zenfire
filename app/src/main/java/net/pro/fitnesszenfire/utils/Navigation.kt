package net.pro.fitnesszenfire.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.pro.fitnesszenfire.presentation.home.Home
import net.pro.fitnesszenfire.presentation.login.LoginScreen
import net.pro.fitnesszenfire.presentation.onboarding.OnBoarding
import net.pro.fitnesszenfire.presentation.profile.Profile
import com.google.accompanist.pager.ExperimentalPagerApi
import net.pro.fitnesszenfire.presentation.challenge.ChallengeScreen
import net.pro.fitnesszenfire.presentation.feed.Feed
import net.pro.fitnesszenfire.presentation.feed.NewPost
import net.pro.fitnesszenfire.presentation.profile.EditAvatar
import net.pro.fitnesszenfire.presentation.profile.EditProfile
import net.pro.fitnesszenfire.presentation.selectActivity.SelectActivity
import net.pro.fitnesszenfire.presentation.startTracking.StartTracking
import net.pro.fitnesszenfire.ui.theme.accentOrange
import net.pro.fitnesszenfire.ui.theme.backgroundLight
import net.pro.fitnesszenfire.ui.theme.accentOrange
import net.pro.fitnesszenfire.ui.theme.linearWhite
import net.pro.fitnesszenfire.ui.theme.mainColor

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    scrollState: LazyListState,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.Onboarding.route,
        ) {
            OnBoarding(navController = navController)
        }
        composable(
            route = Screen.StartTracking.route,
        ) {
            StartTracking(navController = navController)
        }
        composable(
            route = Screen.Challenge.route,
        ) {
            ChallengeScreen(navController = navController)
        }
        composable(
            route = Screen.LoginScreen.route,
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.HomeScreen.route
        ) {
            Home(navController = navController, scrollState = scrollState)
        }
        composable(
            route = Screen.Feed.route
        ) {
            Feed(navController = navController, scrollState = scrollState)
        }
        composable(
            route = Screen.NewPost.route
        ) {
            NewPost(navController = navController, scrollState = scrollState)
        }
        composable(
            route = Screen.SelectActivity.route
        ) {
            SelectActivity(navController = navController, scrollState = scrollState)
        }
        composable(
            route = Screen.Profile.route
        ) {
            Profile(navController = navController)
        }
        composable(
            route = Screen.EditProfile.route,
        ) {
            EditProfile(navController = navController)
        }
        composable(
            route = Screen.EditAvatarProfile.route,
        ) {
            EditAvatar(navController = navController)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetupNavigation(
    startDestination: String,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scrollState = rememberLazyListState()
    val state by remember { derivedStateOf { scrollState.firstVisibleItemIndex == 0 } }

    Scaffold(
        bottomBar = {
            if ((currentRoute == Screen.HomeScreen.route
                        || currentRoute == Screen.Feed.route
                        || currentRoute == Screen.Challenge.route
                        || currentRoute == Screen.Profile.route)
//                && state
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        BottomBarWithFAB(navController = navController)
                    }
                }
            }
        }
    ) {
        NavigationGraph(
            navController = navController,
            scrollState = scrollState,
            startDestination = startDestination,
        )
    }
}

@Composable
fun BottomBarWithFAB(navController: NavHostController) {
    Box {
        // Bottom Navigation Bar
        BottomBar(navController)

        // Floating Action Button
        FloatingActionButton(
            backgroundColor = mainColor,
            onClick = {
                navController.navigate(Screen.Challenge.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -32.dp) // Adjust the offset as needed,


        ) {
            Icon(
                painter = painterResource(net.pro.fitnesszenfire.R.drawable.play),
                contentDescription = stringResource(net.pro.fitnesszenfire.R.string.start_tracking),
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = backgroundLight
    ) {
        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.fitnesszenfire.R.string.home))
            },
            icon = {
                Icon(
                    painter = painterResource(net.pro.fitnesszenfire.R.drawable.home),
                    contentDescription = stringResource(net.pro.fitnesszenfire.R.string.home),
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )
            },
            selectedContentColor = accentOrange,
            unselectedContentColor = linearWhite,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.HomeScreen.route,
            onClick = {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.fitnesszenfire.R.string.feed))
            },
            icon = {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(net.pro.fitnesszenfire.R.drawable.history),
                    contentDescription = stringResource(net.pro.fitnesszenfire.R.string.feed),
                )
            },
            selectedContentColor = accentOrange,
            unselectedContentColor = linearWhite,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.Feed.route,
            onClick = {
                navController.navigate(Screen.Feed.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.fitnesszenfire.R.string.challenge))
            },
            icon = {
                Icon(
                    painter = painterResource(net.pro.fitnesszenfire.R.drawable.challenge),
                    contentDescription = stringResource(net.pro.fitnesszenfire.R.string.challenge),
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )
            },
            selectedContentColor = accentOrange,
            unselectedContentColor = linearWhite,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.Challenge.route,
            onClick = {
                navController.navigate(Screen.Challenge.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        BottomNavigationItem(
            label = {
                Text(text = stringResource(net.pro.fitnesszenfire.R.string.profile))
            },
            icon = {
                Icon(
                    painter = painterResource(net.pro.fitnesszenfire.R.drawable.user),
                    contentDescription = stringResource(net.pro.fitnesszenfire.R.string.profile),
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )
            },
            selectedContentColor = accentOrange,
            unselectedContentColor = linearWhite,
            alwaysShowLabel = true,
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

