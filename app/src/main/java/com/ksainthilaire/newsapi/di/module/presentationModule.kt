package com.ksainthilaire.newsapi.di.module

import android.content.Context
import com.ksainthilaire.domain.usecase.GetArticleByIdUseCase
import com.ksainthilaire.domain.usecase.GetArticlesUseCase
import com.ksainthilaire.newsapi.presentation.feature.home.HomeViewModel
import com.ksainthilaire.newsapi.presentation.feature.savedarticles.SavedArticlesViewModel
import com.ksainthilaire.newsapi.ui.store.BookmarkStorePrefs
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import androidx.lifecycle.SavedStateHandle
import com.ksainthilaire.newsapi.presentation.feature.article.ArticleViewModel

val presentationModule = module {
    single {
        androidContext().getSharedPreferences(
            "bookmark_prefs",
            Context.MODE_PRIVATE
        )
    }

    factory {
        GetArticlesUseCase(get())
    }
    factory {
        GetArticleByIdUseCase(get())
    }
    single {
        BookmarkStorePrefs(get())
    }
    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel { (state: SavedStateHandle) ->
        ArticleViewModel(get(), get(), state)
    }

    viewModel {
        SavedArticlesViewModel(get(), get())
    }
}