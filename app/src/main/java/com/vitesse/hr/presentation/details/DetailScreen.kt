package com.vitesse.hr.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.format.DateFormat
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ContentAlpha
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.vitesse.hr.R
import com.vitesse.hr.presentation.details.event.DetailEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
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
        }
    }
}

@Composable
private fun CardInfo(
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

@Composable
private fun ContactIcon(
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

@Composable
fun ConfirmDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = { Text(title) },
        text = { Text(content) },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(id = R.string.cancel_delete))
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(stringResource(id = R.string.confirm_delete))
            }
        }
    )
}