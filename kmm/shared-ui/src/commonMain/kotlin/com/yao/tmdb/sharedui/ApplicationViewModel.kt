package com.yao.tmdb.sharedui

import com.yao.tmdb.data.repo.HomeRepository
import org.brightify.hyperdrive.multiplatformx.BaseViewModel

class ApplicationViewModel(
    val repository: HomeRepository
) : BaseViewModel() {
}