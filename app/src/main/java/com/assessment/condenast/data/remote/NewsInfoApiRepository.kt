package com.assessment.condenast.data.remote

import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.CommentsResponse
import com.assessment.condenast.data.model.api.LikesResponse
import com.assessment.condenast.data.remote.network.NewsInfoApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages online news info api data - Fetches data from online news info api using contract and pass it to main AppDataManager which
 * is then accessed by ViewModels.
 * ArticleDetailsFragment - > ArticleDetailsViewModel -> AppDataManager - > NewsInfoApiRepository - > NewsInfoApiService (Un-authenticated)
 */
@Singleton
class NewsInfoApiRepository @Inject constructor(
    private val newsInfoApiService: NewsInfoApiService,
) : NewsInfoApiDataSource {

    override suspend fun getLikesForArticle(articleId: String): Result<LikesResponse> {
        return try {
            val articlesResponse = newsInfoApiService.getLikesForArticle(articleId)
            Result.Success(articlesResponse)
        } catch (e: Exception) {
            Result.Error("Unable to fetch likes")
        }
    }

    override suspend fun getCommentsForArticle(articleId: String): Result<CommentsResponse> {
        return try {
            val articlesResponse = newsInfoApiService.getCommentsForArticle(articleId)
            Result.Success(articlesResponse)
        } catch (e: Exception) {
            Result.Error("Unable to fetch comments")
        }
    }
}