package com.ksainthilaire.domain.model

import com.ksainthilaire.domain.model.enum.ArticleCategory
import com.ksainthilaire.domain.model.enum.ArticleCountry
import com.ksainthilaire.domain.model.enum.Language

/**
 * Represents a news source.
 * @property id            The identifier of the news source.
 * @property name          The name of the news source
 * @property description   A description of the news source
 * @property url           The URL of the homepage.
 * @property category      The type of news to expect from this news source. @see [ArticleCategory]
 * @property language      The language that this news source writes in.  @see [Language]
 * @property country       The country this news source is based.
 */
data class ArticleSource(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val url: String? = null,
    val category: ArticleCategory? = null,
    val language: Language? = null,
    val country: ArticleCountry? = null,
)