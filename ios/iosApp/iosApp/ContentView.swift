import SwiftUI
import KMMKit

struct ContentView: View {
	let greet = "Greetings!"

	var body: some View {
		Text(greet)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}