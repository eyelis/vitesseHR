package com.vitesse.hr.domain.usecase

import com.vitesse.hr.domain.repository.CurrencyRepository
import com.vitesse.hr.domain.util.Resource
import java.math.RoundingMode
import kotlin.math.round

class GetCurrency(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(amount: Long): Resource<Double> {
       return when (val ratesResponse = repository.getRate()) {
            is Resource.Error -> Resource.Error(ratesResponse.message ?: "Conversion fails")
            is Resource.Success -> {
                val rates = ratesResponse.data!!.rates
                val rate = rates.quote
                val convertedCurrency = (amount * rate)
                    .toBigDecimal()
                    .setScale(2, RoundingMode.UP)
                    .toDouble()
                Resource.Success(convertedCurrency)
            }
        }
    }
}