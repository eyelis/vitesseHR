package com.vitesse.hr.data.repository

import com.vitesse.hr.data.datasource.CandidateDao
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.repository.CandidateRepository
import kotlinx.coroutines.flow.Flow

class CandidateRepositoryImpl(
    private val dao: CandidateDao
) : CandidateRepository {

    override suspend fun upsert(candidate: Candidate) {
        dao.upsert(candidate)
    }

    override suspend fun delete(candidate: Candidate) {
       dao.delete(candidate)
    }

    override suspend fun byId(id: Int): Candidate? {
        return dao.byId(id)
    }

    override fun all(): Flow<List<Candidate>> {
        return dao.all()
    }
}