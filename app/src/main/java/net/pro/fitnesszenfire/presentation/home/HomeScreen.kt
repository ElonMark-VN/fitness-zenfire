package net.pro.fitnesszenfire.presentation.home


import android.app.Activity
import android.graphics.Shader

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.presentation.components.ActivityCard
import net.pro.fitnesszenfire.ui.theme.accentOrange
import net.pro.fitnesszenfire.ui.theme.backgroundLight
import net.pro.fitnesszenfire.ui.theme.linearWhite

@Composable
fun Home(
    scrollState: LazyListState,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    context.window.statusBarColor = backgroundLight.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()

    val homeScreenState by viewModel.homeScreenState

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundLight)
            .padding(bottom = 60.dp),
        state = scrollState
    ) {
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopSection()
                titleRow(title = "Recent Activity")
            }
        }

        items(homeScreenState.restaurantList.take(4)) { restaurant ->
            ActivityCard(
                restaurant = restaurant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        // Handle click event
                        // navController.navigate(Screen.RestaurantDetails.route)
                    }
            )
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                titleRow(title = "Friends Activity")
            }
        }

        items(homeScreenState.restaurantList.take(4)) { restaurant ->
            ActivityCard(
                restaurant = restaurant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        // Handle click event
                        // navController.navigate(Screen.RestaurantDetails.route)
                    }
            )
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                titleRow(title = "Challenge")
            }
        }

        items(homeScreenState.restaurantList.take(4)) { restaurant ->
            ActivityCard(
                restaurant = restaurant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        // Handle click event
                        // navController.navigate(Screen.RestaurantDetails.route)
                    }
            )
        }
    }
}

@Composable
fun titleRow(title: String) {
    Column(modifier = Modifier.padding(25.dp, 15.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "$title",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "See all",
                color = Color.Black,
                fontStyle = FontStyle.Normal,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
fun TopSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {


        Column(modifier = Modifier.padding(15.dp)) {
            Row {
                Text(
                    text = "Hello",
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Le Phu Quy",
                    color = Color.Black,
                    fontStyle = FontStyle.Normal,
                    fontSize = 16.sp
                )
            }
            Row {
                Text(
                    text = "Good Morning",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(0.dp, 8.dp)

                )
            }
        }

        Row (modifier = Modifier.padding(20.dp)){
            androidx.compose.material3.Icon(
                painter = painterResource(id = R.drawable.message),
                tint = accentOrange,
                contentDescription = "message",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(linearWhite)
                    .padding(5.dp)
                    .clickable { }

            )

            Spacer(modifier = Modifier.width(10.dp))
            
            androidx.compose.material3.Icon(
                painter = painterResource(id = R.drawable.baseline_notifications_active_24),
                tint = accentOrange,
                contentDescription = "message",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(linearWhite)
                    .padding(5.dp)
                    .clickable { }

            )


        }


    }

}

