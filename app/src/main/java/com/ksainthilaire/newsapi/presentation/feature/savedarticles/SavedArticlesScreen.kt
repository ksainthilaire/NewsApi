package com.ksainthilaire.newsapi.presentation.feature.savedarticles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.newsapi.R
import com.ksainthilaire.newsapi.presentation.feature.publishedAtFormatted


@Composable
private fun ArticleCard(
    article: Article,
    onArticleClick: ((Int) -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onArticleClick?.invoke(article.id) }),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(
                    text = article.title.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val meta = listOfNotNull(
                        article.author,
                        article.publishedAtFormatted
                    )
                        .joinToString(" • ")
                    if (meta.isNotEmpty()) {
                        Text(
                            text = meta,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                if (!article.description.isNullOrBlank()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = article.description!!,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = article.urlToImage,
                    contentDescription = article.title ?: "Illustration de l’article",
                    contentScale = ContentScale.Fit
                )
            }

        }
    }
}


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
    SavedScreen(articles)
}

/**
 * Screen showing a list of saved articles.
 *
 * @param articles items to display
 * @param onArticleClick called when an article is tapped
 * @param onBookmarkClick called when bookmark icon is tapped
 */

@Composable
fun SavedScreen(
    articles: List<Article>,
    onArticleClick: ((Int) -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.saved_title),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (articles.isNotEmpty()) {
                items(articles, key = { it.id }) { article ->
                    ArticleCard(
                        article = article,
                        onArticleClick = { onArticleClick?.invoke(article.id) },
                    )
                }
            } else {
                item {
                    Text(text = stringResource(R.string.saved_message_empty))
                }
            }
        }
    }
}