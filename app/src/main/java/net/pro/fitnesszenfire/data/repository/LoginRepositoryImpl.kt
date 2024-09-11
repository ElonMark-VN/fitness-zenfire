package net.pro.fitnesszenfire.data.repository


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import net.pro.fitnesszenfire.domain.repository.LoginRepository

import kotlinx.coroutines.tasks.await
import net.pro.fitnesszenfire.domain.model.Response
import javax.inject.Inject
import javax.inject.Named


class LoginRepositoryImpl @Inject constructor(
    private val oneTapClient: SignInClient,
    @Named("signInRequest") private val signInRequest: BeginSignInRequest,
    @Named("signUpRequest") private val signUpRequest: BeginSignInRequest,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : LoginRepository {

    override suspend fun oneTapSignIn(): Response<BeginSignInResult> {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Response.Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Response.Success(signUpResult)
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun signInWithGoogle(credential: SignInCredential): Response<FirebaseAuth> {
        return try {
            val googleCredential = GoogleAuthProvider.getCredential(credential.googleIdToken, null)
            auth.signInWithCredential(googleCredential).await()
            Response.Success(auth)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun saveUserToFirestore(
        userId: String,
        userData: Map<String, Any>
    ): Response<Void> {
        return try {
            firestore.collection("users").document(userId).set(userData).await()
            Response.Success(null)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}