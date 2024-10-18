package com.vitesse.hr.presentation.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardInfo(
    title: String,
    height: Dp = 170.dp,
    content: @Composable () -> Unit
) {
    Row {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 20.dp),

                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
                content()
            }
        }
    }
}