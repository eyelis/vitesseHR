package com.vitesse.hr.domain.usecase

import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListAll(
    private val repository: CandidateRepository
) {
    operator fun invoke(
        searchText: String
    ): Flow<List<Candidate>> {

        val all = repository.all()

        return if (searchText.isBlank()) all
        else
            all
                .map { candidates ->
                    candidates.filter { candidate ->
                        candidate.firstName.contains(searchText, true)
                                || candidate.lastName.contains(searchText, true)
                    }
                }
    }
}