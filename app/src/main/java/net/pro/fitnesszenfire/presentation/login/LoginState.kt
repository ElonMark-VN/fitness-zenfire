package net.pro.fitnesszenfire.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false
)
