package com.vitesse.hr.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.vitesse.hr.data.datasource.CandidateDao
import com.vitesse.hr.data.datasource.CandidateDatabase
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.presentation.util.DateUtils.now
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CandidateDaoTest {

    private lateinit var database: CandidateDatabase
    private lateinit var dao: CandidateDao

    private lateinit var candidate: Candidate

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CandidateDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.dao()

        //given
        candidate = Candidate(
            id = 1,
            firstName = "firstName",
            lastName = "lastName",
            dateOfBirth = LocalDate.now(),
            email = "firstName.lastName@email.com",
            isFavorite = false,
            phoneNumber = "0600000000",
            expectedSalary = 5000,
            note = "note",
            photo = null
        )
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insert_and_retrieve_candidate() = runBlocking {

        //given


        //when
        dao.upsert(candidate)

        //and
        val found = dao.all().first()

        //then
        assertTrue(found.contains(candidate))
    }

    @Test
    fun insert_and_retrieve_candidate_by_id() = runBlocking {

        //when
        dao.upsert(candidate)

        //and
        val found = dao.byId(1)

        //then
        assertEquals(candidate, found)
    }

    @Test
    fun insert_update_and_retrieve_candidate() = runBlocking {

        //given
        val candidateUpdated = candidate.copy(firstName = "firstNameUpdated")

        //when
        dao.upsert(candidate)
        //and
        dao.upsert(candidateUpdated)
        //and
        val found = dao.all().first()

        //then
        assertEquals(1, found.size)
        assertFalse(found.contains(candidate))
        assertTrue(found.contains(candidateUpdated))
    }

    @Test
    fun insert_delete_candidate() = runBlocking {

        //when
        dao.upsert(candidate)
        //and
        dao.delete(candidate)
        //and
        val found = dao.all().first()

        //then
        assertTrue(found.isEmpty())
    }
}