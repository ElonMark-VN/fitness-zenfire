package net.pro.fitnesszenfire.presentation.login

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.domain.model.Response
import net.pro.fitnesszenfire.ui.theme.mainColor
import net.pro.fitnesszenfire.utils.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credentials = loginViewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                loginViewModel.signInWithGoogle(credentials)
            } catch (e: ApiException) {
                Log.e("LoginScreen:Launcher", "Login One-tap $e")
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            Log.e("LoginScreen:Launcher", "OneTapClient Canceled")
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    if (loginViewModel.isCheckCompleted.value && loginViewModel.isUserLoggedIn.value) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    } else {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fitness4),
                    contentDescription = stringResource(id = R.string.login),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 30.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ButtonSignin(
                        onClick = { loginViewModel.oneTapSignIn() },
                        text = stringResource(id = R.string.login_with_google),
                        iconRes = R.drawable.goog_icon,
                        textColor = Color.White,
                        bgColor = Color.Red
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ButtonSignin(
                        onClick = { /*TODO*/ },
                        text = stringResource(id = R.string.login_with_appleId),
                        iconRes = R.drawable.apple,
                        textColor = Color.Black,
                        bgColor = Color.White
                    )
                }
            }

            when (val oneTapSignInResponse = loginViewModel.oneTapSignInResponse.value) {
                is Response.Loading -> {
                    Log.i("Login:OneTap", "Loading")
                    CircularProgressIndicator()
                }
                is Response.Success -> oneTapSignInResponse.data?.let { signInResult ->
                    LaunchedEffect(signInResult) {
                        launch(signInResult)
                    }
                }
                is Response.Failure -> {
                    Log.e("Login:OneTap", "${oneTapSignInResponse.e}")
                }
            }

            when (val signInWithGoogleResponse = loginViewModel.googleSignInResponse.value) {
                is Response.Loading -> {
                    Log.i("Login:GoogleSignIn", "Loading")
                    CircularProgressIndicator()
                }
                is Response.Success -> signInWithGoogleResponse.data?.let { authResult ->
                    Log.i("Login:GoogleSignIn", "Success: $authResult")
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
                is Response.Failure -> {
                    Log.e("Login:GoogleSignIn", "${signInWithGoogleResponse.e}")
                }
            }
        }
    }
}



@Composable
fun ButtonSignin(
    onClick: () -> Unit,
    text: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    textColor: Color,
    bgColor: Color = mainColor
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp)
            .clip(RoundedCornerShape(50.dp))
            .height(48.dp),

        colors = ButtonDefaults.buttonColors(
            backgroundColor = bgColor,
            contentColor = textColor
        )
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }

//@Composable
//fun FacebookButton(
//    onAuthComplete: () -> Unit,
//    onAuthError: (Exception) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    val scope = rememberCoroutineScope()
//    val loginManager = LoginManager.getInstance()
//    val callbackManager = remember { CallbackManager.Factory.create() }
//    val launcher = rememberLauncherForActivityResult(
//        loginManager.createLogInActivityResultContract(
//            callbackManager, /* Specify login behavior */
//
//
//        )
//    ) {
//        // nothing to do. handled in FacebookCallback
//    }
//
//    DisposableEffect(Unit) {
//        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            override fun onCancel() {
//                // do nothing
//            }
//
//            override fun onError(error: FacebookException) {
//                onAuthError(error)
//            }
//
//            override fun onSuccess(result: LoginResult) {
//
//
//                scope.launch {
//                    val token = result.accessToken.token
//                    val credential =
//                        FacebookAuthProvider.getCredential(token)
//                    val authResult = Firebase.auth.signInWithCredential(credential).await()
//                    if (authResult.user != null) {
//                        onAuthComplete()
//                    } else {
//                        onAuthError(IllegalStateException("Unable to sign in with Facebook"))
//                    }
//                }
//
//
//                // user signed in successfully
//                // TODO Forward to Firebase Auth
//                // check next step in composables.com/blog/firebase-auth-facebook
//            }
//        })
//
//        onDispose {
//            loginManager.unregisterCallback(callbackManager)
//        }
//    }
//
//    Button(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(25.dp, 0.dp)
//            .clip(RoundedCornerShape(50.dp))
//            .height(48.dp),
//
//        colors = ButtonDefaults.buttonColors(
//            backgroundColor = fbColor,
//            contentColor = Color.White
//        ),
//        onClick = {
//            launcher.launch(listOf("email", "public_profile"))
//        }) {
//        Icon(
//            painter = painterResource(id = R.drawable.fb),
//            contentDescription = null,
//            modifier = Modifier.size(24.dp)
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text("Login with Facebook")
//
//    }
//}



}
