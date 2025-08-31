package com.ksainthilaire.data.mapper

import com.ksainthilaire.data.local.entity.ArticleSourceEntity
import com.ksainthilaire.data.remote.dto.ArticleSourceDto
import com.ksainthilaire.domain.model.ArticleSource
import com.ksainthilaire.domain.model.enum.ArticleCategory
import com.ksainthilaire.domain.model.enum.ArticleCountry
import com.ksainthilaire.domain.model.enum.Language


// Maps ArticleSourceDto (API) to ArticleSource (domain)
fun ArticleSourceDto.toArticleSource() : ArticleSource {
    return ArticleSource(
        id = id,
        name = name,
        description = description,
        url = url,
        category = ArticleCategory.Entertainment,
        language = Language.FR,
        country = ArticleCountry.FR,
    )
}

// Maps ArticleSourceDto (API) to ArticleSourceEntity (local DB)
fun ArticleSourceDto.toArticleSourceEntity(): ArticleSourceEntity {
    return ArticleSourceEntity(
        id = 0,
        sourceId = id,
        name = name,
        description = description,
        url = url,
        category = category,
        language = language,
        country = country,
    )
}

// Maps ArticleSourceEntity (local DB) to ArticleSource (domain)
fun ArticleSourceEntity.toArticleSource(): ArticleSource {
    return ArticleSource(
        id = sourceId,
        name = name,
        description = description,
        url = url,
        category = ArticleCategory.Entertainment,
        language = Language.FR,
        country = ArticleCountry.FR,
    )
}
