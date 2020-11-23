package aircraft;

import simulator.Simulator;
import tower.WeatherTower;

public class Baloon extends Aircraft implements Flyable {

    WeatherTower weatherTower;

    Baloon(final String name, final Coordinates coordinates) {
        super(name, coordinates);
    }

    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);
        int prevHeight = this.coordinates.getHeight();
        switch(weather) {
            case "RAIN":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 5);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): All my sorrow and pain, I'll do my crying in the rain");
                break;
            case "FOG":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 3);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Friends become lovers, and lovers lose friends, That's when the fog rolls in!");
                break;
            case "SUN":
                this.coordinates = new Coordinates(this.coordinates.getLongitude() + 2, this.coordinates.getLatitude(), this.coordinates.getHeight() + 4);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Lazy summer days... wash away, Under the spell of soft summer rain");
                break;
            case "SNOW":
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 15);
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
    }}
