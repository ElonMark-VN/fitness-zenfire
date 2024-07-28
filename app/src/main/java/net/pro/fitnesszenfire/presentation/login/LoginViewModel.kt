package net.pro.fitnesszenfire.presentation.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import net.pro.fitnesszenfire.domain.repository.LoginRepository
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepo: LoginRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    fun onFacebookLoginSuccess() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowSnackbar("Login Successful"))
        }
    }

    fun onFacebookLoginError(error: Exception) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowSnackbar("Authentication failed: ${error.message}"))
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        LoginManager.getInstance().logOut()
    }

    fun checkCurrentUser() {
        val currentUser = firebaseAuth.currentUser
        // Update UI based on current user
    }
}
