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

    public String getCurrentWeather(Coordinates var1) {
        byte var2 = (byte)((int)(((double)(var1.getHeight() % 4 + var1.getLatitude() % 4) + (double)(var1.getLongitude() % 4) * Math.random()) % 4.0D));
        return this.weather[var2];
    }
}
