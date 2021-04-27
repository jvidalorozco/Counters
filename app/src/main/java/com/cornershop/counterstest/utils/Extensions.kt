package com.cornershop.counterstest.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

internal inline fun <reified T : Activity> Context.startActivityNew(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}