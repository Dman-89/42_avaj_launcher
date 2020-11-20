package aircraft;

import coordinates.Coordinates;
import tower.WeatherTower;
import weatherprovider.WeatherProvider;

public class Helicopter extends Aircraft implements Flyable {

    WeatherTower weatherTower;

    private Helicopter(final String name, final Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {

    }

    @Override
    public void registerTower(final WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
    }
}
