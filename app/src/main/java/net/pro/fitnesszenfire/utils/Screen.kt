package net.pro.fitnesszenfire.utils

sealed class Screen(val route: String) {
    object Onboarding : Screen(route = "onboarding_screen")
    object LoginScreen : Screen("login_screen")
    object HomeScreen : Screen(route = "home_screen")
    object Feed : Screen(route = "feed_screen")
    object NewPost : Screen(route = "new_post_screen")

    object Profile : Screen(route = "profile_screen")
    object EditProfile : Screen(route = "edit_screen")
    object EditAvatarProfile : Screen(route = "edit_avatar_screen")
    object StartTracking :  Screen(route = "start_tracking_screen")

    object SelectActivity : Screen(route = "select_activity_screen")

    object Challenge : Screen(route = "challenge_screen")



    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}