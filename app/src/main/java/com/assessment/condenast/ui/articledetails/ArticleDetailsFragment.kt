package com.assessment.condenast.ui.articledetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.assessment.condenast.AppConstants
import com.assessment.condenast.R
import com.assessment.condenast.databinding.FragmentArticleDetailsBinding
import com.assessment.condenast.ui.article.ArticleDataItem
import com.assessment.condenast.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article_details.*
import javax.inject.Inject

@AndroidEntryPoint
class ArticleDetailsFragment(override val layout: Int = R.layout.fragment_article_details) :
    BaseFragment<FragmentArticleDetailsBinding>() {

    private var articleDataItem: ArticleDataItem? = null

    @Inject
    lateinit var articleDetailsViewModel: ArticleDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            articleDataItem = arguments?.getParcelable(AppConstants.ARTICLE)
        }
        articleDetailsViewModel.likeLiveData.observe(this, likesObserver)
        articleDetailsViewModel.commentsLiveData.observe(this, commentsObserver)
        articleDetailsViewModel.errorLiveData.observe(this, errorObserver)
        articleDetailsViewModel.progressIndicator.observe(this, progressObserver)
        articleDataItem?.id?.let {
            articleDetailsViewModel.fetchLikesForArticles(it)
            articleDetailsViewModel.fetchCommentsForArticles(it)
        }
    }

    private val errorObserver = Observer<String> { message ->
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private val progressObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private val likesObserver = Observer<Int> { likes ->
        binding.likeNumber.visibility = View.VISIBLE
        binding.likeNumber.text = String.format(getString(R.string.likes), likes)
    }

    private val commentsObserver = Observer<Int> { comments ->
        binding.commentNumber.visibility = View.VISIBLE
        binding.commentNumber.text = String.format(getString(R.string.comments), comments)
    }

    override fun onViewReady() {
        setUp()
        val typefaceBold =
            ResourcesCompat.getFont(requireActivity().applicationContext, R.font.bold)
        titleTextView.run {
            titleTextView.run { titleTextView.typeface = typefaceBold }
        }
        val typefaceRegular =
            ResourcesCompat.getFont(requireActivity().applicationContext, R.font.regular)
        authorTextView.run {
            authorTextView.run { authorTextView.typeface = typefaceRegular }
        }
        contentTextView.run {
            contentTextView.run { contentTextView.typeface = typefaceRegular }
        }
        dateTextView.run {
            dateTextView.run { dateTextView.typeface = typefaceRegular }
        }
    }

    private fun setUp() {
        setUpToolbar()
        setArticle()
        setUpView()
    }

    private fun setArticle() {
        if (articleDataItem != null) {
            binding.article = articleDataItem
        }
    }

    private fun setUpView() {
        binding.likeIcon.setOnClickListener {
            Toast.makeText(activity, "Feature is yet to be implemented", Toast.LENGTH_SHORT).show()
        }
        binding.commentIcon.setOnClickListener {
            Toast.makeText(activity, "Feature is yet to be implemented", Toast.LENGTH_SHORT).show()
        }
        binding.shareIcon.setOnClickListener {
            Toast.makeText(activity, "Feature is yet to be implemented", Toast.LENGTH_SHORT).show()
        }
        binding.bookmarkIcon.setOnClickListener {
            Toast.makeText(activity, "Feature is yet to be implemented", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            if (activity != null) {
                activity?.onBackPressed()
            }
        }
    }

}