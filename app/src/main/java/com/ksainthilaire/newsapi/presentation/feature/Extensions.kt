package com.ksainthilaire.newsapi.presentation.feature

import com.ksainthilaire.domain.model.Article
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

val Article.publishedAtFormatted: String?
    get() = publishedAt?.let { publishedAt ->
        try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val date = parser.parse(publishedAt)

            val formatter = SimpleDateFormat("EEEE d MMMM yyyy 'à' HH:mm", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("Europe/Paris")

            date?.let { formatter.format(it) }
        } catch (_: Exception) {
            null
        }
    }
