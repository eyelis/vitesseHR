package com.vitesse.hr.presentation.list.state

import com.vitesse.hr.domain.model.Candidate

data class ListState(
    val candidates : List<Candidate> = emptyList(),
    val favorites : List<Candidate> = emptyList(),
    val searchText : String = "",
    val isSearching : Boolean = false,
    val activeTabIndex: Int = 0
)
