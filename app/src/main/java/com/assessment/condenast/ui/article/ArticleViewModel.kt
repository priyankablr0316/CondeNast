package com.assessment.condenast.ui.article

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assessment.condenast.data.AppDataManager
import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.ArticlesResponse
import com.assessment.condenast.data.model.db.Article
import com.assessment.condenast.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is used to fetch articles from remote saves it in DB and then UI is powered up from DB.
 * Updates DB with latest news all the time.
 */
class ArticleViewModel @Inject constructor(
    application: Application,
    private val appDataManager: AppDataManager
) : BaseViewModel(application) {

    private val articleListMutableLiveData = MutableLiveData<List<ArticleDataItem>>()
    val articleListLiveData: LiveData<List<ArticleDataItem>> get() = articleListMutableLiveData

    private val articlesLiveData: MutableList<ArticleDataItem> = mutableListOf()

    fun fetchArticles(country: String = "us", category: String = "business") {
        viewModelScope.launch {
            mutableProgressIndicator.value = true
            fetchDataFromDb()
            when (val result =
                appDataManager.getNewsApiRepository().getArticles(country, category)) {
                is Result.Success<ArticlesResponse> -> {
                    result.data.articles.let { it?.let { listItem -> mapArticlesDataItem(listItem) } }
                    mutableProgressIndicator.value = false
                }
                is Result.Error -> {
                    mutableProgressIndicator.value = false
                    errorMutableLiveData.value = result.message
                }
            }
        }
    }

    private suspend fun fetchDataFromDb() {
        val articles: List<Article> = appDataManager.getDbRepository().allArticles()
        if (articles.isNotEmpty()) {
            for (articleDataItem in articles) {
                val article = ArticleDataItem(
                    articleDataItem.author,
                    articleDataItem.imageUrl,
                    articleDataItem.title,
                    articleDataItem.publishedDate,
                    articleDataItem.content,
                    articleDataItem.articleId
                )
                articlesLiveData.add(article)
            }
            notifyDataAdded()
        }
    }

    private fun mapArticlesDataItem(articles: List<ArticlesResponse.Article>) {
        for (articleDataItem in articles) {
            val article = ArticleDataItem(
                articleDataItem.author,
                articleDataItem.urlToImage,
                articleDataItem.title,
                articleDataItem.publishedAt,
                articleDataItem.content,
                articleDataItem.source?.id
            )
            insertArticle(article)
            articlesLiveData.add(article)
        }
        notifyDataAdded()
    }

    private fun notifyDataAdded() {
        articleListMutableLiveData.value = articlesLiveData
        mutableProgressIndicator.value = false
    }

    private fun insertArticle(articleDataItem: ArticleDataItem) {
        viewModelScope.launch {
            appDataManager.getDbRepository().insertArticle(
                Article(
                    articleDataItem.title!!,
                    articleDataItem.author,
                    articleDataItem.imageUrl,
                    articleDataItem.title,
                    articleDataItem.publishedDate,
                    articleDataItem.content,
                    articleDataItem.id
                )
            )
        }
    }

}