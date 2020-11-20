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
        byte index = (byte) (
                coordinates.getHeight() % 4 +
                coordinates.getLatitude() % 4 +
                coordinates.getLongitude() % 4
        );
        return weather[index];
    }
}
