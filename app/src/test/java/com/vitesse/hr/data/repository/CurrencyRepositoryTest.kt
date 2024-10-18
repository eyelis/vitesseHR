package com.vitesse.hr.data.repository

import com.vitesse.hr.data.currency.CurrencyApi
import com.vitesse.hr.domain.util.Resource
import com.vitesse.hr.domain.util.Resource.Success
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class CurrencyRepositoryTest {

    private var mockWebServer = MockWebServer()

    private lateinit var api: CurrencyApi

    @Before
    fun setup() {
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Given gbp currency is provided, When the currency API is called, Then the currency rate is retrieved`() =
        runBlocking {
            val jsonResponse = "{\"date\": \"2024-10-08\",\"eur\": {\"gbp\": 0.83, \"usd\": 1.09}}"

            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(jsonResponse)

            mockWebServer.enqueue(response)

            val repository = CurrencyRepositoryImpl(api)
            val resource = repository.getRate()

            assertTrue(resource is Success)
            assertEquals(0.83, resource.data!!.rates.quote, 0.0)

        }

    @Test
    fun `Given an empty response, When the currency API is called, Then an error message is retrieved`() =
        runBlocking {
            val jsonResponse = "{}"

            val response = MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                .setBody(jsonResponse)

            mockWebServer.enqueue(response)

            val repository = CurrencyRepositoryImpl(api)
            val resource = repository.getRate()

            assertTrue(resource is Resource.Error)
            assertTrue(resource.message!!.isNotEmpty())
        }
}