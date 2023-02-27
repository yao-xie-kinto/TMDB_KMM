package com.yao.tmdb.sharedui.component

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
internal fun ExpandingText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    expandable: Boolean = true,
    collapsedMaxLines: Int = 4,
    expandedMaxLines: Int = Int.MAX_VALUE,
) {
    var canTextExpand by remember(text) { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = text,
        style = textStyle,
        overflow = TextOverflow.Ellipsis,
        maxLines = if (expanded) expandedMaxLines else collapsedMaxLines,
        modifier = modifier
            .clickable(
                enabled = expandable && canTextExpand,
                onClick = { expanded = !expanded }
            ),
        onTextLayout = {
            if (!expanded) {
                canTextExpand = it.hasVisualOverflow
            }
        }
    )
}
