package com.assessment.condenast.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assessment.condenast.data.AppDataManager
import com.assessment.condenast.data.local.DbRepository
import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.ArticlesResponse
import com.assessment.condenast.data.model.db.Article
import com.assessment.condenast.data.remote.NewsApiRepository
import com.assessment.condenast.data.remote.NewsInfoApiRepository
import com.assessment.condenast.helper.TestCoroutineRule
import com.assessment.condenast.ui.article.ArticleDataItem
import com.assessment.condenast.ui.article.ArticleViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ArticleViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var newsApiRepository: NewsApiRepository

    @Mock
    private lateinit var newsInfoApiRepository: NewsInfoApiRepository

    @Mock
    private lateinit var dbRepository: DbRepository

    @Mock
    private lateinit var articleListObserver: Observer<List<ArticleDataItem>>

    @Mock
    private lateinit var errorLiveDataObserver: Observer<String>

    private lateinit var appDataManager: AppDataManager

    @Before
    fun setUp() {
        appDataManager = AppDataManager(newsApiRepository, newsInfoApiRepository, dbRepository)
    }

    @Test
    fun givenArticlesResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val country = "us"
            val category = "business"
            val articlesResponse = ArticlesResponse()
            articlesResponse.articles = listOf()
            doReturn(listOf<Article>()).`when`(appDataManager.getDbRepository()).allArticles()
            doReturn(Result.Success(articlesResponse)).`when`(appDataManager.getNewsApiRepository()).getArticles(country,category)
            val viewModel = ArticleViewModel(application, appDataManager)
            viewModel.articleListLiveData.observeForever(articleListObserver)

            //then
            viewModel.fetchArticles(country,category)

            //return
            verify(appDataManager.getNewsApiRepository()).getArticles(country, category)
            verify(articleListObserver).onChanged(listOf())

            //clear
            viewModel.articleListLiveData.removeObserver(articleListObserver)
        }
    }

    @Test
    fun givenArticlesResponse200_whenFetch_shouldReturnSuccess_withDBInsert() {
        testCoroutineRule.runBlockingTest {
            //when
            val country = "us"
            val category = "business"
            val dummy = "dummy"
            val source = ArticlesResponse.Source(dummy,dummy)
            val articlesResponse = ArticlesResponse()
            doReturn(listOf<Article>()).`when`(appDataManager.getDbRepository()).allArticles()
            val articleList = listOf(ArticlesResponse.Article(source,dummy,dummy,dummy,dummy,dummy,dummy,dummy))
            articlesResponse.articles = articleList
            doReturn(Result.Success(articlesResponse)).`when`(appDataManager.getNewsApiRepository()).getArticles(country,category)
            val viewModel = ArticleViewModel(application, appDataManager)

            //then
            viewModel.fetchArticles(country,category)

            //return
            verify(appDataManager.getNewsApiRepository()).getArticles(country, category)
            verify(dbRepository).insertArticle(Article(dummy,dummy,dummy,dummy,dummy,dummy,dummy))
        }
    }

    @Test
    fun givenArticlesResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            //when
            val country = "us"
            val category = "business"
            val errorMessage = "Error Message For You"
            doReturn(listOf<Article>()).`when`(appDataManager.getDbRepository()).allArticles()
            doReturn(Result.Error(errorMessage)).`when`(appDataManager.getNewsApiRepository()).getArticles(country,category)
            val viewModel = ArticleViewModel(application, appDataManager)
            viewModel.errorLiveData.observeForever(errorLiveDataObserver)

            //then
            viewModel.fetchArticles(country,category)

            //return
            verify(appDataManager.getNewsApiRepository()).getArticles(country, category)
            verify(errorLiveDataObserver).onChanged(errorMessage)

            //clear
            viewModel.errorLiveData.removeObserver(errorLiveDataObserver)
        }
    }
}