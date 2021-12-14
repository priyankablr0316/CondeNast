package com.assessment.condenast.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.ArticlesResponse
import com.assessment.condenast.data.remote.NewsApiRepository
import com.assessment.condenast.data.remote.network.NewsApiService
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
class NewsNewsApiRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var newsApiRepository: NewsApiRepository

    @Mock
    private lateinit var  newsApiService: NewsApiService


    @Before
    fun setUp() {
        newsApiRepository = NewsApiRepository(newsApiService,"")
    }

    @Test
    fun givenArticlesResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val country = "us"
            val category = "business"
            val articlesResponse = ArticlesResponse()
            doReturn(articlesResponse).`when`(newsApiService).getArticles(country,category,"")

            //then
            val result = newsApiRepository.getArticles(country,category)

            //return
            Assert.assertEquals(result, Result.Success(articlesResponse))

        }
    }

    @Test
    fun givenArticlesResponseError_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            //when
            val country = "us"
            val category = "business"
            val errorMessage = "Unable to fetch latest articles"
            doReturn(Result.Error(errorMessage)).`when`(newsApiService).getArticles(country,category,"")

            //then
            val result = newsApiRepository.getArticles(country,category)

            //return
            Assert.assertEquals(result, Result.Error(errorMessage))
        }
    }
}