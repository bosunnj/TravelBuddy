package com.jenstine.travelKing.di

import com.jenstine.travelKing.data.repository.SeeRepository
import com.jenstine.travelKing.data.repository.SeeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SeeModule {

    @Binds
    @Singleton
    abstract fun bindSeeRepository(impl: SeeRepositoryImpl): SeeRepository
}

