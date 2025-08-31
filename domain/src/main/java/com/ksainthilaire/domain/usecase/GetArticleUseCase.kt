package com.ksainthilaire.domain.usecase

import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.domain.repository.NewsRepository

/**
 * Use case responsible for retrieving a specific article from the local database.
 *
 * @property newsRepository Repository that provides access to article data sources.
 */
class GetArticleByIdUseCase(
    private val newsRepository: NewsRepository
) {

    /**
     * Fetches an article by its unique identifier.
     *
     * @param articleId The unique ID of the article to retrieve.
     * @return The [Article] if found, or `null` otherwise.
     */
    suspend operator fun invoke(articleId: Int): Article? {
        return newsRepository.getArticleFromDatabase(articleId)
    }
}
