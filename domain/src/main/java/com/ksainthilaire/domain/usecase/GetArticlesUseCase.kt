package com.ksainthilaire.domain.usecase

import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.domain.model.enum.Language
import com.ksainthilaire.domain.model.enum.Sort
import com.ksainthilaire.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case responsible for fetching all news articles.
 * @property newsRepository Repository that provides access to news data sources.
 */
class GetArticlesUseCase(private val newsRepository: NewsRepository) {

    /**
     * Represents the parameters used to search for news articles.
     *
     * @param query           Keywords or phrases to search for in the article title and body. (q)
     *                        The default value "all" retrieves all articles.
     * @param searchIn        Comma-separated: title,description,content
     * @param sources         Comma-separated source IDs
     * @param domains         Comma-separated domains to include
     * @param excludeDomains  Comma-separated domains to exclude
     * @param from            ISO 8601 datetime (e.g. 2025-08-01T00:00:00)
     * @param to              ISO 8601 datetime
     * @param language        2-letter code.
     * @param sortBy          relevancy | popularity | publishedAt
     * @param pageSize        The number of results to return per page.
     * @param page            Page to through the results..
     */
    data class Params(
        val query: String = "all",
        val searchIn: String? = null,
        val sources: String? = "",
        val domains: String? = null,
        val excludeDomains: String? = null,
        val from: String? = null,
        val to: String? = null,
        val language: Language? = null,
        val sortBy: Sort? = null,
        val pageSize: Int? = null,
        val page: Int? = null
    )

    /**
     * Retrieves news articles based on the provided filters.
     *
     * @see [Params]
     * @return List of [Article], or `null` if none found.
     */
    suspend operator fun invoke(params: Params): Flow<List<Article>> {
        return newsRepository.getArticles(params)
    }
}