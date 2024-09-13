package com.vitesse.hr.presentation.edit.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vitesse.hr.R
import com.vitesse.hr.presentation.edit.EditViewModel


@Composable
fun EditPhoto(
    viewModel: EditViewModel,
    modifier: Modifier = Modifier
) {
    // val defaultUri = Uri.parse("android.resource://com.vitesse.hr/" + R.drawable.media);
    val state by viewModel.state.collectAsState()



    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        //FIXME CHECK WHEN NO IMAGE SELECTED
        onResult = { uri ->
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
            model = if (state.photo != null) state.photo else R.drawable.media,
            contentDescription = "candidate photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
        )

    }

}