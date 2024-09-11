package net.pro.fitnesszenfire.presentation.feed.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.domain.model.Post
import net.pro.fitnesszenfire.presentation.feed.FeedViewModel
import net.pro.fitnesszenfire.ui.theme.usernameColor

@Composable
fun CardPost(post: Post, feedViewModel: FeedViewModel = hiltViewModel()) {
    var authorName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Lấy thông tin người dùng từ authorId
    LaunchedEffect(post.authorId) {
        feedViewModel.getUserById(post.authorId,
                onSuccess = { user ->
                    authorName = user.userName.toString() // Giả sử user có trường "name"
                    isLoading = false
                },
                onError = {
                    authorName = "Unknown"
                    isLoading = false
                }
        )
    }

    if (isLoading) {
        // Hiển thị một placeholder hoặc progress bar trong lúc tải dữ liệu
        CircularProgressIndicator()
    } else {
        Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .background(Color.White)
                    .clip(RoundedCornerShape(10.dp))
        ) {
            // Hiển thị thông tin người đăng bài
            Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = R.drawable.avatar),
                            contentDescription = stringResource(id = R.string.display_picture)
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Column {
                        Text(
                                text = authorName,  // Hiển thị tên người đăng từ `authorName`
                                color = usernameColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                        )
                        Text(
                                text = post.location,  // Hiển thị vị trí hoặc thông tin khác
                                color = Color.Gray,
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 14.sp
                        )
                    }
                }

                Icon(
                        painter = painterResource(id = R.drawable.codicon),
                        contentDescription = "codicon",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 10.dp)
                )
            }

            // Hiển thị ảnh bài đăng (hoặc placeholder nếu chưa có ảnh)
            Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
            ) {
                AsyncImage(
                        model = post.imageUrl.firstOrNull()?.url ?: "placeholder_url",  // Hiển thị ảnh từ post
                        contentDescription = stringResource(id = R.string.post_image),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(10.dp))
                )
            }

            // Các nút like, comment
            Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                            painter = painterResource(id = R.drawable.like),
                            contentDescription = "like",
                            modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                            painter = painterResource(id = R.drawable.comment),
                            contentDescription = "comment",
                            modifier = Modifier.size(24.dp)
                    )
                }

                Row {
                    Icon(
                            painter = painterResource(id = R.drawable.save),
                            contentDescription = "save",
                            modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
