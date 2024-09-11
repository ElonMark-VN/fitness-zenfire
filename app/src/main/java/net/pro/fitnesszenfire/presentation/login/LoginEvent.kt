package net.pro.fitnesszenfire.presentation.login

sealed class LoginEvent {
    object LoginWithGoogle : LoginEvent()
    object LoginWithFacebook : LoginEvent()
    object LoginWithAppleId : LoginEvent()
    data class ShowSnackbar(val message: String) : LoginEvent()
}
