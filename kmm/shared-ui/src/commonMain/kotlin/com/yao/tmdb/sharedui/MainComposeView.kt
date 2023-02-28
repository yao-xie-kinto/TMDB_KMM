package com.yao.tmdb.sharedui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.yao.tmdb.sharedui.ui.DrawerScreen
import com.yao.tmdb.sharedui.ui.ShowDetailScreen
import com.yao.tmdb.sharedui.ui.ShowMoreScreen
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MainComposeView(viewModel: ApplicationViewModel) {
    val scope = rememberCoroutineScope()
    val navigator = rememberNavigator()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navBackStackEntry by navigator.currentEntry.collectAsState(null)
    val currentRoute = navBackStackEntry?.route?.route
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.background(MaterialTheme.colors.primary).fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val route = currentRoute ?: ""
                TopAppBar(
                    elevation = 8.dp,
                    modifier = Modifier.padding(
                        start = SafeArea.current.value.calculateStartPadding(LayoutDirection.Ltr),
                        top = SafeArea.current.value.calculateTopPadding(),
                        end = SafeArea.current.value.calculateEndPadding(LayoutDirection.Ltr)
                    ),
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 80.dp)
                                .align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            text = when (route) {
                                Drawer.BottomNavigation.toString() -> "The Movies"
                                else -> {
                                    if (route.contains(FullScreen.ShowDetail.toString())) {
                                        "Detail"
                                    } else if (route.contains(FullScreen.ShowMore.toString())) {
                                        "More"
                                    } else {
                                        route
                                    }
                                }
                            }
                        )
                    },
                    navigationIcon = {
                        if (route.contains(FullScreen.ShowDetail.toString())
                            || route.contains(FullScreen.ShowMore.toString())
                        ) {
                            Icon(
                                Icons.Default.Close,
                                "Close",
                                modifier = Modifier.clickable(
                                    onClick = {
                                        navigator.popBackStack()
                                    }
                                )
                            )
                        }
                    }
                )
            }
        },
        drawerBackgroundColor = MaterialTheme.colors.background,
        drawerContent = {
            LazyColumn(
                modifier = Modifier.padding(
                    start = SafeArea.current.value.calculateStartPadding(LayoutDirection.Ltr),
                    top = SafeArea.current.value.calculateTopPadding()
                )
            ) {
                items(Drawer.values().size) {
                    val item = Drawer.values()[it]
                    ListItem(
                        text = { Text(item.toString()) },
                        modifier = Modifier.clickable {
                            navigator.navigate(route = item.toString())
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                    )
                    Divider()
                }
            }
        },
        scaffoldState = scaffoldState
    ) {
        NavHost(navigator = navigator, initialRoute = Drawer.BottomNavigation.toString()) {
            Drawer.values().forEach { screen ->
                scene(screen.toString()) {
                    when (screen) {
                        Drawer.BottomNavigation -> BottomNavigationView(navigator, viewModel)
                        Drawer.First -> DrawerScreen(screen.toString())
                        Drawer.Second -> DrawerScreen(screen.toString())
                        Drawer.Third -> DrawerScreen(screen.toString())
                    }
                }
            }
            scene(route = "/${FullScreen.ShowDetail}/{id}?") { backStackEntry ->
                val id: Int? = backStackEntry.path<Int>("id")
                id?.let {
                    ShowDetailScreen(viewModel.repository, it)
                }
            }
            scene(route = "/${FullScreen.ShowMore}/{type}?") { backStackEntry ->
                val type: String? = backStackEntry.path<String>("type")
                type?.let {
                    ShowMoreScreen(navigator, viewModel.repository, it)
                }
            }
        }
    }
}

enum class Drawer {
    BottomNavigation,
    First,
    Second,
    Third
}
