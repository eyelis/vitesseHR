package com.vitesse.hr.presentation.edit.state

import android.net.Uri
import com.vitesse.hr.presentation.util.UiString
import com.vitesse.hr.presentation.validation.Digits
import com.vitesse.hr.presentation.validation.Email
import com.vitesse.hr.presentation.validation.Mandatory
import com.vitesse.hr.presentation.validation.Phone
import kotlinx.datetime.LocalDate

data class EditState(

    val id: Int? = null,

    @Mandatory
    val firstName: String = "",

    @Mandatory
    val lastName: String = "",

    @Mandatory
    @Phone
    val phoneNumber: String = "",

    @Mandatory
    @Email
    val email: String = "",

    @Mandatory
    val dateOfBirth: LocalDate? = null,

    @Digits
    val expectedSalary: String = "",

    val note: String = "",

    val isFavorite: Boolean = false,

    val photo: Uri? = null,

    val errors: Map<String, MutableList<UiString>> = emptyMap(),

    val isLoading: Boolean = true
)
