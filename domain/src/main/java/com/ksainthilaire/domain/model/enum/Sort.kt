package com.ksainthilaire.domain.model.enum

/**
 * Defines the available sorting options for articles.
 * @property option The string identifier of the sorting option
 */
enum class Sort(val option: String) {
    Relevancy("relevancy"),
    Popularity("popularity"),
    PublishedAt("publishedAt"),
}