package com.ksainthilaire.newsapi.presentation.feature.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksainthilaire.domain.usecase.GetArticleByIdUseCase
import com.ksainthilaire.newsapi.ui.store.BookmarkStorePrefs
import io.klogging.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel(
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val bookmarkStore: BookmarkStorePrefs,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private val TAG = ArticleViewModel::class.java.name
    }

    private val logger = logger(TAG)

    private val _uiState = MutableStateFlow(ArticleUiState())
    val uiState: StateFlow<ArticleUiState> = _uiState.asStateFlow()

    init {
        loadSavedIds()

        val articleId: Int? = savedStateHandle["id"]
        if (articleId != null) {
            loadArticle(articleId)
       }
    }

    private fun loadSavedIds() = viewModelScope.launch {
        logger.info { "Loading saved article IDs..." }
        val ids = withContext(Dispatchers.IO) {
            runCatching { bookmarkStore.getSavedIds() }
                .onFailure { logger.error { "Failed to load saved IDs: ${it.message}" } }
                .getOrNull()
                .orEmpty()
        }
        logger.info { "Loaded ${ids.size} saved IDs" }
        _uiState.update { it.copy(savedArticlesIds = ids) }
    }

    private fun loadArticle(articleId: Int) = viewModelScope.launch {
        logger.info { "Loading article id=$articleId..." }
        val article = withContext(Dispatchers.IO) {
            runCatching { getArticleByIdUseCase(articleId) }
                .onFailure { logger.error { "Failed to load article: ${it.message}" } }
                .getOrNull()
        }
        if (article != null) {
            logger.info { "Article id=${article.id} loaded successfully" }
        } else {
            logger.warn { "Article id=$articleId not found" }
        }
        _uiState.update { it.copy(article = article) }
    }

    fun toggleBookmark(articleId: Int) = viewModelScope.launch {
        logger.info { "Toggling bookmark for article id=$articleId" }
        val newSet = uiState.value.savedArticlesIds.toMutableSet().apply {
            if (!add(articleId)) {
                remove(articleId)
                logger.info { "Article id=$articleId removed from bookmarks" }
            } else {
                logger.info { "Article id=$articleId added to bookmarks" }
            }
        }.toSet()

        _uiState.update { it.copy(savedArticlesIds = newSet) }

        withContext(Dispatchers.IO) {
            runCatching { bookmarkStore.saveSavedIds(newSet) }
                .onSuccess { logger.info { "Bookmarks saved (${newSet.size} total)" } }
                .onFailure { logger.error { "Failed to save bookmarks: ${it.message}" } }
        }
    }
}
