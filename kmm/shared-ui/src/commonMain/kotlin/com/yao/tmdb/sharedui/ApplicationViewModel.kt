package com.yao.tmdb.sharedui

import com.yao.tmdb.data.repo.TrendingRepository
import org.brightify.hyperdrive.multiplatformx.BaseViewModel

class ApplicationViewModel(
    val repository: TrendingRepository
) : BaseViewModel()