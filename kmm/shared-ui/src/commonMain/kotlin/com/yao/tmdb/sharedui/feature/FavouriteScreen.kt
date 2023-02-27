package com.yao.tmdb.sharedui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            AboutSettingsItem()
        }
    }
}

@Composable
private fun AboutSettingsItem() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { }
            .padding(start = 16.dp, end = 16.dp)
    ) {
        val appName = "The Movies"
        SettingHeaderTitle(title = "Info")
        ColumnSpacer(value = 8)
        SettingTitle(title = "About '$appName'")
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "'$appName' is a Multiplatform app (Android &amp; iOS) for viewing Movies from TMDB Api.\nIt is built with KMM & compose-jb.",
                style = MaterialTheme.typography.body2,
            )
        }
        ColumnSpacer(value = 8)
        SettingListDivider()
    }
}

@Composable
internal fun SettingHeaderTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
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