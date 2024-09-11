package net.pro.fitnesszenfire.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.pro.fitnesszenfire.data.data_source.DataStoreManager
import net.pro.fitnesszenfire.domain.model.Response
import net.pro.fitnesszenfire.domain.repository.LoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    val oneTapClient: SignInClient,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var oneTapSignInResponse = mutableStateOf<Response<BeginSignInResult>>(Response.Success(null))
    var googleSignInResponse = mutableStateOf<Response<FirebaseAuth>>(Response.Success(null))
    var saveUserToFirestoreResponse = mutableStateOf<Response<Void>>(Response.Success(null))
    var isUserLoggedIn = mutableStateOf(false)
    var isCheckCompleted = mutableStateOf(false)

    init {
        checkUserLoggedIn()
    }

    private fun checkUserLoggedIn() {
        viewModelScope.launch {
            val userId = dataStoreManager.userIdFlow.first()
            isUserLoggedIn.value = userId != null
            isCheckCompleted.value = true
        }
    }

    fun oneTapSignIn() = viewModelScope.launch(Dispatchers.IO) {
        oneTapSignInResponse.value = Response.Loading
        oneTapSignInResponse.value = repository.oneTapSignIn()
    }

    fun signInWithGoogle(credential: SignInCredential) = viewModelScope.launch(Dispatchers.IO) {
        googleSignInResponse.value = Response.Loading
        val response = repository.signInWithGoogle(credential)
        googleSignInResponse.value = response

        if (response is Response.Success) {
            val user = response.data?.currentUser
            user?.let {
                val userData = mapOf<String, Any>(
                    "uid" to it.uid,
                    "name" to (it.displayName ?: ""),
                    "email" to (it.email ?: ""),
                    "photoUrl" to (it.photoUrl?.toString() ?: "")
                )
                saveUserToFirestore(it.uid, userData)
                dataStoreManager.saveUser(it.uid, it.displayName ?: "", it.email ?: "", it.photoUrl?.toString() ?: "")
            }
        }
    }

    private fun saveUserToFirestore(userId: String, userData: Map<String, Any>) = viewModelScope.launch(Dispatchers.IO) {
        saveUserToFirestoreResponse.value = Response.Loading
        saveUserToFirestoreResponse.value = repository.saveUserToFirestore(userId, userData)
    }
}
