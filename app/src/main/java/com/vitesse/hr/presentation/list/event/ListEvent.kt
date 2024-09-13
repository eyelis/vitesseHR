package com.vitesse.hr.presentation.list.event


sealed class ListEvent {
    data class OnChangeTab(val tabIndex: Int): ListEvent()
    data class OnSearch(val searchText: String): ListEvent()
    data class OnActiveSearch(val active: Boolean): ListEvent()
    data object OnAdd: ListEvent()
}