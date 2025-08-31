package com.ksainthilaire.newsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.newsapi.presentation.components.NewsScaffold
import com.ksainthilaire.newsapi.presentation.feature.article.ArticleScreen
import com.ksainthilaire.newsapi.presentation.feature.home.HomeScreen
import com.ksainthilaire.newsapi.presentation.feature.home.HomeViewModel
import com.ksainthilaire.newsapi.presentation.feature.article.ArticleViewModel
import com.ksainthilaire.newsapi.presentation.feature.savedarticles.SavedScreen
import com.ksainthilaire.newsapi.presentation.feature.savedarticles.SavedArticlesViewModel
import com.ksainthilaire.newsapi.presentation.theme.NewsApiTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NewsApiTheme {
                NewsScaffold(navController = navController) { innerPadding ->
                    NewsNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun NewsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            val homeViewModel = koinViewModel<HomeViewModel>()
            val state by homeViewModel.uiState.collectAsStateWithLifecycle()

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(lifecycleOwner) {
                val obs = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        println("hmv on resume")
                        homeViewModel.loadArticles()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(obs)
                onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
            }

            HomeScreen(
                articles = state.articles,
                savedArticlesIds = state.savedArticlesIds,
                onArticleClick = { id -> navController.navigate("article/$id") },
                onBookmarkClick = homeViewModel::toggleBookmark
            )
        }

        composable(
            route = "article/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        )  { entry ->
            val articleViewModel = koinViewModel<ArticleViewModel>(
                viewModelStoreOwner = entry
            )

            val state by articleViewModel.uiState.collectAsStateWithLifecycle()
            val article = state.article ?: Article(id = 0)

            ArticleScreen(
                article = article,
                isBookmarked = state.savedArticlesIds.contains(article.id),
                onBookmarkClick = articleViewModel::toggleBookmark
            )
        }

        composable("saved") {
            val savedArticlesViewModel = koinViewModel<SavedArticlesViewModel>()
            val state by savedArticlesViewModel.uiState.collectAsStateWithLifecycle()

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(lifecycleOwner) {
                val obs = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        savedArticlesViewModel.loadArticles()
                    }
                }
                lifecycleOwner.lifecycle.addObserver(obs)
                onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
            }

            SavedScreen(
                articles = state.articles,
                onArticleClick = { id -> navController.navigate("article/$id") },
            )
        }
    }
}
