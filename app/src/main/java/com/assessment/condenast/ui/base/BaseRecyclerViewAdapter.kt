package com.assessment.condenast.ui.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

/**
 * Base adapter which reduces the overhead of adding and notifying data in all the adapters.
 */
@SuppressLint("NotifyDataSetChanged")
abstract class BaseRecyclerViewAdapter<T>(
    val items: MutableList<T>,
    val itemListener: BaseItemListener<T>
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun addItems(items: List<T>?) {
        items?.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }

    fun clearItems() {
        items.clear()
    }
}