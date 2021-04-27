package com.cornershop.counterstest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cornershop.counterstest.data.local.models.CountersEntity

@Database(entities = [CountersEntity::class], version = 1, exportSchema = false)
abstract class CountersDatabase : RoomDatabase() {
    abstract fun countersDao(): CountersDao
}
