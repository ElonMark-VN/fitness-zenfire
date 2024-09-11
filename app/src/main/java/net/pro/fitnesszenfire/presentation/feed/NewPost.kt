package net.pro.fitnesszenfire.presentation.feed

import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.presentation.components.FullWidthImage
import net.pro.fitnesszenfire.presentation.components.IconWithDynamicState
import net.pro.fitnesszenfire.presentation.components.OptionResource
import net.pro.fitnesszenfire.presentation.components.VideoPlayer
import net.pro.fitnesszenfire.ui.theme.accentOrange
import net.pro.fitnesszenfire.ui.theme.accentOrangeAlpha
import net.pro.fitnesszenfire.ui.theme.accentOrangeAlpha05
import java.util.Locale

@Composable
fun NewPost(
        scrollState: LazyListState,
        navController: NavHostController,
        feedViewModel: FeedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val caption = remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imageDirectory = LocalContext.current.filesDir
    var isLoading by remember { mutableStateOf(false) }  // State to track loading status

    // Lấy `authorId`, ví dụ từ Firebase Auth
    val currentUser = FirebaseAuth.getInstance().currentUser
    val authorId = currentUser?.uid ?: "Unknown"


    val postTime = System.currentTimeMillis().toString()      // lấy postTimr

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var location by remember { mutableStateOf<String>("") }

    // Function to get location
    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                    context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
                loc?.let {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1)
                    val address: String = addresses?.get(0)?.getAddressLine(0)
                                          ?: ""   // Lấy địa chỉ đầy đủ //
                    //               val city: String = addresses?.get(0)?.locality ?: ""          // Lấy thành phố
                    //                    val district: String =
                    //                        addresses?.get(0)?.subLocality ?: ""                      // Lấy quận/huyện
                    //                    val ward: String =
                    //                        addresses?.get(0)?.subThoroughfare ?: ""                  // Lấy phường/xã

                    location = "${address}"


                }
            }
        }
    }

    // Call getLocation when this composable is first launched
    LaunchedEffect(Unit) {
        getLocation()
    }


    Box(Modifier.fillMaxSize()) {

        Column(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
        ) {
            Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Discard", modifier = Modifier.clickable {
                    navController.popBackStack()
                })
                Text(
                        text = "CREATE",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                )

                Text(text = "Publish",
                        color = Color.White,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = accentOrange)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                selectedImageUri?.let { uri ->  // Kiểm tra nếu có ảnh/video được chọn
                                    feedViewModel.uploadPost(
                                            uri = uri,
                                            caption = caption.value,
                                            navController = navController,
                                            authorId = authorId,   // Truyền authorId
                                            location = location,   // Truyền location
                                            postTime = postTime    // Truyền postTime
                                    )
                                }
                                ?: run { // Hiển thị thông báo lỗi hoặc hành động nếu người dùng chưa chọn ảnh/video
                                    Toast
                                        .makeText(
                                                context,
                                                "Please select an image or video",
                                                Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            })
            }

            LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(20.dp, 10.dp)
                        .safeContentPadding(), state = scrollState
            ) {
                item {
                    Column(Modifier.fillMaxWidth()) {
                        Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Image(
                                        painter = painterResource(id = R.drawable.avatar),
                                        contentDescription = "avatar",
                                        modifier = Modifier.size(35.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 10.dp)
                                        .fillMaxWidth()
                            ) {
                                BasicTextField(
                                        value = caption.value,
                                        onValueChange = { caption.value = it },
                                        maxLines = Int.MAX_VALUE,
                                        singleLine = false,
                                        modifier = Modifier.fillMaxWidth()

                                )
                                if (caption.value.isEmpty()) {
                                    Text(
                                            text = "What's on your mind?",
                                            color = Color.Gray,
                                            modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(vertical = 10.dp)
                    ) {
                        selectedImageUri?.let { uri ->
                            Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .aspectRatio(0.8f, false)


                            ) { // Display loading indicator if image is loading
                                if (isLoading) {
                                    CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                                val mimeType = context.contentResolver.getType(uri)
                                if (mimeType?.startsWith("image") == true) { // Show image
                                    AsyncImage(model = uri,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(1f),
                                            contentDescription = "Selected Image",
                                            contentScale = ContentScale.FillWidth,
                                            onLoading = {
                                                isLoading =
                                                    true // Set loading to true when image starts loading
                                            },
                                            onSuccess = {
                                                isLoading =
                                                    false // Set loading to false when image is loaded
                                            },
                                            onError = {
                                                isLoading =
                                                    false // Set loading to false if there was an error
                                            })
                                } else if (mimeType?.startsWith("video") == true) { // Show video
                                    VideoPlayer(uri = uri)
                                }

                            }
                        }
                    }
                }
            }
        }

        BottomNavigation(
                backgroundColor = Color.White, modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            OptionResource(uri = selectedImageUri, directory = imageDirectory, onSetUri = { uri ->
                selectedImageUri = uri
            })
        }
    }
}
