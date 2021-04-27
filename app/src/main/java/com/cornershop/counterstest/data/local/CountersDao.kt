package com.cornershop.counterstest.data.local

import androidx.room.*
import com.cornershop.counterstest.data.local.models.CountersEntity
import com.cornershop.counterstest.domain.models.Counters
import com.cornershop.counterstest.domain.models.*
import org.jetbrains.annotations.NotNull

@Dao
interface CountersDao {


    @Transaction
    @Query("SELECT * FROM counters")
    suspend fun getAllCounters(): List<Counters>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(counters: CountersEntity)


}