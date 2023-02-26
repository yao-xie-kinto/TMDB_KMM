import Foundation
import SwiftUI
import KMMKit

@UIApplicationMain
class AppDelegate: NSObject, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        startKoin()
        let controller = AvoidDispose(RootViewControllersKt.getRootViewController(viewModel: koin.applicationViewModel))
        controller.view.backgroundColor = .white
        let window = UIWindow(frame: UIScreen.main.bounds)
        window.backgroundColor = .white
        window.rootViewController = controller
        window.makeKeyAndVisible()
        self.window = window
        return true
    }
}
