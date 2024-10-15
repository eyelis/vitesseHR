package com.vitesse.hr.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class Candidate(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val dateOfBirth: LocalDate,
    val expectedSalary: Long?,
    val note: String,
    val isFavorite: Boolean,
    val photo: Uri?
)

class InvalidCandidateException(message: String): Exception(message)