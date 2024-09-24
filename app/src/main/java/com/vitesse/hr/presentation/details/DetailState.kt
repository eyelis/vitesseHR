package com.vitesse.hr.presentation.details

import android.net.Uri

data class DetailState(

    val id: Int = -1,

    val firstName: String = "",

    val lastName: String = "",

    val phoneNumber: String = "",

    val email: String = "",

    val dateOfBirth: String = "",

    val age: String = "",

    val expectedSalary: String = "",

    val note: String = "",

    val isFavorite: Boolean = false,

    val photo: Uri? = null
)