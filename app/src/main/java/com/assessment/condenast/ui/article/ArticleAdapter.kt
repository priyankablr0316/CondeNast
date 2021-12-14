package com.assessment.condenast.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.assessment.condenast.R
import com.assessment.condenast.databinding.ItemArticleViewBinding
import com.assessment.condenast.ui.article.ArticleItemViewModel.ArticleItemViewModelListener
import com.assessment.condenast.ui.base.BaseRecyclerViewAdapter
import com.assessment.condenast.ui.base.BaseViewHolder

/**
 * Displays list items for article list screen.
 */
class ArticleAdapter(items: MutableList<ArticleDataItem>, listener: ArticleItemViewModelListener) :
    BaseRecyclerViewAdapter<ArticleDataItem>(items, listener) {

    private lateinit var context: Context

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        return ArticleViewHolder(
            ItemArticleViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    inner class ArticleViewHolder(private val mBinding: ItemArticleViewBinding) :
        BaseViewHolder(mBinding.root) {
        override fun onBind(position: Int) {
            val article = items[position]
            mBinding.viewModel = ArticleItemViewModel(article) { itemListener.onItemClick(article) }
            val typeface = ResourcesCompat.getFont(context, R.font.bold)
            mBinding.authorTextView.typeface = typeface
            mBinding.executePendingBindings()
        }
    }


}