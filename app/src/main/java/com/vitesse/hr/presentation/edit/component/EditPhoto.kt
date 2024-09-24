package com.vitesse.hr.presentation.edit.component

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.vitesse.hr.R
import com.vitesse.hr.presentation.edit.EditViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditPhoto(
    viewModel: EditViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    // val defaultUri = Uri.parse("android.resource://com.vitesse.hr/" + R.drawable.media);
    val state by viewModel.state.collectAsState()


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            val flags =
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            val resolver = context.contentResolver
            resolver.takePersistableUriPermission(uri!!, flags)

            viewModel.updateImageUri(uri)
        }
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = state.photo ?: R.drawable.media,
            /*    model = ImageRequest.Builder(LocalContext.current)
                    .data(state.photo)
                    .placeholder(R.drawable.media)
                    .build(),*/
            contentDescription = "candidate photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .clickable {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
        )

    }

}