package com.vitesse.hr.presentation.list.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitesse.hr.domain.model.Candidate


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CandidateList(
    modifier: Modifier,
    candidates: List<Candidate>,
    onCandidateClick: (Candidate) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(candidates) { candidate ->
            CandidateItem(
                candidate = candidate,
                modifier = Modifier
                    .fillMaxWidth(),
                onCandidateClick = onCandidateClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
