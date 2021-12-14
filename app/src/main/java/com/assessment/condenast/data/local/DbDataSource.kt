package com.assessment.condenast.data.local

import com.assessment.condenast.data.model.db.Article

/**
 * Contract defined to access database.
 */
interface DbDataSource {
    suspend fun insertArticle(article: Article)
    suspend fun allArticles(): List<Article>
}