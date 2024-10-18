package com.vitesse.hr.presentation.list.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vitesse.hr.R
import com.vitesse.hr.presentation.list.state.ListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    state: ListState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit
) {
    androidx.compose.material3.SearchBar(
        query = state.searchText,
        onQueryChange = { onQueryChange(it) },
        onSearch = { onSearch(it) },
        active = state.isSearching,
        onActiveChange = { onActiveChange(it) },
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
            Text(stringResource(id = R.string.search_label))
        }
    ) {
    }
}