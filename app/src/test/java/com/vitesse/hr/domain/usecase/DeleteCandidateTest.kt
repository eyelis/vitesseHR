package com.vitesse.hr.domain.usecase

import com.vitesse.hr.capture
import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.repository.CandidateRepository
import com.vitesse.hr.presentation.util.DateUtils.now
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DeleteCandidateTest {

    @Mock
    lateinit var repository: CandidateRepository

    @InjectMocks
    lateinit var useCases: DeleteCandidate

    @Captor
    lateinit var candidateCaptor: ArgumentCaptor<Candidate>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun  `Given a candidate, Then the repository delete is executed`() = runBlocking {

        //given
        val candidate = Candidate(
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

        //when
        useCases.invoke(candidate)

        //then
        Mockito.verify(repository).delete(capture(candidateCaptor))

        assertEquals(candidateCaptor.value, candidate)
    }

}