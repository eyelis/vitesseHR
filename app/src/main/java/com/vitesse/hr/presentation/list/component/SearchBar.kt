package com.vitesse.hr.presentation.list.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vitesse.hr.R
import com.vitesse.hr.presentation.list.ListViewModel
import com.vitesse.hr.presentation.list.event.ListEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: ListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    androidx.compose.material3.SearchBar(
        query = state.searchText,
        onQueryChange = { text ->
            viewModel.onEvent(ListEvent.OnSearch(text))
        },
        onSearch = { text ->
            viewModel.onEvent(ListEvent.OnSearch(text))
        },
        active = state.isSearching,
        onActiveChange = { active ->
            viewModel.onEvent(ListEvent.OnActiveSearch(active))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(stringResource(id = R.string.hint_search))
        }
    ) {
    }
}