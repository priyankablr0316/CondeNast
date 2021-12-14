package com.assessment.condenast.data.remote.network

import com.assessment.condenast.data.model.api.CommentsResponse
import com.assessment.condenast.data.model.api.LikesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsInfoApiService {

    @GET(ApiEndPoint.ENDPOINT_ARTICLES_LIKES)
    suspend fun getLikesForArticle(@Path("ARTICLEID") articleId: String): LikesResponse

    @GET(ApiEndPoint.ENDPOINT_ARTICLES_COMMENTS)
    suspend fun getCommentsForArticle(@Path("ARTICLEID") articleId: String): CommentsResponse
}