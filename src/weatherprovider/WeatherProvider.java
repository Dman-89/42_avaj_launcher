package weatherprovider;

import aircraft.Coordinates;

public class WeatherProvider {
    private static WeatherProvider instance;
    private final String[] weather = new String[]{"RAIN", "FOG", "SUN", "SNOW"};

    private WeatherProvider() {
    }

    public static WeatherProvider getProvider() {
        if (instance == null) {
            instance = new WeatherProvider();
        }
        return instance;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        byte index = (byte) ((coordinates.getLatitude() % 4 +
                             coordinates.getLongitude() % 4 +
                             coordinates.getHeight() % 4 *
                             Math.random()) % 4);
        return this.weather[index];
    }
}
