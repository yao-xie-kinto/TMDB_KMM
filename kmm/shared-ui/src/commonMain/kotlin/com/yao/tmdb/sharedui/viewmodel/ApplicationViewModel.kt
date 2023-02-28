package com.yao.tmdb.sharedui.viewmodel

import com.yao.tmdb.data.repo.Repository
import org.brightify.hyperdrive.multiplatformx.BaseViewModel

class ApplicationViewModel(
    val repository: Repository
) : BaseViewModel()