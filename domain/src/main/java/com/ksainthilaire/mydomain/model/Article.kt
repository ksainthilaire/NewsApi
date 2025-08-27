package com.ksainthilaire.domain.model

/**
 * @property source        Source of the article.
 * @property author        Author of the article.
 * @property title         Title of the article.
 * @property description   Description of the article.
 * @property url           Article URL.
 * @property urlToImage    Cover image URL of the article.
 * @property publishedAt   Publication date of the article in ISO 8601 format.
 * @property content       Content of the article.
 *
 * @see [ArticleSource]
 */
data class Article(
    val source: ArticleSource?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)