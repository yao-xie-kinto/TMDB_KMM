package com.yao.tmdb.sharedui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yao.tmdb.sharedui.component.ColumnSpacer

@Composable
internal fun SettingsScreen() {
    Box(modifier = Modifier.background(Color.Black)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                val appName = "The Movies"
                SettingsItem(
                    header = "Info",
                    title = "About '$appName'",
                    content = "'$appName' is a Multiplatform app (Android &amp; iOS) for viewing Movies from TMDB Api.\nIt is built with KMM & compose-jb."
                )
            }
            item {
                val tmdb = "TMDB"
                SettingsItem(
                    header = "License",
                    title = "About '$tmdb'",
                    content = "The API is provided by'$tmdb', please refer to ${tmdb}'s Terms of Use at http://www.themoviedb.org/terms-of-use."
                )
            }

        }
    }
}

@Composable
private fun SettingsItem(header: String, title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { }
            .padding(start = 16.dp, end = 16.dp)
    ) {
        SettingHeaderTitle(header = header)
        ColumnSpacer(value = 8)
        SettingTitle(title = title)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = content,
                style = MaterialTheme.typography.body2,
            )
        }
        ColumnSpacer(value = 8)
        SettingListDivider()
        ColumnSpacer(value = 8)
    }
}

@Composable
internal fun SettingHeaderTitle(header: String, modifier: Modifier = Modifier) {
    Text(
        text = header,
        style = MaterialTheme.typography.body2.copy(
            color = Color.Yellow
        ),
        modifier = modifier
            .fillMaxWidth(),
    )
}

@Composable
internal fun SettingTitle(title: String) {
    Text(title, style = MaterialTheme.typography.subtitle1)
}

@Composable
private fun SettingListDivider() {
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.25f)
    )
}