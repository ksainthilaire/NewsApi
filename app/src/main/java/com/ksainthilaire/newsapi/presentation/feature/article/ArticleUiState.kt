package com.ksainthilaire.newsapi.presentation.feature.article

import com.ksainthilaire.domain.model.Article

data class ArticleUiState(
    val article: Article? = null,
    val savedArticlesIds: Set<Int> = emptySet()
)