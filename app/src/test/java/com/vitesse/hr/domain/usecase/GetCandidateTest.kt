package com.vitesse.hr.domain.usecase

import com.vitesse.hr.capture
import com.vitesse.hr.domain.repository.CandidateRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetCandidateTest {

    @Mock
    lateinit var repository: CandidateRepository

    @InjectMocks
    lateinit var useCases: GetCandidate

    @Captor
    lateinit var idCaptor: ArgumentCaptor<Int>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Given a candidate id, Then the repository get id is executed`() = runBlocking {

        //given
        val id = 1

        //when
        useCases.invoke(id)

        //then
        Mockito.verify(repository).byId(capture(idCaptor))

        assertEquals(idCaptor.value, id)
    }

}