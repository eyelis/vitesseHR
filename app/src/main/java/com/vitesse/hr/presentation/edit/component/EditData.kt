package com.vitesse.hr.presentation.edit.component

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vitesse.hr.R
import com.vitesse.hr.presentation.edit.state.EditState
import com.vitesse.hr.presentation.util.PastOrPresentSelectableDates
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditData(
    modifier: Modifier = Modifier,
    state: EditState,
    onUpdateDate: (Long) -> Unit,
    onUpdateFirstName: (String) -> Unit,
    onUpdateLastName: (String) -> Unit,
    onUpdatePhone: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateSalary: (String) -> Unit,
    onUpdateNote: (String) -> Unit,
    onUpdateImageUri: (Uri) -> Unit,
    isError: (String) -> Boolean
) {

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        selectableDates = PastOrPresentSelectableDates,
        initialSelectedDateMillis = state
            .dateOfBirth
            ?.atStartOfDayIn(TimeZone.UTC)
            ?.toEpochMilliseconds()
    )

    LaunchedEffect(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { onUpdateDate(it) }
    }

    EditPhoto(
        state = state,
        modifier = modifier,
        onUpdateImageUri = { onUpdateImageUri(it) }
    )

    EditField(
        state = state,
        modifier = modifier,
        name = "firstName",
        label = stringResource(id = R.string.field_first_name_label),
        value = state.firstName,
        icon = Icons.Outlined.Person,
        onValueChanged = { onUpdateFirstName(it) },
        isError = { isError(it) }
    )

    EditField(
        state = state,
        modifier = modifier,
        name = "lastName",
        label = stringResource(id = R.string.field_last_name_label),
        value = state.lastName,
        icon = null,
        onValueChanged = { onUpdateLastName(it) },
        isError = { isError(it) }
    )

    EditField(
        state = state,
        modifier = modifier,
        name = "phoneNumber",
        label = stringResource(id = R.string.field_phone_label),
        value = state.phoneNumber,
        icon = Icons.Outlined.Phone,
        onValueChanged = { onUpdatePhone(it) },
        type = KeyboardType.Phone,
        isError = { isError(it) }
    )

    EditField(
        state = state,
        modifier = modifier,
        name = "email",
        label = stringResource(id = R.string.field_email_label),
        value = state.email,
        icon = Icons.Outlined.Email,
        onValueChanged = { onUpdateEmail(it) },
        type = KeyboardType.Email,
        isError = { isError(it) }
    )

    Row(
        modifier = Modifier.padding(20.dp)
    ) {
        Icon(
            modifier = Modifier.padding(top = 5.dp, end = 10.dp),
            imageVector = Icons.Outlined.Cake,
            contentDescription = stringResource(id = R.string.icon_person_description)
        )

        DatePicker(
            state = datePickerState,
            showModeToggle = true,
            title = {
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 12.dp, top = 16.dp),
                    text = stringResource(id = R.string.field_date_label)
                )
            },
            headline = {
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 12.dp, bottom = 12.dp),
                    text = DatePickerDefaults.dateFormatter().formatDate(
                        datePickerState.selectedDateMillis,
                        Locale.getDefault()
                    )
                        ?: stringResource(id = R.string.field_date_enter),

                    color = if (isError("dateOfBirth")) MaterialTheme.colorScheme.error else Color.Unspecified
                )
            },

            dateFormatter = DatePickerDefaults.dateFormatter(),

            )
    }

    EditField(
        state = state,
        modifier = modifier,
        name = "expectedSalary",
        label = stringResource(id = R.string.field_expect_salary_label),
        value = state.expectedSalary,
        icon = Icons.Outlined.AttachMoney,
        onValueChanged = { onUpdateSalary(it) },
        type = KeyboardType.Number,
        isError = { isError(it) }
    )

    EditField(
        state = state,
        modifier = modifier,
        name = "note",
        label = stringResource(id = R.string.field_note_label),
        value = state.note,
        icon = Icons.Outlined.Edit,
        lines = 5,
        onValueChanged = { onUpdateNote(it) },
        isError = { isError(it) }
    )
}

