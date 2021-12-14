package com.assessment.condenast.data.model.api

/**
 * Response form news api for a specific country.
 */
class ArticlesResponse {

    var articles: List<Article>? = null

    data class Article(
        var source: Source?,
        var author: String?,
        var title: String?,
        var description: String?,
        var url: String?,
        var urlToImage: String?,
        var publishedAt: String?,
        var content: String?)

    data class Source(var id: String?, var name: String?)
}
