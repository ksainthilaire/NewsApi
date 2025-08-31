package com.ksainthilaire.newsapi.presentation.feature.article

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.newsapi.presentation.feature.publishedAtFormatted


@Composable
fun ArticleScreen(
    article: Article,
    isBookmarked: Boolean = false,
    onBookmarkClick: ((Int) -> Unit)? = null,
) {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp)
                ) {
                    Text(
                        text = article.title.orEmpty(),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val author = article.author?.takeIf { it.isNotBlank() }

                        val meta = listOfNotNull(author, article.publishedAtFormatted)
                            .joinToString(" • ")
                        if (meta.isNotEmpty()) {
                            Text(
                                text = meta,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row {
                        article.url?.let { url ->
                            IconButton(onClick = {
                                uriHandler.openUri(url)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Link,
                                    contentDescription = "Open link"
                                )
                            }
                        }
                        IconButton(onClick = { onBookmarkClick?.invoke(article.id) }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                contentDescription = if (isBookmarked) "Remove from favorites" else "Add to favorites"
                            )
                        }
                    }
                    AsyncImage(
                        modifier = Modifier.height(300.dp).fillMaxWidth(),
                        model = article.urlToImage,
                        contentDescription = article.title ?: "Article cover",
                        contentScale = ContentScale.Fit
                    )
                    val articleContent = article.content
                    if (articleContent != null) {
                        Text(
                            text = articleContent,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ArticleScreen_Preview() {
    val article = Article(
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
    ArticleScreen(article = article)
}
