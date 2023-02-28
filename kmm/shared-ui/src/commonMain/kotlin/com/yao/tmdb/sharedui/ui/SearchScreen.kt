package com.yao.tmdb.sharedui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.yao.tmdb.data.model.Show
import com.yao.tmdb.data.repo.Repository
import com.yao.tmdb.sharedui.FullScreen
import com.yao.tmdb.sharedui.base.BaseAction
import com.yao.tmdb.sharedui.base.BaseState
import com.yao.tmdb.sharedui.component.ColumnSpacer
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator

@Composable
internal fun SearchScreen(rootNavigator: Navigator, repository: Repository) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val (state, channel) = rememberPresenter { Presenter(repository, it) }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            elevation = 8.dp,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SearchBar(
                hint = "Search Movies",
                textState = textState
            ) {
                channel.trySend(SearchContract.Action.Search(it))
            }
        }
        ColumnSpacer(value = 16)
        ShowGrid(
            PaddingValues(0.dp),
            state.data,
        ) { rootNavigator.navigate(route = "/${FullScreen.ShowDetail}/$it") }
    }
}

@Composable
private fun SearchBar(
    hint: String,
    textState: MutableState<TextFieldValue>,
    onValueChange: (String) -> Unit
) {
    var textFieldFocusState by remember { mutableStateOf(false) }

    SearchInputText(
        textFieldValue = textState.value,
        onTextChanged = {
            textState.value = it
            onValueChange.invoke(it.text)
        },
        keyboardShown = textFieldFocusState,
        onTextFieldFocused = { focused ->
            textFieldFocusState = focused
        },
        hint = hint,
        focusState = textFieldFocusState
    )
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchInputText(
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    keyboardShown: Boolean,
    hint: String = "",
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean
) {
    var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }

    LaunchedEffect(keyboardController, keyboardShown) {
        keyboardController?.let {
            if (keyboardShown) it.show() else it.hide()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .semantics { keyboardShownProperty = keyboardShown }
            .background(Color.White),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .height(45.dp)
                .weight(1f)
                .align(Alignment.Bottom)
        ) {

            var lastFocusState by remember { mutableStateOf(Recomposer.State.Inactive) }

            BasicTextField(
                value = textFieldValue,
                onValueChange = { onTextChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
                    .onFocusEvent { state ->
                        // TODO:: Handle focus state
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                    keyboardType = keyboardType
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // TODO:: Invoke Search Action
                    }
                ),
                textStyle = MaterialTheme.typography.body2,
            )

            if (textFieldValue.text.isEmpty() && !focusState) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    text = hint,
                    style = MaterialTheme.typography.body2.copy(Color.Gray)
                )
            }
        }
    }
}

@Composable
private fun Presenter(
    repository: Repository,
    action: Flow<SearchContract.Action>,
): SearchContract.State {
    var data: List<Show> by remember { mutableStateOf(emptyList()) }

    action.collectAction {
        when (this) {
            is SearchContract.Action.Search -> {
                data = repository.searchMovies(this.keyword)
            }
            else -> {}
        }
    }

    return SearchContract.State(data = data)
}

interface SearchContract {
    sealed interface Action : BaseAction {
        data class Search(val keyword: String) : Action
    }

    data class State(
        val data: List<Show>
    ) : BaseState()

}