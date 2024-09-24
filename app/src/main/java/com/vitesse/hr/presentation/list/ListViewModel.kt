package com.vitesse.hr.presentation.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.presentation.list.event.ListEvent
import com.vitesse.hr.presentation.list.state.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    private val _activeTabIndex= MutableStateFlow(0)
    private val _isSearching = MutableStateFlow(false)

    private val _candidates = _searchText
        .onEach { _isSearching.update { true } }
        .flatMapLatest { searchText ->
            useCases.getCandidates(searchText)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    private val _favorites = _searchText
        .onEach { _isSearching.update { true } }
        .flatMapLatest { searchText ->
            useCases.getFavorites(searchText)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    private val _listState = MutableStateFlow(ListState())
    val state = combine(
        _listState,
        _activeTabIndex,
        _searchText,
        _candidates,
        _favorites
    ) { state, activeTabIndex, searchText, candidates, favorites ->
        state.copy(
            activeTabIndex = activeTabIndex,
            searchText = searchText,
            candidates = candidates,
            favorites = favorites
        )
    }
        .onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ListState())


    fun onEvent(event: ListEvent) = when (event) {
        is ListEvent.OnChangeTab -> {
            _activeTabIndex.value = event.tabIndex
        }

        is ListEvent.OnSearch -> {
            _searchText.value = event.searchText
        }

        is ListEvent.OnActiveSearch -> {
            _isSearching.value = event.active
        }

        ListEvent.OnAdd -> TODO()
    }

}