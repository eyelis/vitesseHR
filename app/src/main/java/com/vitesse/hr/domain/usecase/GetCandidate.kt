package com.vitesse.hr.domain.usecase

import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCandidate(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke(id: Int): Candidate? {
        return repository.byId(id)
    }
}