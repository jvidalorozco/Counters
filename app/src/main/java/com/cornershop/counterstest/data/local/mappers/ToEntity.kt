package com.cornershop.counterstest.data.local.mappers

import com.cornershop.counterstest.data.local.models.CountersEntity
import com.cornershop.counterstest.domain.models.Counters

internal fun Counters.toEntity(): CountersEntity {
    return CountersEntity(
      id = id,
      title = title,
      count = count
    )
}