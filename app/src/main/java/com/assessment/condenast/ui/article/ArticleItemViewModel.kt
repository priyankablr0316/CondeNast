package com.assessment.condenast.ui.article

import androidx.databinding.ObservableField
import com.assessment.condenast.ui.base.BaseItemListener

/**
 * Not an actual Android view model but helper used for data binding and onclick operation.
 */
class ArticleItemViewModel(
    article: ArticleDataItem,
    private val onItemClick: () -> Unit
) {
    val imageUrl: ObservableField<String?> = ObservableField(article.imageUrl)
    val title: ObservableField<String?> = ObservableField(article.title)
    val author: ObservableField<String?> = ObservableField(article.author)
    val publishedDate: ObservableField<String?> = ObservableField(article.publishedDate)

    fun onItemClick() = onItemClick.invoke()

    interface ArticleItemViewModelListener : BaseItemListener<ArticleDataItem>
}