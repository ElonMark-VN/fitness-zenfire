package net.pro.fitnesszenfire.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import net.pro.fitnesszenfire.R

@Composable
fun UserProfileImage(userAvatarUrl: String) {
    Image(
        painter = rememberImagePainter(data = userAvatarUrl),
        contentDescription = stringResource(id = R.string.display_picture),
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
    )
}