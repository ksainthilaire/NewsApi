package com.ksainthilaire.newsapi.ui.store

import android.content.SharedPreferences
import androidx.core.content.edit


class BookmarkStorePrefs(
    private val prefs: SharedPreferences
) {

    private val KEY_IDS = "saved_article_ids"

    fun getSavedIds(): Set<Int> {
        val raw = prefs.getStringSet(KEY_IDS, emptySet()) ?: emptySet<String>()
        return raw.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun saveSavedIds(ids: Set<Int>) {
        val asStrings = ids.map { it.toString() }.toSet()
        prefs.edit { putStringSet(KEY_IDS, asStrings) }
    }
}
