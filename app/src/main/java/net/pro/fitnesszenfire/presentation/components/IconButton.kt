package net.pro.fitnesszenfire.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.pro.fitnesszenfire.R


@Composable
fun IconWithDynamicState(
    isExpanded: Boolean,
    onIconClick: () -> Unit,
    accentOrange: Color,
    borderColor: Color,
    iconEnabled: Painter,
    iconDisabled: Painter
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clickable(
                indication = null, // Disables ripple effect
                interactionSource = remember { MutableInteractionSource() }, // Required to manage interactions
                onClick = {
                    onIconClick()
                }
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center

    ) {
        Icon(
            painter = if (isExpanded) iconEnabled else iconDisabled,
            contentDescription = if (isExpanded) "close" else "select resource",
            tint = accentOrange,
            modifier = Modifier.size(15.dp) // Maintain the size of the icon
        )
    }
}


@Composable
fun FullWidthImage(
    painter: Painter
) {


    SubcomposeLayout { constraints ->
        val placeable = subcompose("image") {
            Image(
                painter = painter,
                contentDescription = "image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()

            )
        }[0].measure(constraints)

        val aspectRatio = painter.intrinsicSize.width / painter.intrinsicSize.height
        val height = (constraints.maxWidth / aspectRatio).toInt()

        layout(constraints.maxWidth, height) {
            placeable.place(0, 0)
        }
    }
}
