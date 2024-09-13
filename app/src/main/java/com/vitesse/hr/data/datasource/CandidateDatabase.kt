package com.vitesse.hr.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vitesse.hr.data.converter.Converters
import com.vitesse.hr.domain.model.Candidate

@Database(
    entities = [Candidate::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CandidateDatabase: RoomDatabase(){

    abstract fun dao(): CandidateDao

    companion object {
        const val DATABASE_NAME = "candidate_db"
    }
}