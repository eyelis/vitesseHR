package com.vitesse.hr.domain.usecase

import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListFavorites(
    private val repository: CandidateRepository
) {
    operator fun invoke(
        searchText: String
    ): Flow<List<Candidate>> {

        val favorites = repository.all()
            .map { candidates ->
                candidates.filter { candidate ->
                    candidate.isFavorite
                }
            }

        return if (searchText.isBlank()) favorites
        else
            favorites
                .map { candidates ->

                    candidates.filter { candidate ->
                        candidate.firstName.contains(searchText, true)
                                || candidate.lastName.contains(searchText, true)
                    }
                }
    }

}