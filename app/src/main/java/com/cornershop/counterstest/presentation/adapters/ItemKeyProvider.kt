package com.cornershop.counterstest.presentation.adapters

import androidx.recyclerview.selection.ItemKeyProvider
import com.cornershop.counterstest.domain.models.Counters

class ItemKeyProvider(private val adapter: CounterAdapter) :
    ItemKeyProvider<Counters>(SCOPE_CACHED) {
    override fun getKey(position: Int): Counters? {
        return adapter.getItem(position)
    }

    override fun getPosition(key: Counters): Int {
        return adapter.getPosition(key.id)
    }
}