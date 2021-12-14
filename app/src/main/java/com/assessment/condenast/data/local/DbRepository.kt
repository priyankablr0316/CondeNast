package com.assessment.condenast.data.local

import com.assessment.condenast.data.model.db.Article
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages DB data - Fetches data from DB using contract and pass it to main AppDataManager which
 * is then accessed by ViewModels.
 *  ArticleFragment - > ArticleViewModel -> AppDataManager - > DbRepository - > AppDatabase (DB)
 */
@Singleton
class DbRepository @Inject constructor(private val mAppDatabase: AppDatabase) : DbDataSource {

    override suspend fun insertArticle(article: Article) = mAppDatabase.articleDao().insert(article)

    override suspend fun allArticles(): List<Article> = mAppDatabase.articleDao().loadAll()
}