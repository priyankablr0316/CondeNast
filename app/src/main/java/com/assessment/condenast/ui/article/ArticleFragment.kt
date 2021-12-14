package com.assessment.condenast.ui.article

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.condenast.AppConstants
import com.assessment.condenast.R
import com.assessment.condenast.databinding.FragmentArticleBinding
import com.assessment.condenast.ui.article.ArticleItemViewModel.ArticleItemViewModelListener
import com.assessment.condenast.ui.base.BaseFragment
import com.assessment.condenast.util.gone
import com.assessment.condenast.util.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment(override val layout: Int = R.layout.fragment_article) :
    BaseFragment<FragmentArticleBinding>(),
    ArticleItemViewModelListener {

    lateinit var articleAdapter: ArticleAdapter

    @Inject
    lateinit var articleViewModel: ArticleViewModel

    override fun onRetryClick() {
        articleViewModel.fetchArticles()
    }

    override fun onItemClick(item: ArticleDataItem) {
        val bundle = Bundle()
        bundle.putParcelable(
            AppConstants.ARTICLE,
            item
        )
        findNavController().navigate(R.id.action_articleFragment_to_articleDetailsFragment, bundle)
    }

    private val articleListObserver = Observer<List<ArticleDataItem>?> { articleList ->
        articleAdapter.addItems(articleList)
    }

    private val progressObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            binding.progressCircular.visible()
            binding.progressText.visible()
        } else {
            binding.progressCircular.gone()
            binding.progressText.gone()
        }
    }

    private val errorObserver = Observer<String> { message ->
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //fetching US top headlines news.
        articleViewModel.fetchArticles()
        articleAdapter = ArticleAdapter(arrayListOf(), this)
        articleViewModel.articleListLiveData.observe(this, articleListObserver)
        articleViewModel.errorLiveData.observe(this, errorObserver)
        articleViewModel.progressIndicator.observe(this, progressObserver)
    }

    private fun setUpRecyclerView() {
        binding.resultsBeanRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.resultsBeanRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.resultsBeanRecyclerView.adapter = articleAdapter
    }

    override fun onViewReady() {
        setUpRecyclerView()
        setHasOptionsMenu(false)
        val typeface = ResourcesCompat.getFont(requireActivity().applicationContext, R.font.regular)
        with(binding.toolbarTitle) {
            this.typeface = typeface
        }
    }

}