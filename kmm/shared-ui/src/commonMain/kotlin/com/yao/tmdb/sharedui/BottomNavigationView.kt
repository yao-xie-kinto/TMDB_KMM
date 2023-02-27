package com.yao.tmdb.sharedui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.yao.tmdb.sharedui.feature.SettingsScreen
import com.yao.tmdb.sharedui.feature.HomeScreen
import com.yao.tmdb.sharedui.feature.SearchScreen
import io.github.aakira.napier.Napier
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
internal fun BottomNavigationView(rootNavigator: Navigator, viewModel: ApplicationViewModel) {
    val navigator = rememberNavigator()
    val navBackStackEntry by navigator.currentEntry.collectAsState(null)
    val currentRoute = navBackStackEntry?.route?.route ?: ""
    Scaffold(
        bottomBar = {
            Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                BottomNavigation(
                    modifier = Modifier.height(55.dp + SafeArea.current.value.calculateBottomPadding())
                ) {
                    Tab.values().forEach { tab ->
                        BottomNavigationItem(
                            modifier = Modifier.padding(bottom = SafeArea.current.value.calculateBottomPadding()),
                            icon = {
                                Icon(
                                    imageVector = when (tab) {
                                        Tab.Home -> Icons.Default.Home
                                        Tab.Search -> Icons.Default.Search
                                        Tab.Settings -> Icons.Default.Settings
                                    },
                                    contentDescription = tab.toString()
                                )
                            },
                            label = { Text(tab.toString()) },
                            selected = currentRoute == tab.toString(),
                            onClick = {
                                navigator.navigate(route = tab.toString())
                            },
                            selectedContentColor = MaterialTheme.colors.onPrimary,
                            unselectedContentColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    ) {
        Napier.e {
            "navigator = $navigator"
        }
        NavHost(
            navigator = navigator,
            initialRoute = Tab.values().first().toString(),
            modifier = Modifier.padding(
                start = SafeArea.current.value.calculateStartPadding(
                    LayoutDirection.Ltr
                ),
                end = SafeArea.current.value.calculateEndPadding(LayoutDirection.Ltr),
                bottom = 55.dp + SafeArea.current.value.calculateBottomPadding()
            )
        ) {
            Tab.values().forEach { tab ->
                scene(tab.toString()) {
                    when (tab) {
                        Tab.Home -> HomeScreen(rootNavigator, viewModel.repository)
                        Tab.Search -> SearchScreen(rootNavigator, viewModel.repository)
                        Tab.Settings -> SettingsScreen()
                    }
                }
            }
        }
    }
}

enum class Tab {
    Home, Search, Settings,
}

enum class FullScreen {
    ShowDetail, ShowMore
}