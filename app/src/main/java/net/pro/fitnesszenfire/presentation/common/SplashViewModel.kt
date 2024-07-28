package net.pro.fitnesszenfire.presentation.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import net.pro.fitnesszenfire.domain.repository.OnBoardingRepository
import net.pro.fitnesszenfire.utils.Screen
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    repository: OnBoardingRepository
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Onboarding.route)
    val startDestination: State<String> = _startDestination

    init {

        val onBoardingState = runBlocking {
            repository.onBoardingState.first()
        }

        if (onBoardingState) {
            _startDestination.value = Screen.LoginScreen.route

        } else {
            _startDestination.value = Screen.Onboarding.route

        }


        _isLoading.value = false


    }
}