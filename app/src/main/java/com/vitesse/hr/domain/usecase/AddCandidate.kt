package com.vitesse.hr.domain.usecase

import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.repository.CandidateRepository

class AddCandidate(
    private val repository: CandidateRepository
) {
    suspend operator fun invoke(candidate: Candidate) {
        return repository.upsert(candidate)
    }
}