package com.ksainthilaire.data.repository

import com.ksainthilaire.data.local.dao.ArticleDao
import com.ksainthilaire.data.local.dao.ArticleSourceDao
import com.ksainthilaire.data.mapper.toArticle
import com.ksainthilaire.data.mapper.toArticleEntity
import com.ksainthilaire.data.mapper.toArticleSourceEntity
import com.ksainthilaire.data.remote.NewsService
import com.ksainthilaire.data.remote.dto.ArticleDto
import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.domain.repository.NewsRepository
import com.ksainthilaire.domain.usecase.GetArticlesUseCase
import io.klogging.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.collections.map

class NewsRepositoryImpl(
    val api: NewsService,
    val articleDao: ArticleDao,
    val articleSourceDao: ArticleSourceDao,
) : NewsRepository {

    companion object {
        private val TAG = NewsRepositoryImpl::class.java.name
    }

    private val logger = logger(TAG)

    val articles: Flow<List<Article>>
        get() = articleDao
            .getAllArticles()
            .map { entities -> entities.map { it.toArticle() } }
            .distinctUntilChanged()

    override suspend fun getArticles(
        params: GetArticlesUseCase.Params
    ): Flow<List<Article>> =
        articles.onStart {
            kotlin.runCatching  {
                refreshArticles(params = params)
            }
        }

    override suspend fun getArticleFromDatabase(articleId: Int): Article? {
        val articleEntity = articleDao.getArticle(articleId)
        return articleEntity?.toArticle()
    }

    private suspend fun refreshArticles(params: GetArticlesUseCase.Params) {
        logger.info { "Start refreshing news with query: '${params.query}'" }

        val response = api.searchEverything(
            query = params.query,
            searchIn = params.searchIn,
            sources = params.sources,
            domains = params.domains,
            excludeDomains = params.excludeDomains,
            from = params.from,
            to = params.to,
            language = params.language?.code,
            sortBy = params.sortBy?.option,
            pageSize = params.pageSize,
            page = params.page,
        )

        if (!response.isSuccessful) {
            logger.error { "Request failed: code=${response.code()} message=${response.message()}" }
            throw IllegalStateException("searchEverything failed: HTTP ${response.code()} ${response.message()}")
        }

        val body = response.body() ?: return
        val articlesDto: List<ArticleDto> = body.articles.filter { it.url?.isNotEmpty() ?: false }
        val sources = articlesDto.mapNotNull { it.source?.toArticleSourceEntity() }

        if (sources.isNotEmpty()) {
            logger.info { "Saving ${sources.size} sources into database" }
            articleSourceDao.insertAll(sources)
        } else {
            logger.info { "No sources found in response" }
        }

        val entities = articlesDto.map { it.toArticleEntity() }

        if (entities.isNotEmpty()) {
            logger.info { "Saving ${entities.size} articles into database" }
            articleDao.insertAll(entities)
            logger.info { "Articles saved successfully" }
        }
    }
}