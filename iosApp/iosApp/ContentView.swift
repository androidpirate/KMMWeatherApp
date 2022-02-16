import SwiftUI
import shared

struct ContentView: View {
	let greeting = Greeting()
	@State var greet = "Loading..."

	let platform = Platform()

	func loadCurrentWeather() {
	    platform.fetchCurrentWeather(city: "London") { result, error in
	        if let result = result {
	            self.greet = result.coord.lat.description
	        } else if let error = error {
	            greet = "Error: \(error)"
	        }
	    }
	}

	var body: some View {
        Text(greet).onAppear() {
            loadCurrentWeather()
        }
    }
}



struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}