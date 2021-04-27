package com.cornershop.counterstest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Counters(
        val id: String,
        val title: String,
        val count: Int
) : Parcelable
