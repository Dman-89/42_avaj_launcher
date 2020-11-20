package aircraft;

import coordinates.Coordinates;
import tower.WeatherTower;

public class JetPlane extends Aircraft implements Flyable {

    WeatherTower weatherTower;

    private JetPlane(final String name, final Coordinates coordinates) {
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
