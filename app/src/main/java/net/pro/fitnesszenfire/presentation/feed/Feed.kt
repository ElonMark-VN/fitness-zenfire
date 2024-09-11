package net.pro.fitnesszenfire.presentation.feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.presentation.feed.component.CardPost
import net.pro.fitnesszenfire.ui.theme.accentOrange
import net.pro.fitnesszenfire.ui.theme.backgroundLight
import net.pro.fitnesszenfire.ui.theme.linearWhite
import net.pro.fitnesszenfire.utils.Screen


@Composable
fun Feed(
        scrollState: LazyListState,
        navController: NavHostController,
        viewModel: FeedViewModel = hiltViewModel()
) {
    val posts = viewModel.posts  // Lấy danh sách bài đăng từ ViewModel

    var count = 0;
    count++
    Log.i("loadCheck","check${count}")
    LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundLight),
            state = scrollState
    ) {

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                header(title = "Feed", navController)
            }
        }

        items(posts) { post ->  // Vòng lặp để tạo từng `CardPost`
            CardPost(post = post)
        }
    }
}



@Composable
fun header(title: String, navController: NavHostController) {
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
                text = "$title",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
        )

        androidx.compose.material3.Icon(
                painter = painterResource(id = R.drawable.baseline_add_24),
                tint = accentOrange,
                contentDescription = "add post",
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(linearWhite)
                    .padding(5.dp)
                    .clickable {
                        navController.navigate(Screen.NewPost.route) {
                            popUpTo(Screen.NewPost.route) {
                                saveState = true
                            }

                        }
                    },
        )
    }
}