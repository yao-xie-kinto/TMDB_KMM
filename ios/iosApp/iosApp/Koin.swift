//
//  Koin.swift
//  iosApp
//
//  Created by Xie Yao on 2023/02/26.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import KMMKit

func startKoin() {
    let userDefaults = UserDefaults(suiteName: "TMDB_SETTINGS")!

    let koinApplication = DependencyInjectionKt.doInitKoinIos(userDefaults: userDefaults)
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}

