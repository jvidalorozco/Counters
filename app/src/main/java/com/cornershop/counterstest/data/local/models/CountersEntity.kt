package com.cornershop.counterstest.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "counters")
data class CountersEntity(
 @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "count")
    val count: Int?,

)



