package com.assessment.condenast.data.remote

import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.CommentsResponse
import com.assessment.condenast.data.model.api.LikesResponse

/**
 * Contract defined to access news info api's.
 */
interface NewsInfoApiDataSource {

    suspend fun getLikesForArticle(articleId: String): Result<LikesResponse>

    suspend fun getCommentsForArticle(articleId: String): Result<CommentsResponse>
}