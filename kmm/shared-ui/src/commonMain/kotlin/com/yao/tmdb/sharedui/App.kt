package com.yao.tmdb.sharedui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import com.yao.tmdb.sharedui.screen.First
import com.yao.tmdb.sharedui.screen.HomeScreen
import com.yao.tmdb.sharedui.screen.Third
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun App() {
    val scope = rememberCoroutineScope()
    val navigator = rememberNavigator()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navBackStackEntry by navigator.currentEntry.collectAsState(null)
    val currentRoute = navBackStackEntry?.route?.route
    Scaffold(
        topBar = {
            Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                TopAppBar(
                    modifier = Modifier.padding(
                        start = SafeArea.current.value.calculateStartPadding(LayoutDirection.Ltr),
                        top = SafeArea.current.value.calculateTopPadding(),
                        end = SafeArea.current.value.calculateEndPadding(LayoutDirection.Ltr)
                    ),
                    title = { Text(currentRoute ?: "") },
                    navigationIcon = {
                        Icon(
                            Icons.Default.Menu,
                            "test",
                            modifier = Modifier.clickable(
                                onClick = {
                                    scope.launch { scaffoldState.drawerState.open() }
                                }
                            )
                        )
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
                items(Demo.values().size) {
                    val item = Demo.values()[it]
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
        NavHost(navigator = navigator, initialRoute = Demo.BottomNavigation.toString()) {
            Demo.values().forEach { screen ->
                scene(screen.toString()) {
                    if (screen == Demo.BottomNavigation) {
                        HomeScreen()
                    } else {
                        Box(
                            modifier = Modifier.padding(
                                start = SafeArea.current.value.calculateStartPadding(LayoutDirection.Ltr),
                                end = SafeArea.current.value.calculateEndPadding(LayoutDirection.Ltr),
                                bottom = SafeArea.current.value.calculateBottomPadding()
                            )
                        ) {
                            when (screen) {
                                Demo.First -> First()
                                Demo.Second -> First()
                                Demo.Third -> Third()
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class Demo {
    BottomNavigation,
    First,
    Second,
    Third
}
