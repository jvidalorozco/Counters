package com.cornershop.counterstest.data.remote


import com.cornershop.counterstest.domain.models.Counters
import okhttp3.internal.http.hasBody
import retrofit2.http.*

interface CountersApiService {

    @GET("counters")
    suspend fun getAllCounters(): List<Counters>

    @POST("counter")
    suspend fun createNewCounter(@Body counters: Counters): List<Counters>

    @POST("counter/inc")
    suspend fun incrementCounter(@Body counters: Counters): List<Counters>

    @POST("counter/dec")
    suspend fun decrementCounter(@Body counters: Counters): List<Counters>

    @HTTP(method = "DELETE", path = "counter", hasBody = true)
    suspend fun deleteCounter(@Body counters: Counters): List<Counters>
}