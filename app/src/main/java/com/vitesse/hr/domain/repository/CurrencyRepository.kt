package com.vitesse.hr.domain.repository

import com.vitesse.hr.domain.model.CurrencyResponse
import com.vitesse.hr.domain.util.Resource

interface CurrencyRepository {
    suspend fun getRate(): Resource<CurrencyResponse>
}