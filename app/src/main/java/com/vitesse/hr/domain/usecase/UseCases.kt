package com.vitesse.hr.domain.usecase

data class UseCases(
    val getCandidates: ListAll,
    val getFavorites: ListFavorites,
    val getCandidate: GetCandidate,
    val addCandidate: AddCandidate,
    val deleteCandidate: DeleteCandidate,
    val getCurrency: GetCurrency
)
