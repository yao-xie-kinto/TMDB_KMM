package com.yao.tmdb.sharedui.state

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun Error(
    msg: String,
    onTryAgain: () -> Unit = {}
) {
    val color = Color(0xFFFF8C00)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = msg,
                style = MaterialTheme.typography.h5,
                color = color
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = onTryAgain,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = color,
                    contentColor = Color.White,
                    disabledContentColor = Color.LightGray
                )
            ) {
                Text(
                    text = "Try again",
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun Error_Preview() {
//    Error("An error has occurred")
//}