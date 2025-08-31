package com.ksainthilaire.newsapi.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksainthilaire.domain.model.enum.Language
import com.ksainthilaire.domain.usecase.GetArticlesUseCase
import com.ksainthilaire.newsapi.ui.store.BookmarkStorePrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import io.klogging.logger

class HomeViewModel(
    private val getEverythingNewsUseCase: GetArticlesUseCase,
    private val bookmarkStore: BookmarkStorePrefs
) : ViewModel() {

    companion object {
        private val TAG = HomeViewModel::class.java.name
    }

    private val logger = logger(TAG)

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var everythingJob: Job? = null

    init {
        loadArticles()
    }

    private fun loadSavedIds() = viewModelScope.launch {
        logger.info { "Loading saved article IDs" }
        val ids = withContext(Dispatchers.IO) {
            runCatching { bookmarkStore.getSavedIds() }.getOrNull().orEmpty()
        }
        logger.info { "Saved IDs found: $ids" }
        _uiState.update { it.copy(savedArticlesIds = ids) }
    }

    fun loadArticles() {
        loadSavedIds()

        everythingJob?.cancel()
        everythingJob = viewModelScope.launch {
            logger.info { "Starting news loading" }
            val sysLang = Locale.getDefault().language
            val language: Language = enumValues<Language>()
                .firstOrNull {
                    it.name.equals(sysLang, ignoreCase = true)
                            || it.code.equals(sysLang, ignoreCase = true)
                }
                ?: Language.EN

            logger.info { "Language detected: ${language.name} (${language.code})" }

            val params = GetArticlesUseCase.Params(
                language = language
            )

            getEverythingNewsUseCase(params)
                .flowOn(Dispatchers.IO)
                .onStart {
                    logger.info { "Loading articles..." }
                }
                .catch { t ->
                    logger.error { "Error while loading articles: ${t.message}" }
                    t.printStackTrace()
                    _uiState.update { it.copy(articles = emptyList()) }
                }
                .collectLatest { articles ->
                    logger.info { "Articles received: ${articles.size}" }
                    _uiState.update { it.copy(articles = articles) }
                }
        }
    }

    fun toggleBookmark(articleId: Int) = viewModelScope.launch {
        logger.info { "Toggling favorite for article: $articleId" }
        val newSet = uiState.value.savedArticlesIds.toMutableSet().also { set ->
            if (!set.add(articleId)) {
                logger.info { "Article removed from favorites: $articleId" }
                set.remove(articleId)
            } else {
                logger.info { "Article added to favorites: $articleId" }
            }
        }

        _uiState.update { it.copy(savedArticlesIds = newSet) }

        withContext(Dispatchers.IO) {
            runCatching { bookmarkStore.saveSavedIds(newSet) }
            logger.info { "Favorites saved: $newSet" }
        }
    }

    override fun onCleared() {
        everythingJob?.cancel()
        super.onCleared()
    }
}
