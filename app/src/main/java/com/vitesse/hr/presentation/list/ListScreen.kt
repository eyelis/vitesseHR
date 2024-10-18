package com.vitesse.hr.presentation.list

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitesse.hr.R
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.presentation.list.component.SearchBar
import com.vitesse.hr.presentation.list.component.TabBar
import com.vitesse.hr.presentation.list.event.ListEvent

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun ListScreen(
    onCandidateClick: (Candidate) -> Unit = {},
    onAddClick: () -> Unit = {},
    viewModel: ListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SearchBar(
                state = state,
                onSearch = { viewModel.onEvent(ListEvent.OnSearch(it)) },
                onQueryChange = { viewModel.onEvent(ListEvent.OnSearch(it)) },
                onActiveChange = { viewModel.onEvent(ListEvent.OnActiveSearch(it)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.content_description_add)
                )
            }
        }
    ) { padding ->

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            TabBar(
                state = state,
                onCandidateClick = onCandidateClick,
                onChangeTab = { viewModel.onEvent(ListEvent.OnChangeTab(it)) }
            )
        }
    }

}