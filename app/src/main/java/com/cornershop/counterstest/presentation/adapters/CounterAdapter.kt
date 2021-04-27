package com.cornershop.counterstest.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.ItemCountersListBinding
import com.cornershop.counterstest.domain.models.Counters


class CounterAdapter internal constructor(
    private val listener : OnItemClickListener,
    private val postItems: List<Counters>
) :
    RecyclerView.Adapter<CounterAdapter.ListViewHolder>() {

    var tracker: SelectionTracker<Counters>? = null

    inner class ListViewHolder(private val itemBinding: ItemCountersListBinding) : RecyclerView.ViewHolder(itemBinding.root) {


        fun setCounter(counters: Counters,viewListener: View.OnClickListener, isActivated: Boolean = false) {
            with(itemBinding) {
                counter = counters
                show = isActivated
                clickListener = viewListener
                itemView.isActivated = isActivated
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Counters> =
            object : ItemDetailsLookup.ItemDetails<Counters>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Counters? = postItems[position]
            }
    }

    private fun createOnClickListener(counter: Counters): View.OnClickListener {
        return View.OnClickListener {
            when(it.id){
                R.id.btSum -> {
                    listener.onClickSum(counter)
                }
                R.id.btMinus -> {
                    listener.onClickMinus(counter)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = ItemCountersListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        tracker?.let {
            holder.setCounter(postItems[position], createOnClickListener(postItems[position]),it.isSelected(postItems[position]))
        }

    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    fun getItem(position: Int) = postItems[position]
    fun getPosition(id: String) = postItems.indexOfFirst { it.id == id }

    interface OnItemClickListener{
        fun onClickSum(counter: Counters)
        fun onClickMinus(counter: Counters)
    }


}

