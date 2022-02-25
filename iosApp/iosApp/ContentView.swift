import SwiftUI
import shared

struct LoadingView: View {
    var body: some View {
       return ProgressView()
    }
}

struct ContentView: View {
	let greeting = Greeting()
    let platform = Platform()
    
	@State var greet = "Loading..."
    @State var city = "City"
    @State var imageUrl = ""
    @State var temp = "Temp"
    @State var minTemp = "Minimum Temperature"
    @State var maxTemp = "Maximum Temperature"
    @State var weatherCondition = "Weather Condition"
    @State var weatherConditionDesc = "Weather condition description"
    @State var isLoading = false
    @State var isError = false
    
    // How to display a progress bar before fetching the data
    func displayLoadingScreen() {
        // May be just display greet here?
    }

	func loadCurrentWeather() {
	    platform.fetchCurrentWeather(city: "Detroit") { result, error in
	        if let result = result {
                self.city = result.city
                self.temp = result.temp.description + " C"
                self.imageUrl = result.weatherConditionIconUri
                self.minTemp = "Min Temp: " + result.minTemp.description
                self.maxTemp = "Max Temp:" + result.maxTemp.description
                self.weatherCondition = result.weatherCondition
                self.weatherConditionDesc = result.weatherConditionDesc
	        } else if let error = error {
	            greet = "Error: \(error)"
	        }
            isLoading = false
	    }
	}

    var body: some View {
        // How to add a background to the container?
        VStack {
            if(!isLoading) {
                VStack {
                    // How to add the icon dynamically to the image?
                    if #available(iOS 15, *) {
                        AsyncImage(url: URL(string: imageUrl))
                    }
                    Text(city)
                        .padding(5)
                        .font(.headline)
                    Text(temp)
                        .padding(5)
                    Text(weatherCondition)
                        .font(.body)
                    Text(weatherConditionDesc.capitalized)
                        .font(.caption)
                    Text(minTemp)
                        .padding(5)
                    Text(maxTemp)
                        .padding(5)
                }
            
            
            } else {
                ProgressView()
            }
        }.onAppear {
            isLoading = true
            loadCurrentWeather()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.blue)
        .padding(.zero)
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
