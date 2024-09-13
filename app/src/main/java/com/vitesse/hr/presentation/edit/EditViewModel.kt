package com.vitesse.hr.presentation.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.model.InvalidCandidateException
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.presentation.edit.event.EditEvent
import com.vitesse.hr.presentation.edit.state.EditState
import com.vitesse.hr.presentation.edit.validation.ValidateState
import com.vitesse.hr.presentation.edit.validation.dateFrom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = MutableStateFlow(EditState())
    val state: StateFlow<EditState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    //  private val _imageUri = MutableStateFlow<Uri?>(null)
    //  val _imageUri = _imageUri


    fun onEvent(event: EditEvent) = when (event) {
        is EditEvent.OnSave -> {
            val stateValidator = ValidateState(EditState::class)
            val errors = stateValidator.validate(state.value)

            if (errors.isEmpty()) {
                _state.update {
                    it.copy(errors = emptyMap())
                }

                viewModelScope.launch {
                    try {

                        val value = _state.value
                        useCases.addCandidate.invoke(
                            Candidate(
                                firstName = value.firstName,
                                lastName = value.lastName,
                                phoneNumber = value.phoneNumber,
                                email = value.email,
                                note = value.note,
                                dateOfBirth = dateFrom(value.dateOfBirth, "dd/MM/yyyy")!!,
                                expectedSalary = value.expectedSalary.toLong(),
                                photo = value.photo,
                                isFavorite = value.isFavorite
                            )
                        )
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

        is EditEvent.OnBack -> TODO()

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

    sealed class UiEvent {
        data class Error(val message: String) : UiEvent()
        data object Saved : UiEvent()
    }

}