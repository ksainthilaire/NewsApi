package com.ksainthilaire.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksainthilaire.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
   @Query("SELECT * FROM articles ORDER BY published_at DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>


    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    suspend fun getArticle(id: Int): ArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)
}