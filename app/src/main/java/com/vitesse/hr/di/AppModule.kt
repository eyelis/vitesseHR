package com.vitesse.hr.di

import android.app.Application
import androidx.room.Room
import com.vitesse.hr.data.datasource.CandidateDatabase
import com.vitesse.hr.data.repository.CandidateRepositoryImpl
import com.vitesse.hr.domain.repository.CandidateRepository
import com.vitesse.hr.domain.usecase.AddCandidate
import com.vitesse.hr.domain.usecase.DeleteCandidate
import com.vitesse.hr.domain.usecase.GetCandidate
import com.vitesse.hr.domain.usecase.ListAll
import com.vitesse.hr.domain.usecase.ListFavorites
import com.vitesse.hr.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun database(app: Application) : CandidateDatabase {
        return Room.databaseBuilder(
            app,
            CandidateDatabase::class.java,
            CandidateDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun repository(db: CandidateDatabase) : CandidateRepository {
        return CandidateRepositoryImpl(db.dao())
    }

    @Provides
    @Singleton
    fun listUseCases(repository: CandidateRepository) : UseCases {
        return UseCases(
            getCandidates = ListAll(repository),
            getFavorites = ListFavorites(repository),
            getCandidate = GetCandidate(repository),
            addCandidate = AddCandidate(repository),
            deleteCandidate = DeleteCandidate(repository)
        )
    }
}