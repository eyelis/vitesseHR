package com.vitesse.hr.domain.usecase

import com.vitesse.hr.domain.model.Candidate
import com.vitesse.hr.domain.repository.CandidateRepository
import com.vitesse.hr.presentation.util.DateUtils.now
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(Parameterized::class)

class ListAllTest(
    private val searchText: String,
    private val expected: Boolean
) {

    @Mock
    lateinit var repository: CandidateRepository

    @InjectMocks
    lateinit var useCases: ListAll

    lateinit var candidate: Candidate

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

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
    @Test
    fun `Given an existing candidate match a search text, Then the result should contains the candidate`() = runBlocking {

        //given
        Mockito.`when`(repository.all()).thenReturn(listOf(listOf(candidate)).asFlow())

        //when
        val actual = useCases.invoke(searchText).first()

        //then
        assertEquals(expected, actual.contains(candidate))
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: Given searchText={0}, Then matching candidate is found={1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("first", true),
                arrayOf("LAST", true),
                arrayOf("Rst", true),
                arrayOf("as", true),
                arrayOf("", true),
                arrayOf("toto", false)
            )
        }
    }

}