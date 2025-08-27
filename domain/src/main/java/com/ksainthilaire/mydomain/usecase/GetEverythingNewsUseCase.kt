package com.ksainthilaire.domain.usecase

import com.ksainthilaire.domain.model.Articles
import com.ksainthilaire.domain.model.SortByOptions
import com.ksainthilaire.domain.repository.NewsRepository

/**
 * Use case responsible for fetching all news articles.
 *
 * @property newsRepository Repository that provides access to news data sources.
 */
class GetEverythingNewsUseCase(private val newsRepository: NewsRepository) {

    /**
     * Retrieves news articles based on the provided filters.
     *
     * @return List of [Articles], or `null` if none found.
     */
    suspend operator fun invoke(
        query: String?,
        searchIn: String?,
        sources: List<String>?,
        domains: List<String>?,
        excludeDomains: List<String>?,
        from: String?,
        to: String?,
        language: String?,
        sortBy: SortByOptions,
        pageSize: String?,
        page: String?,
    ): List<Articles>? {
        return null
    }
}