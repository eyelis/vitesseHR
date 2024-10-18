package com.vitesse.hr.presentation.details.component

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun ContactIcon(
    icon: ImageVector,
    descriptionId: Int,
    intent: Intent
) {
    val context = LocalContext.current
    IconButton(
        modifier = Modifier
            .padding(end = 20.dp),
        onClick = {
            ContextCompat.startActivity(context, intent, null)
        }
    ) {
        Icon(
            modifier = Modifier
                .border(width = 1.dp, shape = CircleShape, color = Color.Black)
                .padding(8.dp),
            imageVector = icon,
            contentDescription = stringResource(id = descriptionId)
        )
    }
}