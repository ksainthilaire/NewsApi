package com.ksainthilaire.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksainthilaire.data.local.entity.ArticleSourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleSourceDao {
    @Query("SELECT * FROM sources")
    fun getAllSources(): Flow<List<ArticleSourceEntity>>

    @Query("SELECT * FROM sources WHERE id = :aid LIMIT 1")
    fun getSourceById(aid: Int): Flow<ArticleSourceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sources: List<ArticleSourceEntity>)
}