package com.ksainthilaire.domain.repository

import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.domain.model.ArticleCategory
import com.ksainthilaire.domain.model.ArticleCountry
import com.ksainthilaire.domain.model.Articles

/**
 * Repository interface for retrieving news articles from different sources.
 * Defines the contract for fetching both top headlines and more detailed search queries.
 */
interface NewsRepository {
    /**
     * Retrieves the top headlines based on the given filters.
     *
     * @param country   The country where the news source is based.
     * @param category  The category of news to expect from this source. See [ArticleCategory].
     * @param sources   A comma-separated string of identifiers for specific news sources or blogs to fetch headlines from.
     * @param query     Keywords or a phrase to search for in the headlines.
     * @param pageSize  The number of results to return per page.
     * @param page      The page number of the results to fetch.
     * @return List of [Articles], or `null` if none found.
     */
    fun getTopHeadlines(
        country: ArticleCountry,
        category: ArticleCategory,
        sources: String?,
        query: String?,
        pageSize: Int?,
        page: Int?,
    ) : List<Article>?

    /**
     * @param query           Keywords or phrases to search for in the article title and body. (q)
     * @param searchIn        Comma-separated: title,description,content
     * @param sources         Comma-separated source IDs
     * @param domains         Comma-separated domains to include
     * @param excludeDomains  Comma-separated domains to exclude
     * @param from            ISO 8601 datetime (e.g. 2025-08-01T00:00:00)
     * @param to              ISO 8601 datetime
     * @param language        2-letter code.
     * @param sortBy          relevancy | popularity | publishedAt
     * @param pageSize        The number of results to return per page.
     * @param page            Page to through the results.
     * @return List of [Articles], or `null` if none found.
     */
    fun getEverything(
        query: String?,
        searchIn: String?,
        sources: String?,
        domains: String?,
        excludeDomains: String?,
        from: String?,
        to: String?,
        language: String?,
        sortBy: String?,
        pageSize: String?,
        page: String?,
    ) : List<Article>?
}