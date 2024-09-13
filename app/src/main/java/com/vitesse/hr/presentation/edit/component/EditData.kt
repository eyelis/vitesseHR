package com.vitesse.hr.presentation.edit.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vitesse.hr.R
import com.vitesse.hr.presentation.edit.EditViewModel

//FIXME pass state instead of viewmodel
@Composable
fun EditData(
    viewModel: EditViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    EditPhoto(
        viewModel = viewModel,
        modifier = modifier
    )

    EditField(
        viewModel = viewModel,
        modifier = modifier,
        name = "firstName",
        label = "First Name",
        value = state.firstName,
        icon = Icons.Outlined.Person,
        onValueChanged = { viewModel.updateProperty(state.copy(firstName = it)) }
    )

    EditField(
        viewModel = viewModel,
        modifier = modifier,
        name = "lastName",
        label = "Last Name",
        value = state.lastName,
        icon = null,
        onValueChanged = { viewModel.updateProperty(state.copy(lastName = it)) }
    )

    EditField(
        viewModel = viewModel,
        modifier = modifier,
        name = "phoneNumber",
        label = "Phone",
        value = state.phoneNumber,
        icon = Icons.Outlined.Phone,
        onValueChanged = { viewModel.updateProperty(state.copy(phoneNumber = it)) },
        type = KeyboardType.Phone
    )

    EditField(
        viewModel = viewModel,
        modifier = modifier,
        name = "email",
        label = "Email",
        value = state.email,
        icon = Icons.Outlined.Email,
        onValueChanged = { viewModel.updateProperty(state.copy(email = it)) },
        type = KeyboardType.Email
    )

    EditField(
        viewModel = viewModel,
        modifier = modifier,
        name = "dateOfBirth",
        label = "Date",
        value = state.dateOfBirth,
        icon = null,
        onValueChanged = { viewModel.updateProperty(state.copy(dateOfBirth = it)) }
    )

    EditField(
        viewModel = viewModel,
        modifier = modifier,
        name = "expectedSalary",
        label = "Expected Salary",
        value = state.expectedSalary,
        icon = Icons.Outlined.AttachMoney,
        onValueChanged = { viewModel.updateProperty(state.copy(expectedSalary = it)) },
        type = KeyboardType.Number
    )

    EditField(
        viewModel = viewModel,
        modifier = modifier,
        name = "note",
        label = "Note",
        value = state.note,
        icon = Icons.Outlined.Edit,
        lines = 5,
        onValueChanged = { viewModel.updateProperty(state.copy(note = it)) }
    )
}