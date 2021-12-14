package com.assessment.condenast.ui.article

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleDataItem(
    val author: String?,
    val imageUrl: String?,
    val title: String?,
    val publishedDate: String?,
    val content: String?,
    val id: String?
) : Parcelable