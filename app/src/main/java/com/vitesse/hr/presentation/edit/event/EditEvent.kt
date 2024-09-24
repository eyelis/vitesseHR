package com.vitesse.hr.presentation.edit.event

import com.vitesse.hr.presentation.edit.state.EditState


sealed class EditEvent {
    data class OnSave(val editState: EditState): EditEvent()
}