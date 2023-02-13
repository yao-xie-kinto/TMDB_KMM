package com.yao.tmdb.feature

import com.yao.tmdb.data.model.Response
import com.yao.tmdb.domain.DiscoverMovieUseCase
import com.yao.tmdb.domain.base.BaseViewModel
import com.yao.tmdb.domain.base.BasicUiState
import com.yao.tmdb.domain.base.spec
import com.yao.tmdb.domain.base.stringSpec
import org.koin.core.component.inject


open class DiscoverMovieViewModel :
    BaseViewModel<DiscoverMovieContract.Event, DiscoverMovieContract.State, DiscoverMovieContract.Effect>() {
    // Multiple Use Cases can be defined here
    private val useCase: DiscoverMovieUseCase by inject()

    // IN
    private lateinit var language: String

    // OUT
    private lateinit var faqList: List<DiscoverMovieContent>

    init {
        setEvent(DiscoverMovieContract.Event.DiscoverMovie("popularity.desc"))
    }

    override fun createInitialState(): DiscoverMovieContract.State =
        DiscoverMovieContract.State(
            uiState = BasicUiState.Idle,
            isChanged = false
        )

    override fun handleEvent(event: DiscoverMovieContract.Event) {
        when (event) {
            is DiscoverMovieContract.Event.DiscoverMovie -> getFaq(event.sort_by)
            is DiscoverMovieContract.Event.Expand -> toggleExpand(event.content)
            DiscoverMovieContract.Event.Retry -> language.apply {
                getFaq(this)
            }
        }
    }

    private fun getFaq(language: String) {
        try {
            this.language = language
//            Napier.v("$funName, language = $language", null, TAG.HTTP_CLIENT)
            setState { copy(uiState = BasicUiState.Loading) }
            collect(
                flow = useCase(language spec ::stringSpec),
                callback = {
                    when (it) {
                        is Response.Success -> {
                            setState {
                                copy(
                                    // TODO: Set up the Empty View
//                                    uiState = if (it.data.isEmpty()) BasicUiState.Empty
//                                    else BasicUiState.Success(it.data)

//                                    uiState = BasicUiState.Success(it.data)
                                    uiState = BasicUiState.Success(emptyList())
                                )
                            }
//                            this.faqList = it.data
                            this.faqList = emptyList()
                        }
                        is Response.Error -> {
                            setState { copy(uiState = (BasicUiState.Error())) }
//                            Napier.e("$funName, error = ${it.exception.message}", null, TAG.HTTP_CLIENT)
                        }
                    }
                }
            )
        } catch (e: Exception) {
//            Napier.e("$funName, error = ${e.message}", null, TAG.HTTP_CLIENT)
        }
    }

    private fun toggleExpand(faq: DiscoverMovieContent) {
        val newList = mutableListOf<DiscoverMovieContent>()
        this.faqList.forEach {
            newList.add(if (it.title == faq.title) it.toggleExpand() else it)
        }
        setState {
            copy(uiState = BasicUiState.Success(newList))
        }
        this.faqList = newList
    }
}