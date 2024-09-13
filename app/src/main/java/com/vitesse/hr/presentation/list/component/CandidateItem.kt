package com.vitesse.hr.presentation.list.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vitesse.hr.R
import com.vitesse.hr.domain.model.Candidate

@SuppressLint("ResourceType")
@Composable
fun CandidateItem(
    candidate: Candidate,
    onCandidateClick: (Candidate) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            //.background(color = Color(0xFFF3FBFF))
            .clickable {
                onCandidateClick(candidate)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            AsyncImage(
                //painter =  painterResource(R.drawable.media),
                model = candidate.photo,
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
                    //TODO 2 LINES MAX
                    candidate.note,
                    maxLines = 2
                )
            }
        }
    }
}
