package com.ksainthilaire.domain.model

/**
 *
 * @property id            The identifier of the news source.
 * @property name          The name of the news source
 * @property description   A description of the news source
 * @property url           The URL of the homepage.
 * @property category      The type of news to expect from this news source. @see [ArticleCategory]
 * @property language      The language that this news source writes in.  @see [ArticleLanguage]
 * @property country       The country this news source is based in (and primarily writes about).
 */
data class ArticleSource(
    val id: String?,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: ArticleCategory?,
    val language: ArticleLanguage?,
    val country: ArticleCountry?,
)