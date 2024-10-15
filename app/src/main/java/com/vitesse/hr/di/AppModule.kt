package com.vitesse.hr.di

import android.app.Application
import androidx.room.Room
import com.vitesse.hr.data.currency.CurrencyApi
import com.vitesse.hr.data.datasource.CandidateDatabase
import com.vitesse.hr.data.repository.CandidateRepositoryImpl
import com.vitesse.hr.data.repository.CurrencyRepositoryImpl
import com.vitesse.hr.domain.repository.CandidateRepository
import com.vitesse.hr.domain.repository.CurrencyRepository
import com.vitesse.hr.domain.usecase.AddCandidate
import com.vitesse.hr.domain.usecase.DeleteCandidate
import com.vitesse.hr.domain.usecase.GetCandidate
import com.vitesse.hr.domain.usecase.GetCurrency
import com.vitesse.hr.domain.usecase.ListAll
import com.vitesse.hr.domain.usecase.ListFavorites
import com.vitesse.hr.domain.usecase.UseCases
import com.vitesse.hr.presentation.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL =
        "https://cdn.jsdelivr.net/"

    @Provides
    @Singleton
    fun database(app: Application) = Room.databaseBuilder(
        app,
        CandidateDatabase::class.java,
        CandidateDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun currencyApi(): CurrencyApi = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun currencyRepository(api: CurrencyApi): CurrencyRepository = CurrencyRepositoryImpl(api)

    @Provides
    @Singleton
    fun repository(db: CandidateDatabase): CandidateRepository = CandidateRepositoryImpl(db.dao())

    @Provides
    @Singleton
    fun appDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    @Singleton
    fun listUseCases(
        repository: CandidateRepository,
        currencyRepository: CurrencyRepository
    ) = UseCases(
        getCandidates = ListAll(repository),
        getFavorites = ListFavorites(repository),
        getCandidate = GetCandidate(repository),
        addCandidate = AddCandidate(repository),
        deleteCandidate = DeleteCandidate(repository),
        getCurrency = GetCurrency(currencyRepository)
    )
}