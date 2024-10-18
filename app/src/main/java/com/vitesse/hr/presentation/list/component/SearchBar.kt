package com.vitesse.hr.presentation.list.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = state.searchText,
                onQueryChange = { onQueryChange(it) },
                onSearch = { onSearch(it) },
                expanded = state.isSearching,
                onExpandedChange = { onActiveChange(it) },
                enabled = true,
                placeholder = {
                    Text(stringResource(id = R.string.search_label))
                },
                leadingIcon = null,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )
                },
                interactionSource = null,
            )
        },
        expanded = state.isSearching,
        onExpandedChange = { onActiveChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        content = { }
    )
}