package net.pro.fitnesszenfire.presentation.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.pro.fitnesszenfire.domain.model.Restaurant
import net.pro.fitnesszenfire.ui.theme.linearWhite
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit



@Composable
fun ActivityCard(
    restaurant: Restaurant,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 2.dp)
            .shadow(
                elevation = 18.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = linearWhite,
                spotColor = linearWhite,
            )

    )
    {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White)

                .padding(10.dp, 5.dp)
            ,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = restaurant.image),
                contentDescription = "Restaurant",
                modifier = Modifier
                    .size(38.dp, 38.dp)
                    .shadow(elevation = 0.dp, shape = CircleShape, clip = true),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(10.dp,10.dp)) {
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    androidx.compose.material.Text(
                        text = "${restaurant.name}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    androidx.compose.material.Text(
                        text = "See all",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(3.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){
                    androidx.compose.material.Text(
                        text = "${restaurant.name}",
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontSize = 8.sp
                    )
                    androidx.compose.material.Text(
                        text = "See all",
                        color = Color.Black,
                        fontStyle = FontStyle.Normal,
                        fontSize = 8.sp
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(5.dp))

    }


}

fun getTimeInMins(timeInMillis: Long): String {
    var millis = timeInMillis
    require(millis >= 0) { "Less than a minute" }
    val days: Long = TimeUnit.MILLISECONDS.toDays(millis)
    millis -= TimeUnit.DAYS.toMillis(days)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(millis)
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val sb = StringBuilder(64)
    if (hours > 0) {
        sb.append(hours)
        sb.append(" Hr ")
    }
    sb.append(minutes)
    sb.append(" Min ")
    return sb.toString()
}

