package com.yao.tmdb.sharedui

import platform.UIKit.*

actual class Platform actual constructor() {
    actual val platform: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
