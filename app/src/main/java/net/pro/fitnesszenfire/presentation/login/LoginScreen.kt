package net.pro.fitnesszenfire.presentation.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import net.pro.fitnesszenfire.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import net.pro.fitnesszenfire.ui.theme.backgroundLight
import net.pro.fitnesszenfire.ui.theme.fbColor
import net.pro.fitnesszenfire.ui.theme.mainColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundLight
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
                onClick = { /*TODO*/ },
                text = stringResource(id = R.string.login_with_google),
                iconRes = R.drawable.goog_icon,
                textColor = Color.White,
                bgColor = Color.Red
            )

            Spacer(modifier = Modifier.height(10.dp))

            FacebookButton(
                onAuthComplete = { viewModel.onFacebookLoginSuccess() },
                onAuthError = { error ->
                    viewModel.onFacebookLoginError(error)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .height(48.dp)
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
}

@Composable
fun FacebookButton(
    onAuthComplete: () -> Unit,
    onAuthError: (Exception) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(
            callbackManager, /* Specify login behavior */


        )
    ) {
        // nothing to do. handled in FacebookCallback
    }

    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
            }

            override fun onError(error: FacebookException) {
                onAuthError(error)
            }

            override fun onSuccess(result: LoginResult) {


                scope.launch {
                    val token = result.accessToken.token
                    val credential =
                        FacebookAuthProvider.getCredential("EAAFIEVroX3oBOzwJiMxWtXMbNowaWqIi6Ik79ECI2mZAFZAY6O1JyjNcTwde5wQR8ClIZB82g3zUREbAUIyfjQmSniZBr0beX9u4ODmglq6KbZBAsc29Uw349Nu7eZBFxF1m7OFa6cThiDcKdvWZAgZCTNvk3n1B8jnQZBacYFaZAeL9c1pcr4Bbup3R51foPtiHIVdI8hlhR04vMZD")
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    if (authResult.user != null) {
                        onAuthComplete()
                    } else {
                        onAuthError(IllegalStateException("Unable to sign in with Facebook"))
                    }
                }


                // user signed in successfully
                // TODO Forward to Firebase Auth
                // check next step in composables.com/blog/firebase-auth-facebook
            }
        })

        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }

    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp, 0.dp)
            .clip(RoundedCornerShape(50.dp))
            .height(48.dp),

        colors = ButtonDefaults.buttonColors(
            backgroundColor = fbColor,
            contentColor = Color.White
        ),
        onClick = {
            launcher.launch(listOf("email", "public_profile"))
        }) {
        Icon(
            painter = painterResource(id = R.drawable.fb),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Login with Facebook")

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
}
