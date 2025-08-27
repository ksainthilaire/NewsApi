package com.ksainthilaire.domain.model

/**
 * Defines the available sorting options for articles.
 * @property option The string identifier of the sorting option
 */
enum class SortByOptions(val option: String) {
    Relevancy("relevancy"),
    Popularity("popularity"),
    PublishedAt("publishedAt"),
}