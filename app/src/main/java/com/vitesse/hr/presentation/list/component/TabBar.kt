package com.vitesse.hr.presentation.list.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vitesse.hr.R
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.presentation.list.state.ListState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TabBar(
    onCandidateClick: (Candidate) -> Unit = {},
    state: ListState,
    onChangeTab: (Int) -> Unit
) {

    val tabs =
        listOf(
            stringResource(id = R.string.tab_all_label),
            stringResource(id = R.string.tab_favorites_label)
        )

    TabRow(selectedTabIndex = state.activeTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(text = { Text(title) },
                selected = state.activeTabIndex == index,
                onClick = { onChangeTab(index) }
            )
        }
    }

    when (state.activeTabIndex) {

        0 -> CandidateList(
            modifier = Modifier.fillMaxWidth(),
            candidates = state.candidates,
            onCandidateClick = onCandidateClick
        )

        1 -> CandidateList(
            modifier = Modifier.fillMaxWidth(),
            candidates = state.favorites,
            onCandidateClick = onCandidateClick
        )
    }

}