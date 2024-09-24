package com.vitesse.hr.presentation.details.event

import com.vitesse.hr.domain.model.Candidate

sealed class DetailEvent {
    data object AddFavorite: DetailEvent()
    data object Edit: DetailEvent()
    data object Delete: DetailEvent()
    data class Call(val candidate: Candidate): DetailEvent()
    data class Sms(val candidate: Candidate): DetailEvent()
    data class Email(val candidate: Candidate): DetailEvent()
}