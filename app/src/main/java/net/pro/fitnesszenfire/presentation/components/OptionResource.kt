package net.pro.fitnesszenfire.presentation.components

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.ui.theme.accentOrange
import net.pro.fitnesszenfire.ui.theme.accentOrangeAlpha
import net.pro.fitnesszenfire.ui.theme.accentOrangeAlpha05
import java.io.File

@Composable
fun OptionResource(
    uri: Uri? = null,
    directory: File? = null,
    onSetUri: (Uri) -> Unit = {},
) {
    val context = LocalContext.current
    val tempUri = remember { mutableStateOf<Uri?>(null) }
    val authority = stringResource(id = R.string.fileprovider)

    fun getTempUri(): Uri? {
        directory?.let {
            it.mkdirs()
            val file = File.createTempFile(
                "image_" + System.currentTimeMillis().toString(),
                ".jpg",
                it
            )
            return FileProvider.getUriForFile(
                context,
                authority,
                file
            )
        }
        return null
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), // Use GetContent instead of PickVisualMedia
        onResult = {
            it?.let {
                onSetUri.invoke(it)
            }
        }
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            tempUri.value?.let {
                if (isSaved) {
                    onSetUri.invoke(it)
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val tmpUri = getTempUri()
            tempUri.value = tmpUri
            tempUri.value?.let { takePhotoLauncher.launch(it) }
        }
    }

    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconWithDynamicState(
            isExpanded = isExpanded,
            onIconClick = { isExpanded = !isExpanded },
            accentOrange = accentOrange,
            borderColor = accentOrangeAlpha05,
            iconEnabled = painterResource(id = R.drawable.baseline_close_24),
            iconDisabled = painterResource(id = R.drawable.baseline_add_24)
        )

        Spacer(modifier = Modifier.width(15.dp))

        AnimatedVisibility(visible = isExpanded) {
            Row(
                modifier = Modifier
                    .height(38.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(accentOrangeAlpha, shape = RoundedCornerShape(20.dp))
                    .padding(15.dp, 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(id = R.drawable.image),
                    tint = accentOrange,
                    contentDescription = "Select photo",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            imagePicker.launch("image/*") // Opens photo application
                        }
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_video),
                    tint = accentOrange,
                    contentDescription = "Select video",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            imagePicker.launch("video/*") // Opens video application
                        }
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    painter = painterResource(id = R.drawable.camera),
                    tint = accentOrange,
                    contentDescription = "Take photo",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            val permission = Manifest.permission.CAMERA
                            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                                val tmpUri = getTempUri()
                                tempUri.value = tmpUri
                                tempUri.value?.let { takePhotoLauncher.launch(it) }
                            } else {
                                cameraPermissionLauncher.launch(permission)
                            }
                        }
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    painter = painterResource(id = R.drawable.attachment),
                    tint = accentOrange,
                    contentDescription = "Select GIF",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {

                        }
                )

                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }


}
