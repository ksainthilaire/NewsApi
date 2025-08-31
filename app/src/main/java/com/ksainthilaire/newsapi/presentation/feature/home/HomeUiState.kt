package com.ksainthilaire.newsapi.presentation.feature.home

import com.ksainthilaire.domain.model.Article

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val savedArticlesIds: Set<Int> = emptySet()
)