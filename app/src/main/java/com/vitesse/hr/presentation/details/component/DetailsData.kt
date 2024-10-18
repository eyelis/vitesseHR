package com.vitesse.hr.presentation.details.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.vitesse.hr.R
import com.vitesse.hr.presentation.details.DetailState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsData(
    state: DetailState
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Row {
            GlideImage(
                modifier = Modifier.size(300.dp),
                model = state.photo,
                failure = placeholder(R.drawable.media),
                contentDescription = state.firstName,
                contentScale = ContentScale.Crop
            )
        }

        Row {
            ContactIcon(
                Icons.Outlined.Call,
                R.string.action_delete,
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts("tel", state.phoneNumber, null)
                )
            )

            ContactIcon(
                Icons.AutoMirrored.Outlined.Chat,
                R.string.action_delete,
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.fromParts("sms", state.phoneNumber, null)
                )
            )

            ContactIcon(
                Icons.Outlined.Email,
                R.string.action_delete,
                Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, state.email)
                }
            )
        }

        CardInfo(
            title = stringResource(id = R.string.detail_about_label)
        ) {
            Column {
                Text(
                    text = "${state.dateOfBirth} (${state.age} ${stringResource(id = R.string.detail_age_label)})"
                )
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.detail_birthday)
                )
            }
        }

        CardInfo(
            title = stringResource(id = R.string.detail_salary_title)
        ) {

            Text(
                text = "${state.expectedSalary} ${stringResource(id = R.string.detail_salary_eur_label)}"
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = "${stringResource(id = R.string.detail_salary_gbp_label)} ${state.expectedSalaryGbp}"
            )
        }

        CardInfo(
            title = "Notes",
            height = 230.dp
        ) {
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = state.note
            )
        }
    }
}