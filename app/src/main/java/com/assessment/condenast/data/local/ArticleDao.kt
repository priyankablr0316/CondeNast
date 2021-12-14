package com.assessment.condenast.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assessment.condenast.data.model.db.Article

/**
 * Data access layer which is used for CRUD operation.
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    suspend fun loadAll():List<Article>
}