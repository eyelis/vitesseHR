package com.vitesse.hr.presentation.list

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vitesse.hr.R
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.presentation.list.component.SearchBar
import com.vitesse.hr.presentation.list.component.TabBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun ListScreen(
    onCandidateClick: (Candidate) -> Unit = {},
    onAddClick: () -> Unit = {},
    viewModel: ListViewModel = hiltViewModel()
) {


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SearchBar(viewModel)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.contentDescription_add)
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
                viewModel = viewModel,
                onCandidateClick = onCandidateClick
            )
        }
    }

}