package weatherprovider;

import coordinates.Coordinates;

public class WeatherProvider {

    WeatherProvider instance;
    String[] weather;

    private WeatherProvider() {
    }

    public WeatherProvider getProvider() {
        if (instance == null) {
            instance = new WeatherProvider();
        }
        return instance;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        return null;
    }
}
