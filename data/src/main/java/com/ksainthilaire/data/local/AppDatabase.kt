package com.ksainthilaire.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ksainthilaire.data.local.dao.ArticleDao
import com.ksainthilaire.data.local.dao.ArticleSourceDao
import com.ksainthilaire.data.local.entity.ArticleEntity
import com.ksainthilaire.data.local.entity.ArticleSourceEntity

@Database(entities = [ArticleEntity::class, ArticleSourceEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun articleSourceDao(): ArticleSourceDao
}