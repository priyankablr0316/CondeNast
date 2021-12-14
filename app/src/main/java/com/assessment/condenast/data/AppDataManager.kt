package com.assessment.condenast.data

import com.assessment.condenast.data.remote.NewsApiRepository
import com.assessment.condenast.data.local.DbRepository
import com.assessment.condenast.data.remote.NewsInfoApiRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single place which manages online and offline data and provides to view models.
 */
@Singleton
class AppDataManager @Inject constructor(
    private val newsApiRepository: NewsApiRepository,
    private val newsInfoApiRepository: NewsInfoApiRepository,
    private val dbRepository: DbRepository
) {
    fun getNewsApiRepository(): NewsApiRepository = newsApiRepository

    fun getNewInfoApiRepository(): NewsInfoApiRepository = newsInfoApiRepository

    fun getDbRepository(): DbRepository = dbRepository
}