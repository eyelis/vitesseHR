package com.vitesse.hr.presentation.details.event

sealed class DetailEvent {
    data object AddFavorite: DetailEvent()
    data object Delete: DetailEvent()
}