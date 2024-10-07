package com.vitesse.hr.domain.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("eur")
    val rates: Rates
)
