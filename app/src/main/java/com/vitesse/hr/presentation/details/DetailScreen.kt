package com.vitesse.hr.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitesse.hr.R
import com.vitesse.hr.presentation.details.component.ConfirmDialog
import com.vitesse.hr.presentation.details.component.DetailsData
import com.vitesse.hr.presentation.details.event.DetailEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    id: Int,
    onBackClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
    onEditClick: (Int) -> Unit
) {

    val state by viewModel.state.collectAsState()

    var showDeleteConfirm by remember { mutableStateOf(false) }

    if (showDeleteConfirm) {
        ConfirmDialog(
            title = stringResource(id = R.string.delete_dialog_title),
            content = stringResource(id = R.string.delete_dialog_message),
            onDismiss = { showDeleteConfirm = false },
            onConfirm = {
                showDeleteConfirm = false
                viewModel.onEvent(DetailEvent.Delete)
            }
        )
    }

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is DetailViewModel.UiEvent.Deleted -> {
                    onBackClick()
                }

                is DetailViewModel.UiEvent.Edit -> {
                    onEditClick(id)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = "${state.firstName} ${state.lastName}"
                    )
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
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(DetailEvent.AddFavorite)
                    }) {
                        Icon(
                            imageVector = if (state.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                            contentDescription = stringResource(id = if (state.isFavorite) R.string.action_favorite_on else R.string.action_favorite_off)
                        )
                    }
                    IconButton(onClick = {
                        onEditClick(state.id!!)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(id = R.string.action_edit)
                        )
                    }
                    IconButton(onClick = {
                        showDeleteConfirm = true

                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(id = R.string.action_delete)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(padding)
        ) {

            item {
                DetailsData(state)
            }
        }
    }
}




