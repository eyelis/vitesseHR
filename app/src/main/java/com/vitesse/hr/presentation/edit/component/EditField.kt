package com.vitesse.hr.presentation.edit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vitesse.hr.R
import com.vitesse.hr.presentation.edit.EditViewModel

@Composable
fun EditField(
    viewModel: EditViewModel,
    modifier: Modifier = Modifier,
    name: String,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    icon: ImageVector?,
    lines: Int = 1,
    type: KeyboardType = KeyboardType.Text
) {
    val state by viewModel.state.collectAsState()

    Row(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp),

       // horizontalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        Column(
            modifier = Modifier
                .weight(0.1f)
                .padding(top = 10.dp)
               // .padding(top = 10.dp, end = if (icon != null) 10.dp else 35.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = R.string.icon_person_description)
                )
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .weight(1f),
            //  .weight(1f),
            minLines = lines,
            maxLines = lines,
            label = { Text(label) },
            value = value,
            onValueChange = { onValueChanged(it) },
            isError = viewModel.isError(name),
            keyboardOptions = KeyboardOptions(keyboardType = type)
        )

    }
    Column {
        state.errors.getOrDefault(name, emptyList()).forEach { error ->
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}