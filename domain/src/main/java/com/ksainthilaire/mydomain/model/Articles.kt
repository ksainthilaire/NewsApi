package com.ksainthilaire.domain.model

/**
 * @property totalResults The total number of articles
 * @property articles     List of articles.
 * @see [Article]
 */
data class Articles(
    val totalResults: String?,
    val articles: List<Article>? = emptyList()
)