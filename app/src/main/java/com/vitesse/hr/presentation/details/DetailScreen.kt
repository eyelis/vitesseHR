package com.vitesse.hr.presentation.details

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    viewModel: DetailViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()

    viewModel.load(id)

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is DetailViewModel.UiEvent.Deleted -> {
                    onBackClick()
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
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(DetailEvent.AddFavorite)
                    }) {
                        Icon(
                            imageVector = if (state.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                            contentDescription = stringResource(id = if (state.isFavorite) R.string.favorite_on else R.string.favorite_off)
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(DetailEvent.Edit)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(id = R.string.edit)
                        )
                    }
                    IconButton(onClick = {
                        viewModel.onEvent(DetailEvent.Delete)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(id = R.string.delete)
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
                        ContactIcon(Icons.Outlined.Call, R.string.delete)
                        ContactIcon(Icons.AutoMirrored.Outlined.Chat, R.string.delete)
                        ContactIcon(Icons.Outlined.Email, R.string.delete)
                    }


                    CardInfo(
                        title = "A propos"
                    ) {
                        Column {
                            Text(
                                text = "${state.dateOfBirth} (${state.age} ans)"
                            )
                            Text(
                                modifier = Modifier.alpha(ContentAlpha.medium),
                                text = "Anniversaire"
                            )
                        }
                    }

                    CardInfo(
                        title = "Pretentions salariales"
                    ) {

                        Text(
                            text = "${state.expectedSalary} €"
                        )
                        Text(
                            //TODO Retrofit + exchange rate api.
                            modifier = Modifier.alpha(ContentAlpha.medium),
                            text = "soit £ ${state.expectedSalary}"
                        )

                    }

                    CardInfo(
                        title = "Notes",
                        height = 270.dp
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
            //   .defaultMinSize(minHeight = height)
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
    descriptionId: Int
) {
    Icon(
        modifier = Modifier
            .padding(end = 20.dp)
            // .size(50.dp)
            .border(width = 1.dp, shape = CircleShape, color = Color.Black)
            .padding(8.dp),
        imageVector = icon,
        contentDescription = stringResource(id = descriptionId)
    )
}