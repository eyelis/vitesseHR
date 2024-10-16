package com.vitesse.hr.presentation.edit

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.model.InvalidCandidateException
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.presentation.AppDispatchers
import com.vitesse.hr.presentation.edit.event.EditEvent
import com.vitesse.hr.presentation.edit.state.EditState
import com.vitesse.hr.presentation.validation.ValidateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val useCases: UseCases,
    private val savedStateHandle: SavedStateHandle,
    private val stateValidator: ValidateState<EditState>,
    private val appDispatchers: AppDispatchers
) : ViewModel() {

    private val _state = MutableStateFlow(EditState())
    val state: StateFlow<EditState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        load()
    }

    fun onEvent(event: EditEvent) = when (event) {
        is EditEvent.OnSave -> {
            val errors = stateValidator.validate(state.value)

            if (errors.isEmpty()) {
                _state.update {
                    it.copy(errors = emptyMap())
                }

                viewModelScope.launch {
                    try {
                        val value = _state.value
                        withContext(appDispatchers.IO) {
                            useCases.addCandidate.invoke(
                                Candidate(
                                    id = value.id,
                                    firstName = value.firstName,
                                    lastName = value.lastName,
                                    phoneNumber = value.phoneNumber,
                                    email = value.email,
                                    note = value.note,
                                    dateOfBirth = value.dateOfBirth!!,
                                    expectedSalary = value.expectedSalary.toLongOrNull(),
                                    photo = value.photo,
                                    isFavorite = value.isFavorite
                                )
                            )
                        }
                        _eventFlow.emit(UiEvent.Saved)
                    } catch (e: InvalidCandidateException) {
                        _eventFlow.emit(
                            UiEvent.Error(
                                message = e.message ?: "Couldn't save candidate"
                            )
                        )
                    }
                }
                _state.update { EditState() }

            } else {
                _state.update {
                    it.copy(errors = errors)
                }
            }
        }

    }

    fun isError(fieldName: String): Boolean {
        return _state.value.errors.containsKey(fieldName)
    }

    fun updateProperty(newState: EditState) {
        _state.update { newState }
    }

    fun updateImageUri(newUri: Uri?) {
        _state.update { it.copy(photo = newUri) }
    }

    private fun load() {
        viewModelScope.launch {
            savedStateHandle.get<String>("id")?.toInt().let { id ->
                if (id == -1) {
                    return@let
                }

                val candidate = withContext(appDispatchers.IO) {
                    useCases.getCandidate.invoke(id!!)
                }

                _state.update {
                    it.copy(
                        id = candidate!!.id,
                        firstName = candidate.firstName,
                        lastName = candidate.lastName,
                        photo = candidate.photo,
                        email = candidate.email,
                        isFavorite = candidate.isFavorite,
                        phoneNumber = candidate.phoneNumber,
                        dateOfBirth = candidate.dateOfBirth,
                        expectedSalary = if (candidate.expectedSalary != null) candidate.expectedSalary.toString() else "",
                        note = candidate.note,
                        isLoading = false
                    )
                }

            }
        }
    }


    fun updateDate(dateMillis: Long?) {
        _state.update { it.copy(dateOfBirth = toLocalDate(dateMillis)) }
        if (dateMillis != null) {
            _state.update {
                it.copy(errors = _state.value.errors.minus("dateOfBirth"))
            }
        }
    }


    private fun toLocalDate(dateMillis: Long?) =
        dateMillis?.let { newDate ->
            Instant.fromEpochMilliseconds(newDate).toLocalDateTime(
                TimeZone.currentSystemDefault()
            ).date
        }


    sealed class UiEvent {
        data class Error(val message: String) : UiEvent()
        data object Saved : UiEvent()
    }

}