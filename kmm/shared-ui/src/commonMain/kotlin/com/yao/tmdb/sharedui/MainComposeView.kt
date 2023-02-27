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
import com.yao.tmdb.sharedui.feature.DetailScreen
import com.yao.tmdb.sharedui.feature.FavouriteScreen
import com.yao.tmdb.sharedui.feature.HomeScreen
import com.yao.tmdb.sharedui.feature.SearchScreen
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavHost
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
            Napier.e {
                "navigator = $navigator"
            }
            Demo.values().forEach { screen ->
                scene(screen.toString()) {
                    when (screen) {
                        Demo.BottomNavigation -> BottomNavigationView(navigator, viewModel)
                        Demo.First -> HomeScreen(navigator, viewModel.repository)
                        Demo.Second -> SearchScreen()
                        Demo.Third -> FavouriteScreen()
                        Demo.ShowDetail -> DetailScreen()
                        Demo.ShowMore -> SearchScreen()
                        else -> {}
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
    Third,
    ShowDetail,
    ShowMore
}
