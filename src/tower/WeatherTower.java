package tower;

import aircraft.Coordinates;
import weatherprovider.WeatherProvider;

public class WeatherTower extends Tower {
    public WeatherTower() {
    }

    public String getWeather(Coordinates var1) {
        return WeatherProvider.getProvider().getCurrentWeather(var1);
    }

    void changeWeather() {
        this.conditionsChanged();
    }
}
