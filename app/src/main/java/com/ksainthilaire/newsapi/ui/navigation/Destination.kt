package com.ksainthilaire.newsapi.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val icon: ImageVector,
    val contentDescription: String,
    val label: String
) {
    Home(
        route = "home",
        icon = Icons.Filled.LocationOn,
        contentDescription = "Home",
        label = "Home"
    ),
    Saved(
        route = "saved",
        icon = Icons.Filled.BookmarkBorder,
        contentDescription = "Saved",
        label = "Saved"
    );

    companion object {
        val entries = listOf(Home, Saved)
    }
}