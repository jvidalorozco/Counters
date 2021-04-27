package com.cornershop.counterstest.commons

import androidx.annotation.StringRes
import com.cornershop.counterstest.R
import java.net.UnknownHostException

internal object ExceptionHandler {

    @StringRes
    fun parse(t: Throwable): Int {
        return when (t) {
            is UnknownHostException -> R.string.connection_error_description
            else -> R.string.generic_error_description
        }
    }

}
