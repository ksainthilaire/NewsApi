package com.ksainthilaire.domain.model

/**
 * Specifies the fields in which a search query can be applied.
 * @property value The string identifier of the search field
 */
enum class SearchInOptions(val value: String) {
    Title("title"),
    Description("description"),
    Content("content")
}