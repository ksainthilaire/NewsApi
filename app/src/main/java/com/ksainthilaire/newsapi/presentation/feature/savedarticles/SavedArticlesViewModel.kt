package com.ksainthilaire.newsapi.presentation.feature.savedarticles

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
import java.util.Locale
import io.klogging.logger
import kotlinx.coroutines.flow.map

class SavedArticlesViewModel(
    private val getEverythingNewsUseCase: GetArticlesUseCase,
    private val bookmarkStore: BookmarkStorePrefs
) : ViewModel() {

    companion object {
        private val TAG = SavedArticlesViewModel::class.java.name
    }

    private val logger = logger(TAG)

    private val savedArticlesIds: Set<Int>
        get() = bookmarkStore.getSavedIds()


    private val _uiState: MutableStateFlow<SavedArticlesUiState> = MutableStateFlow(SavedArticlesUiState())
    val uiState: StateFlow<SavedArticlesUiState> = _uiState.asStateFlow()

    private var everythingJob: Job? = null

    init {
        loadArticles()
    }

    fun loadArticles() {
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
                .map { articles -> articles.filter { savedArticlesIds.contains(it.id) } }
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

    override fun onCleared() {
        super.onCleared()
        everythingJob?.cancel()
    }
}
