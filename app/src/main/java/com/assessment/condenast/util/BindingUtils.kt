package com.assessment.condenast.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.assessment.condenast.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Utils to display the details on the UI.
 */
object BindingUtils {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .apply(RequestOptions.centerCropTransform())
            .into(imageView)
    }
}