package net.pro.fitnesszenfire.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import net.pro.fitnesszenfire.domain.model.Response

interface LoginRepository {
     suspend fun oneTapSignIn(): Response<BeginSignInResult>
     suspend fun signInWithGoogle(credential: SignInCredential): Response<FirebaseAuth>
     suspend fun saveUserToFirestore(userId: String, userData: Map<String, Any>): Response<Void>
}