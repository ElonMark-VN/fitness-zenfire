package net.pro.fitnesszenfire.presentation.profile

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.ui.theme.accentOrange
import net.pro.fitnesszenfire.ui.theme.backgroundLight
import net.pro.fitnesszenfire.ui.theme.mainColor


@Composable
fun Profile(
        viewModel: ProfileViewModel = hiltViewModel(),
        navController: NavHostController,
) {
    val context = LocalContext.current as Activity

    // Status bar transparency (consider theming or app-wide configuration)
    context.window.statusBarColor = Color.Transparent.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()

    val user by viewModel.user.collectAsState()
    LaunchedEffect(Unit) {
//        viewModel.logUserData()
    }


    Column(
            modifier = Modifier
                .fillMaxSize() // Fill entire screen (adjust padding as needed)
                .padding(vertical = 30.dp, horizontal = 25.dp)
    ) {
        Text(
                text = "My Profile",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp) // Adjust spacing
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                    painter = rememberAsyncImagePainter(model = user?.avatar),
                    contentDescription = stringResource(id = R.string.display_picture),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(25.dp))

            Column {
                user?.userName?.let {
                    Text(
                            text = it,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(5.dp, 0.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Edit Profile",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .border(
                                    width = 1.5.dp,
                                    color = accentOrange,
                                    shape = RoundedCornerShape(20.dp)
                            )
                            .padding(35.dp, 12.dp)
                            .clickable {

                                /* Handle edit profile click */
                            })
            }
        }
    }
}