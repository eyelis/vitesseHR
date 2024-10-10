package com.vitesse.hr.presentation.edit.state

import android.net.Uri
import com.vitesse.hr.presentation.edit.validation.Digits
import com.vitesse.hr.presentation.edit.validation.Email
import com.vitesse.hr.presentation.edit.validation.Mandatory
import com.vitesse.hr.presentation.edit.validation.Phone
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

    val errors: Map<String, MutableList<String>> = emptyMap(),

    val isLoading: Boolean = true
)
