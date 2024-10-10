package com.vitesse.hr.data.repository

import com.vitesse.hr.data.currency.CurrencyApi
import com.vitesse.hr.domain.model.CurrencyResponse
import com.vitesse.hr.domain.repository.CurrencyRepository
import com.vitesse.hr.domain.util.Resource
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : CurrencyRepository {

    override suspend fun getRate(): Resource<CurrencyResponse> {
        return try {
            val response = api.getRate()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error("Error getting currency rate. Code : " + response.code() + " / " +  " Message : " + response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unexpected error getting currency rate")
        }
    }
}