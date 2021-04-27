package com.cornershop.counterstest.presentation.adapters

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.domain.models.Counters

class CounterItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Counters>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Counters>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as CounterAdapter.ListViewHolder)
                .getItemDetails()
        }
        return null
    }
}