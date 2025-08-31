package com.ksainthilaire.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sources")
data class ArticleSourceEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "source_id") val sourceId: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "url") val url: String? = null,
    @ColumnInfo(name = "category") val category: String? = null,
    @ColumnInfo(name = "language") val language: String? = null,
    @ColumnInfo(name = "country") val country: String? = null
)
