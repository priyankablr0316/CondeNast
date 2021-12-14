package com.assessment.condenast.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base view holder which gives a callback to bind the data with view.
 */
abstract class BaseViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(position: Int)
}