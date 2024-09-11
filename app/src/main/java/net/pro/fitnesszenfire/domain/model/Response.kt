package net.pro.fitnesszenfire.domain.model

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<out T>(val data: T?) : Response<T>()
    data class Failure(val e: Exception) : Response<Nothing>()
}

typealias OneTapSignInResponse = Response<com.google.android.gms.auth.api.identity.BeginSignInResult>
typealias FirebaseSignInResponse = Response<com.google.firebase.auth.FirebaseAuth>