package com.vitesse.hr.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vitesse.hr.domain.model.Candidate
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidateDao {

    @Upsert
    suspend fun upsert(candidate: Candidate)

    @Delete
    suspend fun delete(candidate: Candidate)

    @Query("SELECT * FROM candidate WHERE id = :id")
    suspend fun byId(id: Int): Candidate?

    @Query("SELECT * FROM candidate ORDER BY firstName, lastName ASC")
    fun all(): Flow<List<Candidate>>

}