package com.vitesse.hr.presentation.edit.state

import android.net.Uri
import com.vitesse.hr.presentation.edit.validation.Digits
import com.vitesse.hr.presentation.edit.validation.Email
import com.vitesse.hr.presentation.edit.validation.Mandatory
import com.vitesse.hr.presentation.edit.validation.PastDate
import com.vitesse.hr.presentation.edit.validation.Phone

data class EditState(

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
    @PastDate(format = "dd/MM/yyyy")
    val dateOfBirth: String = "",

    @Digits
    val expectedSalary: String = "",

    val note: String = "",

    val isFavorite: Boolean = false,

    val photo: Uri? = null,

    val errors: Map<String, MutableList<String>> = emptyMap()
)
