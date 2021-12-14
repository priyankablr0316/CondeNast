package com.assessment.condenast.data.remote

import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.ArticlesResponse
import com.assessment.condenast.data.remote.network.NewsApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages online news api data - Fetches data from online news api using contract and pass it to main AppDataManager which
 * is then accessed by ViewModels.
 * ArticleFragment -> ArticleViewModel -> AppDataManager - > NewsApiRepository - > NewsApiService (Authenticated)
 */
@Singleton
class NewsApiRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val apiKey: String
) : NewsApiDataSource {

    override suspend fun getArticles(country: String,category: String): Result<ArticlesResponse> {
        return try {
            val articlesResponse = newsApiService.getArticles(country,category,apiKey)
            Result.Success(articlesResponse)
        } catch (e: Exception) {
            Result.Error("Unable to fetch latest articles")
        }
    }
}