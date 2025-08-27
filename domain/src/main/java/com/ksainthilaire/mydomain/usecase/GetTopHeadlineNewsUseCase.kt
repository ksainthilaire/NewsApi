package com.ksainthilaire.domain.usecase

import com.ksainthilaire.domain.model.ArticleCategory
import com.ksainthilaire.domain.model.ArticleCountry
import com.ksainthilaire.domain.model.Articles
import com.ksainthilaire.domain.repository.NewsRepository

/**
 * Use case responsible for fetching the top headline news.
 *
 * @property newsRepository Repository that provides access to news data sources.
 */
class GetTopHeadlineNewsUseCase(private val newsRepository: NewsRepository) {

    /**
     * Executes the use case.
     *.
     * @return A list of [Articles] containing the top headline news for the given user,
     * or `null` if no data is available.
     */
    suspend operator fun invoke(
        country: ArticleCountry,
        category: ArticleCategory,
        sources: List<String>,
        query: List<String>,
        pageSize: Int?,
        page: Int?,
    ): List<Articles>? {
        return null
    }
}