package com.vitesse.hr.presentation.edit

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitesse.hr.R
import com.vitesse.hr.presentation.edit.component.EditData
import com.vitesse.hr.presentation.edit.event.EditEvent
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    viewModel: EditViewModel = hiltViewModel(),
    id: Int
) {

    val state by viewModel.state.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditViewModel.UiEvent.Error -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is EditViewModel.UiEvent.Saved -> {
                    onSaveClick()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.action_add))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.action_back)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.onEvent(EditEvent.OnSave(state))
                }
            ) {
                Text(
                    text = stringResource(id = R.string.action_save)
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .fillMaxWidth()
                .padding(padding)
        ) {

            if(id == -1 || !state.isLoading) {
                EditData(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    onUpdateFirstName = {viewModel.updateProperty(state.copy(firstName = it))},
                    onUpdateLastName = {viewModel.updateProperty(state.copy(lastName = it))},
                    onUpdateEmail = {viewModel.updateProperty(state.copy(email = it))},
                    onUpdateNote = {viewModel.updateProperty(state.copy(note = it))},
                    onUpdateSalary = {viewModel.updateProperty(state.copy(expectedSalary = it))},
                    onUpdatePhone = {viewModel.updateProperty(state.copy(phoneNumber = it))},
                    onUpdateDate = {viewModel.updateDate(it)},
                    onUpdateImageUri = {viewModel.updateImageUri(it)},
                    isError = {viewModel.isError(it)}
                )
            }

        }
    }

}