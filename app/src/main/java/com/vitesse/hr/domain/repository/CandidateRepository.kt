package com.vitesse.hr.domain.repository

import com.vitesse.hr.domain.model.Candidate
import kotlinx.coroutines.flow.Flow

interface CandidateRepository {

    suspend fun upsert(candidate: Candidate)

    suspend fun delete(candidate: Candidate)

    suspend fun byId(id: Int): Candidate?

    fun all(): Flow<List<Candidate>>
}