package com.vitesse.hr.data.currency

import com.vitesse.hr.domain.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {

    @GET("npm/@fawazahmed0/currency-api@latest/v1/currencies/eur.json")
    suspend fun getRate(): Response<CurrencyResponse>
}