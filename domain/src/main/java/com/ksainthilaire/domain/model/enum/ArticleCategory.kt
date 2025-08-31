package com.ksainthilaire.domain.model.enum

/**
 * Represents the different possible categories of articles.
 * @property value The string identifier of the category
 */
enum class ArticleCategory(val value: String) {
    Business("business"),
    Entertainment("entertainment"),
    General("general"),
    Health("health"),
    Science("science"),
    Sports("sports"),
    Technology("technology"),
    ALL("all")
}