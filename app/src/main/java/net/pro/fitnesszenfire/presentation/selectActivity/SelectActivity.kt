package net.pro.fitnesszenfire.presentation.selectActivity

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.data.data_source.optionActivity
import net.pro.fitnesszenfire.presentation.feed.header
import net.pro.fitnesszenfire.ui.theme.backgroundLight
import net.pro.fitnesszenfire.ui.theme.isSelectedColor
import net.pro.fitnesszenfire.ui.theme.mainColor
import kotlin.math.round

@Composable
fun SelectActivity(
    scrollState: LazyListState,
    navController: NavHostController,
    viewModel: SelectActivityViewModel = hiltViewModel()
) {



    val context = LocalContext.current as Activity
    context.window.statusBarColor = backgroundLight.toArgb()
    context.window.navigationBarColor = backgroundLight.toArgb()


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundLight),
        state = scrollState
    ) {



        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(35.dp, 20.dp)) {
                Row {
                    Text (
                        text = "Select your favorite sport  activity ",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                            .fillMaxWidth()

                    )
                }
            }
        }

        items(optionActivity) { activity ->
            ActivityItem(activity)
        }

        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(35.dp, 20.dp)) {

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp, 0.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .height(48.dp),

                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = mainColor,
                        contentColor = mainColor
                    )
                ) {

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Let's start")
                }
            }
        }


    }


}

@Composable
fun ActivityItem(activity: net.pro.fitnesszenfire.domain.model.Activity) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { isChecked = !isChecked },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(end = 16.dp)
        )

        Image(
            painter = painterResource(id = activity.image),
            contentDescription = activity.name,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = activity.name,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}


