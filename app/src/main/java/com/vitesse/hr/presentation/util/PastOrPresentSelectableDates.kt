package com.vitesse.hr.presentation.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import com.vitesse.hr.presentation.util.DateUtils.now
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year <= LocalDate.now().year
    }
}