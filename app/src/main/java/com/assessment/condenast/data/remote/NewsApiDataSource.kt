package com.assessment.condenast.data.remote

import com.assessment.condenast.data.model.Result
import com.assessment.condenast.data.model.api.ArticlesResponse

/**
 * Contract defined to access news api.
 */
interface NewsApiDataSource {

    suspend fun getArticles(country: String,category: String): Result<ArticlesResponse>
}