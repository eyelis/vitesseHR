package com.vitesse.hr.domain.usecase

import com.vitesse.hr.domain.model.CurrencyResponse
import com.vitesse.hr.domain.model.Rates
import com.vitesse.hr.domain.repository.CurrencyRepository
import com.vitesse.hr.domain.util.Resource.Error
import com.vitesse.hr.domain.util.Resource.Success
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetCurrencyTest {

    @Mock
    lateinit var repository: CurrencyRepository

    @InjectMocks
    lateinit var useCases: GetCurrency

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun convert_currency_success() = runBlocking {

        //given
        val response = Success(CurrencyResponse(rates = Rates(quote = 0.888888)))

        Mockito.`when`(repository.getRate()).thenReturn(response)

        //when
        val resource = useCases.invoke(2)

        //then
        assertEquals(1.78, resource.data!!, 0.0)

    }

    @Test
    fun convert_currency_error() = runBlocking {

        //given
        val response = Error<CurrencyResponse>("Wierd")

        Mockito.`when`(repository.getRate()).thenReturn(response)

        //when
        val resource = useCases.invoke(2)

        //then
        assertTrue(resource is Error)
        assertTrue(resource.message!!.isNotEmpty())
        assertTrue(resource.message!!.contains("Wierd"))
    }


}