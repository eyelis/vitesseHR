package com.vitesse.hr.presentation.edit.event


sealed class EditEvent {
    data object OnSave : EditEvent()
}