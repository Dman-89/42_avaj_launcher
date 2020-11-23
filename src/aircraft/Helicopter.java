package aircraft;

import simulator.Simulator;
import tower.WeatherTower;

public class Helicopter extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);
        int prevHeight = this.coordinates.getHeight();
        switch(weather) {
            case "RAIN":
                this.coordinates = new Coordinates(this.coordinates.getLongitude() + 5, this.coordinates.getLatitude(), this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Rainy days never say goodbye, To desire when we are together");
                break;
            case "FOG":
                this.coordinates = new Coordinates(this.coordinates.getLongitude() + 1, this.coordinates.getLatitude(), this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Eto byl obychniy tuman? Konechno net, eto ne byl obychniy tuman");
                break;
            case "SUN":
                this.coordinates = new Coordinates(this.coordinates.getLongitude() + 10, this.coordinates.getLatitude(), this.coordinates.getHeight() + 2);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Oh, sunny days, Lift me when I'm down");
                break;
            case "SNOW":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 12);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): This is my December, This is my time of the year");
                break;
            default:
                throw new IllegalStateException("unknown weather type");
        }

        if (this.coordinates.getHeight() == 0 && prevHeight != 0) {
            this.weatherTower.unregister(this);
            Simulator.getUnregisteredObservers().add(this);
            Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): landing.");
            Simulator.getWriter().println("Tower says: " + this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ") unregistered from weather tower.");
        } else if (this.coordinates.getHeight() > 0 && prevHeight == 0) {
            Simulator.getRegisterObserversWaitList().add(this);
            Simulator.getWriter().println("Tower says: " + this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ") registered to weather tower.");
        }

    }

    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
        weatherTower.register(this);
        Simulator.getWriter().println("Tower says: " + this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ") registered to weather tower.");
    }
}
