package com.vitesse.hr.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

//TODO Use mapper
@Entity
data class Candidate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    //TODO use date
    val dateOfBirth: LocalDate,
    val expectedSalary: Long?,
    val note: String,
    val isFavorite: Boolean,
    val photo: Uri?
)

class InvalidCandidateException(message: String): Exception(message)