package aircraft;

import simulator.Simulator;
import tower.WeatherTower;

public class JetPlane extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    JetPlane(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);
        int prevHeight = this.coordinates.getHeight();
        switch(weather) {
            case "RAIN":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 5, this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): All my sorrow and pain, I'll do my crying in the rain");
                break;
            case "FOG":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 1, this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Friends become lovers, and lovers lose friends, That's when the fog rolls in!");
                break;
            case "SUN":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 10, this.coordinates.getHeight() + 2);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Lazy summer days... wash away, Under the spell of soft summer rain");
                break;
            case "SNOW":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 7);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): This ain't nothing but a winter jam, We're gonna celebrate as much as we can");
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

    public void registerTower(WeatherTower var1) {
        this.weatherTower = var1;
        var1.register(this);
        Simulator.getWriter().println("Tower says: " + this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ") registered to weather tower.");
    }
}
