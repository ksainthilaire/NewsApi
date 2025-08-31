package com.ksainthilaire.domain.repository

import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.domain.usecase.GetArticlesUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for retrieving news articles from different sources.
 */
interface NewsRepository {
    /**
     * Retrieves news articles matching the given parameters.
     *
     * @param params Filters and options for the search (query, sources, dates, language, etc.).
     * @return A [Flow] emitting a list of [Article], or an empty list if none are found.
     */
    suspend fun getArticles(params: GetArticlesUseCase.Params): Flow<List<Article>>

    /**
     * Retrieves a single news article from the database by its ID.
     *
     * @param articleId The unique identifier of the article to retrieve.
     * @return The [Article] corresponding to the given ID.
     * @throws NoSuchElementException if no article with the given ID is found.
     */
    suspend fun getArticleFromDatabase(articleId: Int): Article?
}