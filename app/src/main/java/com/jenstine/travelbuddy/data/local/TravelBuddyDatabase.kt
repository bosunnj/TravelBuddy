package com.jenstine.travelKing.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jenstine.travelKing.data.local.dao.JournalDao
import com.jenstine.travelKing.data.local.entity.JournalEntryEntity

@Database(entities = [JournalEntryEntity::class], version = 1, exportSchema = false)
abstract class TravelBuddyDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
}
