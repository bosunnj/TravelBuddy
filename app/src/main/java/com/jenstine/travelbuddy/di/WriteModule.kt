package com.jenstine.travelKing.di

import android.content.Context
import androidx.room.Room
import com.jenstine.travelKing.data.local.TravelBuddyDatabase
import com.jenstine.travelKing.data.local.dao.JournalDao
import com.jenstine.travelKing.data.repository.WriteRepository
import com.jenstine.travelKing.data.repository.WriteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WriteModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TravelBuddyDatabase =
        Room.databaseBuilder(context, TravelBuddyDatabase::class.java, "travelking.db")
            .build()

    @Provides
    fun provideJournalDao(db: TravelBuddyDatabase): JournalDao = db.journalDao()

    @Provides
    @Singleton
    fun provideWriteRepository(dao: JournalDao): WriteRepository = WriteRepositoryImpl(dao)
}
