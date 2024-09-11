package net.pro.fitnesszenfire.presentation.profile


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.pro.fitnesszenfire.data.data_source.DataStoreManager
import net.pro.fitnesszenfire.domain.model.User
import net.pro.fitnesszenfire.domain.repository.LoginRepository
import net.pro.fitnesszenfire.domain.repository.ProfileRepository
import net.pro.fitnesszenfire.presentation.login.UiEvent
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val loginRepository: LoginRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _userAvatarUrl = mutableStateOf("")
    val userAvatarUrl: State<String> = _userAvatarUrl

    fun logUserData() {
        viewModelScope.launch {
            try {
                val userId = dataStoreManager.userIdFlow.first()
                val userName = dataStoreManager.userNameFlow.first()
                val userEmail = dataStoreManager.userEmailFlow.first()
                val userPhotoUrl = dataStoreManager.userPhotoUrlFlow.first()

                // Log user data
                println("User ID: $userId - $userName - $userEmail - $userPhotoUrl")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    init {
        viewModelScope.launch {
            combine(
                dataStoreManager.userIdFlow,
                dataStoreManager.userNameFlow,
                dataStoreManager.userEmailFlow,
                dataStoreManager.userPhotoUrlFlow
            ) { userId, userName, userEmail, userPhotoUrl ->
                _user.value = User(userId, userName, userEmail, userPhotoUrl)
            }.catch {
                // Xử lý lỗi
            }.collect()
        }
    }
}
