package com.ksainthilaire.newsapi.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.newsapi.R
import com.ksainthilaire.newsapi.presentation.feature.publishedAtFormatted
import kotlin.time.ExperimentalTime


@Preview(showBackground = true)
@Composable
private fun ArticleScreen_Preview() {
    val articles = listOf(
        Article(
            id = 0,
            sourceId = "",
            author = "The Associated Press",
            title = "Pilot killed when F-16 jet crashes during preparations for a Polish air show - ABC News",
            description = "A government spokesperson has confirmed that an F-16 pilot was killed when his jet crashed in central Poland",
            url = "https://abcnews.go.com/International/wireStory/pilot-killed-16-jet-crashes-preparations-polish-air-125072313",
            urlToImage = "https://s.abcnews.com/images/US/abc_news_default_2000x2000_update_16x9_992.jpg",
            publishedAt = "2025-08-29T10:09:35Z",
            content = "..."
        )
    )
    HomeScreen(
        articles = articles,
        savedArticlesIds = emptySet(),
        onArticleClick = {},
        onBookmarkClick = {}
    )
}


@OptIn(ExperimentalTime::class, ExperimentalTime::class)
@Composable
private fun ArticleCard(
    article: Article,
    isBookmarked: Boolean = false,
    onArticleClick: ((Int) -> Unit)? = null,
    onBookmarkClick: ((Int) -> Unit)? = null,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onArticleClick?.invoke(article.id)
            }),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = article.urlToImage,
                    contentDescription = article.title ?: "Article cover",
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { onBookmarkClick?.invoke(article.id) },
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector =
                            if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription =
                            if (isBookmarked) "Remove from favorites" else "Add to favorites",
                        tint = Color.White
                    )
                }
            }
            Column(modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp)
                ) {
                Text(
                    text = article.title.orEmpty(),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val author = article.author?.takeIf { it.isNotBlank() }

                    val meta = listOfNotNull(author, article.publishedAtFormatted)
                        .joinToString(" • ")
                    if (meta.isNotEmpty()) {
                        Text(
                            text = meta,
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                if (!article.description.isNullOrBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = article.description!!,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Displays a list of articles.
 *
 * @param articles List of articles to show.
 * @param onArticleClick Called when an article is clicked.
 * @param onBookmarkClick Called when bookmark icon is clicked.
 */

@Composable
fun HomeScreen(
    articles: List<Article>,
    savedArticlesIds: Set<Int>?,
    onArticleClick: ((Int) -> Unit)? = null,
    onBookmarkClick: (Int) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.home_title),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (articles.isNotEmpty()) {
                items(articles, key = { it.id }) { article ->
                    val isBookmarked = savedArticlesIds?.contains(article.id) ?: false
                    ArticleCard(
                        article = article,
                        isBookmarked = isBookmarked,
                        onArticleClick = onArticleClick,
                        onBookmarkClick = onBookmarkClick,
                    )
                }
            } else {
                item {
                    Text(text = stringResource(R.string.home_message_empty))
                }
            }
        }
    }
}
