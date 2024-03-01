package com.example.shemajamebelin10.di

import com.example.shemajamebelin10.data.common.HandleResponse
import com.example.shemajamebelin10.data.repository.AccountsRepositoryImpl
import com.example.shemajamebelin10.data.repository.CourseRepositoryImpl
import com.example.shemajamebelin10.data.service.AccountApiService
import com.example.shemajamebelin10.data.service.CurrencyService
import com.example.shemajamebelin10.domain.repository.AccountsRepository
import com.example.shemajamebelin10.domain.repository.CourseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideAccountsRepository(
        accountApiService: AccountApiService,
        handleResponse: HandleResponse
    ): AccountsRepository =
        AccountsRepositoryImpl(
            apiService = accountApiService,
            handleResponse = handleResponse,
        )

    @Provides
    @Singleton
    fun provideCourseRepository(handleResponse: HandleResponse, currencyService: CurrencyService) : CourseRepository {
        return CourseRepositoryImpl(handleResponse = handleResponse, currencyService = currencyService)
    }
}