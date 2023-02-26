import Foundation
import SwiftUI
import KMMKit

@UIApplicationMain
class AppDelegate: NSObject, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        startKoin()

        let controller = AvoidDispose(RootViewControllersKt.RootViewController())
        controller.view.backgroundColor = .white
        let window = UIWindow(frame: UIScreen.main.bounds)
        window.backgroundColor = .white
        window.rootViewController = controller
        window.makeKeyAndVisible()
        self.window = window
        
        return true
    }
}

func startKoin() {
    let userDefaults = UserDefaults(suiteName: "DROIDCON_SETTINGS")!

    let koinApplication = DependencyInjectionKt.doInitKoinIos(userDefaults: userDefaults)
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}