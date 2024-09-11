package net.pro.fitnesszenfire.presentation.common

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.pro.fitnesszenfire.data.data_source.DataStoreManager
import net.pro.fitnesszenfire.domain.repository.OnBoardingRepository
import net.pro.fitnesszenfire.utils.Screen
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: OnBoardingRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.HomeScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        determineStartDestination()
    }

    private fun determineStartDestination() {
        viewModelScope.launch {
            // Kiểm tra trạng thái đăng nhập trước
            val userId = dataStoreManager.userIdFlow.first()
            val isUserLoggedIn = userId != null


            _startDestination.value = if (isUserLoggedIn) {
                Screen.HomeScreen.route
            } else {
                // Nếu chưa đăng nhập, kiểm tra trạng thái onboarding
                val isOnboardingCompleted = repository.onBoardingState.first()
                if (!isOnboardingCompleted) {
                    Screen.Onboarding.route
                } else {
                    Screen.LoginScreen.route
                }
            }

            _isLoading.value = false
        }
    }
}
