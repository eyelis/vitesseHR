package com.vitesse.hr.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.presentation.details.event.DetailEvent
import com.vitesse.hr.presentation.util.DateUtils.now
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.yearsUntil
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var id: Int? = null

    init {
        println("init")
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id == -1) {
                return@let
            }
            this.id = id

            load(id)

        }
    }


    fun onEvent(event: DetailEvent) {

        when (event) {
            is DetailEvent.AddFavorite -> {
                _state.update {
                    it.copy(
                        isFavorite = !state.value.isFavorite
                    )
                }

                viewModelScope.launch {
                    useCases.getCandidate.invoke(_state.value.id)?.let { candidate ->
                        useCases.addCandidate.invoke(
                            candidate
                                .copy(isFavorite = _state.value.isFavorite)
                        )
                    }
                }
            }

            is DetailEvent.Call -> TODO()
            is DetailEvent.Delete -> {
                viewModelScope.launch {
                    useCases.getCandidate.invoke(_state.value.id)?.let { candidate ->
                        useCases.deleteCandidate.invoke(candidate)
                    }
                    _eventFlow.emit(UiEvent.Deleted)
                }

            }

            is DetailEvent.Edit -> TODO()
            is DetailEvent.Email -> TODO()
            is DetailEvent.Sms -> TODO()
        }
    }

    fun load(id: Int) {
        viewModelScope.launch {
            useCases.getCandidate.invoke(id)?.let { candidate ->
                _state.update {
                    it.copy(
                        id = candidate.id,
                        firstName = candidate.firstName,
                        lastName = candidate.lastName,
                        photo = candidate.photo,
                        email = candidate.email,
                        isFavorite = candidate.isFavorite,
                        phoneNumber = candidate.phoneNumber,
                        dateOfBirth = candidate.dateOfBirth.format(LocalDate.Format {
                            dayOfMonth(); chars(
                            "/"
                        ); monthNumber(); chars("/");year()
                        }),
                        expectedSalary = if (candidate.expectedSalary != null) candidate.expectedSalary.toString() else "",
                        age = candidate.dateOfBirth.yearsUntil(LocalDate.now()).toString(),
                        note = candidate.note
                    )
                }
            }
        }
    }

    sealed class UiEvent {
        data object Deleted : UiEvent()
    }

}