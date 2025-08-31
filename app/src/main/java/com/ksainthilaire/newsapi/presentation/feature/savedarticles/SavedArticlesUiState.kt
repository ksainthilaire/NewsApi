package com.ksainthilaire.newsapi.presentation.feature.savedarticles

import com.ksainthilaire.domain.model.Article

data class SavedArticlesUiState(
    val articles: List<Article> = emptyList(),
)