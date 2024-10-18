package com.vitesse.hr.presentation.list.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vitesse.hr.R
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
        if (candidates.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(260.dp))
                if (candidates.isEmpty()) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),

                        horizontalArrangement =  Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Text(
                            modifier = Modifier
                                .align(alignment = Alignment.CenterVertically),
                            text = stringResource(id = R.string.no_candidate)
                        )
                    }

                }
            }

        }
    }
}
