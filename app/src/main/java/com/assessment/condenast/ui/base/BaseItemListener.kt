package com.assessment.condenast.ui.base

/**
 * Base Item click listeners.
 */
interface BaseItemListener<T> {
    /**
     * Callback when item is clicked.
     */
    fun onItemClick(item: T)

    /**
     * Callback when retry button is clicked.
     */
    fun onRetryClick()
}