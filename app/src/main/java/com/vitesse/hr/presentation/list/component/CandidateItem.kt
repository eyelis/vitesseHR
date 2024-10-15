package com.vitesse.hr.presentation.list.component

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vitesse.hr.R
import com.vitesse.hr.domain.model.Candidate


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalGlideComposeApi::class)
@SuppressLint("ResourceType")
@Composable
fun CandidateItem(
    candidate: Candidate,
    onCandidateClick: (Candidate) -> Unit,
    modifier: Modifier = Modifier
) {

    val imagePermissionState = rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)

    LaunchedEffect(imagePermissionState) {
        if (!imagePermissionState.status.isGranted) {
            imagePermissionState.launchPermissionRequest()
        }
    }

    Box(
        modifier = modifier
            .clickable {
                onCandidateClick(candidate)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            GlideImage(
                model = if (imagePermissionState.status.isGranted) candidate.photo else R.drawable.media,
                failure = placeholder(R.drawable.media),
                contentDescription = candidate.firstName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 10.dp)
            )
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        candidate.firstName,
                        fontSize = 25.sp
                    )
                    Text(
                        candidate.lastName,
                        fontSize = 25.sp
                    )
                }
                Text(
                    candidate.note,
                    maxLines = 2
                )
            }
        }
    }
}
