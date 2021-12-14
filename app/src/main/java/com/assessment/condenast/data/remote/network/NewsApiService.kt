package com.assessment.condenast.data.remote.network

import com.assessment.condenast.data.model.api.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET(ApiEndPoint.ENDPOINT_ARTICLES)
    suspend fun getArticles(@Query("country") country: String, @Query("category") category: String, @Query("apiKey") apiKey: String): ArticlesResponse
}