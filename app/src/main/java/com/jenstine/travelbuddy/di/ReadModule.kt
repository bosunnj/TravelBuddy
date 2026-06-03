package com.jenstine.travelbuddy.di

import com.jenstine.travelbuddy.data.repository.ReadRepository
import com.jenstine.travelbuddy.data.repository.ReadRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReadModule {

    @Binds
    @Singleton
    abstract fun bindReadRepository(impl: ReadRepositoryImpl): ReadRepository
}
