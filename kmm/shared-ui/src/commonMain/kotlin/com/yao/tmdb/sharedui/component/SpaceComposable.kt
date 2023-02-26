package com.yao.tmdb.sharedui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ColumnSpacer(value: Int) = Spacer(modifier = Modifier.height(value.dp))

@Suppress("unused")
@Composable
internal fun RowSpacer(value: Int) = Spacer(modifier = Modifier.width(value.dp))
