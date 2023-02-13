package com.yao.tmdb.domain

import com.yao.tmdb.data.Movie
import com.yao.tmdb.data.repo.MovieRepository
import com.yao.tmdb.domain.base.Spec
import com.yao.tmdb.domain.base.SpecPair
import com.yao.tmdb.domain.base.UseCaseFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge


class DiscoverMovieUseCase(
    private val repository: MovieRepository,
) : UseCaseFlow<List<Movie>>() {
    override fun <T> build(vararg params: SpecPair<T, () -> Spec<T>>): Flow<List<Movie>> {
//        val flow1 = flow {
//            emit(
//                MMKVDataStoreUtil.getFaq(language = params[0].value as String)
//                    .map {
//                        FaqContent(
//                            faqId = it.faqId,
//                            title = it.faqDigest.question,
//                            body = it.faqDigest.answer
//                        )
//                    }
//            )
//        }
        val flow1 = flow {
            emit(
                repository.discoverMovies()
            )
        }
        val flow2 = flow {
            emit(
                repository.discoverMovies()
            )
        }
        return listOf(flow1, flow2).merge()
    }
}