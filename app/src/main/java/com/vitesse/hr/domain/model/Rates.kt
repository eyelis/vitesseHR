package com.vitesse.hr.domain.model

import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("gbp")
    val quote: Double,
)
