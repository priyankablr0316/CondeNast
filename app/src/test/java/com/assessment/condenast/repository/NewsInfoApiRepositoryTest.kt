package com.assessment.condenast.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.CommentsResponse
import com.assessment.condenast.data.model.api.LikesResponse
import com.assessment.condenast.data.remote.NewsInfoApiRepository
import com.assessment.condenast.data.remote.network.NewsInfoApiService
import com.assessment.condenast.helper.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsInfoApiRepositoryTest {


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var newsInfoApiRepository: NewsInfoApiRepository

    @Mock
    private lateinit var  newsInfoApiService: NewsInfoApiService


    @Before
    fun setUp() {
        newsInfoApiRepository = NewsInfoApiRepository(newsInfoApiService)
    }

    @Test
    fun givenLikesResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "articleId"
            val likesResponse = LikesResponse(1)
            doReturn(likesResponse).`when`(newsInfoApiService).getLikesForArticle(articleId)

            //then
            val result = newsInfoApiRepository.getLikesForArticle(articleId)

            //return
            Assert.assertEquals(result, Result.Success(likesResponse))

        }
    }

    @Test
    fun givenLikesResponseError_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "articleId"
            val errorMessage = "Unable to fetch likes"
            doReturn(Result.Error(errorMessage))
                .`when`(newsInfoApiService).getLikesForArticle(articleId)

            //then
            val result = newsInfoApiRepository.getLikesForArticle(articleId)

            //return
            Assert.assertEquals(result, Result.Error(errorMessage))
        }
    }

    @Test
    fun givenCommentsResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "articleId"
            val commentsResponse = CommentsResponse(1)
            doReturn(commentsResponse).`when`(newsInfoApiService).getCommentsForArticle(articleId)

            //then
            val result = newsInfoApiRepository.getCommentsForArticle(articleId)

            //return
            Assert.assertEquals(result, Result.Success(commentsResponse))

        }
    }

    @Test
    fun givenCommentsResponseError_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val articleId = "articleId"
            val errorMessage = "Unable to fetch comments"
            doReturn(Result.Error(errorMessage))
                .`when`(newsInfoApiService).getCommentsForArticle(articleId)

            //then
            val result = newsInfoApiRepository.getCommentsForArticle(articleId)

            //return
            Assert.assertEquals(result, Result.Error(errorMessage))
        }
    }

}