package aircraft;

import coordinates.Coordinates;
import tower.WeatherTower;

public class Baloon extends Aircraft implements Flyable {

    private Baloon(final String name, final Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {

    }

    @Override
    public void registerTower(final WeatherTower weatherTower) {

    }
}
