package com.assessment.condenast.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.assessment.condenast.data.AppDataManager
import com.assessment.condenast.data.local.DbRepository
import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.CommentsResponse
import com.assessment.condenast.data.model.api.LikesResponse
import com.assessment.condenast.data.remote.NewsApiRepository
import com.assessment.condenast.data.remote.NewsInfoApiRepository
import com.assessment.condenast.helper.TestCoroutineRule
import com.assessment.condenast.ui.articledetails.ArticleDetailsViewModel
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
class ArticleDetailsViewModelTest {

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
    private lateinit var likesObserver: Observer<Int>

    @Mock
    private lateinit var commentsObserver: Observer<Int>

    @Mock
    private lateinit var errorLiveDataObserver: Observer<String>

    private lateinit var appDataManager: AppDataManager

    @Before
    fun setUp() {
        appDataManager = AppDataManager(newsApiRepository, newsInfoApiRepository, dbRepository)
    }

    @Test
    fun givenLikesResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "test"
            val likesResponse = LikesResponse(1)
            doReturn(Result.Success(likesResponse)).`when`(appDataManager.getNewInfoApiRepository()).getLikesForArticle(articleId)
            val viewModel = ArticleDetailsViewModel(application, appDataManager)
            viewModel.likeLiveData.observeForever(likesObserver)

            //then
            viewModel.fetchLikesForArticles(articleId)

            //return
            verify(appDataManager.getNewInfoApiRepository()).getLikesForArticle(articleId)
            verify(likesObserver).onChanged(1)

            //clear
            viewModel.likeLiveData.removeObserver(likesObserver)
        }
    }

    @Test
    fun givenLikesResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "test"
            val errorMessage = "Error Message For You"
            doReturn(Result.Error(errorMessage)).`when`(appDataManager.getNewInfoApiRepository()).getLikesForArticle(articleId)
            val viewModel = ArticleDetailsViewModel(application, appDataManager)
            viewModel.errorLiveData.observeForever(errorLiveDataObserver)

            //then
            viewModel.fetchLikesForArticles(articleId)

            //return
            verify(appDataManager.getNewInfoApiRepository()).getLikesForArticle(articleId)
            verify(errorLiveDataObserver).onChanged(errorMessage)

            //clear
            viewModel.errorLiveData.removeObserver(errorLiveDataObserver)
        }
    }

    @Test
    fun givenCommentsResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "test"
            val commentsResponse = CommentsResponse(1)
            doReturn(Result.Success(commentsResponse)).`when`(appDataManager.getNewInfoApiRepository()).getCommentsForArticle(articleId)
            val viewModel = ArticleDetailsViewModel(application, appDataManager)
            viewModel.commentsLiveData.observeForever(commentsObserver)

            //then
            viewModel.fetchCommentsForArticles(articleId)

            //return
            verify(appDataManager.getNewInfoApiRepository()).getCommentsForArticle(articleId)
            verify(commentsObserver).onChanged(1)

            //clear
            viewModel.commentsLiveData.removeObserver(commentsObserver)
        }
    }

    @Test
    fun givenCommentsResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "test"
            val errorMessage = "Error Message For You"
            doReturn(Result.Error(errorMessage)).`when`(appDataManager.getNewInfoApiRepository()).getCommentsForArticle(articleId)
            val viewModel = ArticleDetailsViewModel(application, appDataManager)
            viewModel.errorLiveData.observeForever(errorLiveDataObserver)

            //then
            viewModel.fetchCommentsForArticles(articleId)

            //return
            verify(appDataManager.getNewInfoApiRepository()).getCommentsForArticle(articleId)
            verify(errorLiveDataObserver).onChanged(errorMessage)

            //clear
            viewModel.errorLiveData.removeObserver(errorLiveDataObserver)
        }
    }
}