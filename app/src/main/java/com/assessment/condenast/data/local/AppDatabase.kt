package com.assessment.condenast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assessment.condenast.data.model.db.Article

/**
 * Application data base class which deals with insert and retrieve operations for articles.
 */
@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}