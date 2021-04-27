package com.cornershop.counterstest.helpers


import com.cornershop.counterstest.helpers.Constants.COUNTER_DECREMENT_URL
import com.cornershop.counterstest.helpers.Constants.COUNTER_INCREMENT_URL
import com.cornershop.counterstest.helpers.Constants.COUNTER_LIST_URL
import java.net.HttpURLConnection
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
class CounterRequestDispatcher
    : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            COUNTER_LIST_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/counter_list_response.json"))
            }
            COUNTER_INCREMENT_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(
                        getJson("json/character_search_no_match.json")
                    )
            }
            COUNTER_DECREMENT_URL -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                    .setBody(getJson("json/not_found.json"))
            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")
        }
    }

}