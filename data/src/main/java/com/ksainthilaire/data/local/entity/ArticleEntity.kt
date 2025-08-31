package com.ksainthilaire.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "source_id") val sourceId: String? = null,
    @ColumnInfo(name = "source_name") val sourceName: String? = null,
    @ColumnInfo(name = "author") val author: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "url") val url: String? = null,
    @ColumnInfo(name = "url_to_image") val urlToImage: String? = null,
    @ColumnInfo(name = "published_at") val publishedAt: String? = null,
    @ColumnInfo(name = "content") val content: String? = null
)
