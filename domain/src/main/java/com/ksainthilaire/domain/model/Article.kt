package com.ksainthilaire.domain.model

/**
 * Represents a news article.
 *
 * @property sourceId      Source id of the article.
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
    val id: Int,
    val sourceId: String? = null,
    val author: String? = null,
    val title:  String? = null,
    val description:  String? = null,
    val url:  String? = null,
    val urlToImage: String? = null,
    val publishedAt:  String? = null,
    val content:  String? = null,
)