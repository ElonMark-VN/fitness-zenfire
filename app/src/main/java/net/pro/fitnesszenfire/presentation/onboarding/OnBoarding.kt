package net.pro.fitnesszenfire.presentation.onboarding

import android.app.Activity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import net.pro.fitnesszenfire.presentation.onboarding.util.OnBoardingItem
import net.pro.fitnesszenfire.ui.theme.backgroundLight
import net.pro.fitnesszenfire.ui.theme.mainColor
import net.pro.fitnesszenfire.utils.Screen

@Composable
fun OnBoarding(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val items = OnBoardingItem.get()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundLight
    ) {
        val currentItem = items[pagerState.currentPage]

        Image(
            painter = painterResource(id = currentItem.image),
            contentDescription = stringResource(id = currentItem.title),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current as Activity
            context.window.statusBarColor = backgroundLight.toArgb()
            context.window.navigationBarColor = backgroundLight.toArgb()

            HorizontalPager(
                count = items.size,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.8f),
                state = pagerState,
                verticalAlignment = Alignment.Bottom
            ) { page ->
                OnboardingPage(item = items[page])
            }

            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 16.dp)
                    .fillMaxSize()
                    .weight(0.2f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BottomSection(size = items.size, index = pagerState.currentPage) {
                    if (pagerState.currentPage + 1 < items.size) {
                        scope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        viewModel.onEvent(OnBoardingEvent.CompleteOnboarding {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPage(item: OnBoardingItem) {
    Column(
        modifier = Modifier.padding(20.dp, 0.dp, 40.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Text(
            text = stringResource(id = item.title),
            color = Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start

        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = item.text),
            color = Color.White,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start


        )
    }
}

@Composable
fun BottomSection(
    size: Int,
    index: Int,
    onNextClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Indicators(size = size, index = index)

        Button(
            onClick = onNextClicked,
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(25.dp),
                    ambientColor = mainColor,
                    spotColor = mainColor
                )
                .clip(RoundedCornerShape(25.dp))
                .height(50.dp)
                .width(200.dp)
                .align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = mainColor,
                contentColor = Color.Black
            )
        ) {
            Text(text = "Next")
        }

    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) mainColor else Color.White)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOnBoarding() {
    val navController = rememberNavController()
    OnBoarding(navController = navController)
}
