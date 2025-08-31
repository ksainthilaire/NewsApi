package com.ksainthilaire.data.mapper

import com.ksainthilaire.data.local.entity.ArticleEntity
import com.ksainthilaire.data.remote.dto.ArticleDto
import com.ksainthilaire.domain.model.Article

// Maps ArticleDto (API) to Article (domain)
fun ArticleDto.toArticle() : Article {
    return Article(
        id = 0,
        sourceId = source?.id,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}

// Maps ArticleDto (API) to ArticleEntity (local DB)
fun ArticleDto.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        sourceId = source?.id,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

// Maps ArticleEntity (local DB) to Article (domain)
fun ArticleEntity.toArticle(): Article {
    return Article(
        id = id ?: 0,
        sourceId = null,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}
