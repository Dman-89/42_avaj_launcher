package aircraft;

import simulator.Simulator;
import tower.WeatherTower;

public class Baloon extends Aircraft implements Flyable {

    WeatherTower weatherTower;

    private Baloon(final String name, final Coordinates coordinates) {
        super(name, coordinates);
    }

    public void updateConditions() {
        String var1 = this.weatherTower.getWeather(this.coordinates);
        int var2 = this.coordinates.getHeight();
        byte var4 = -1;
        switch(var1.hashCode()) {
            case 69790:
                if (var1.equals("FOG")) {
                    var4 = 1;
                }
                break;
            case 82476:
                if (var1.equals("SUN")) {
                    var4 = 2;
                }
                break;
            case 2507668:
                if (var1.equals("RAIN")) {
                    var4 = 0;
                }
                break;
            case 2550147:
                if (var1.equals("SNOW")) {
                    var4 = 3;
                }
        }

        switch(var4) {
            case 0:
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 5, this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): All my sorrow and pain, I'll do my crying in the rain");
                break;
            case 1:
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 1, this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Friends become lovers, and lovers lose friends, That's when the fog rolls in!");
                break;
            case 2:
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude() + 10, this.coordinates.getHeight() + 2);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Lazy summer days... wash away, Under the spell of soft summer rain");
                break;
            case 3:
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 7);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): This ain't nothing but a winter jam, We're gonna celebrate as much as we can");
                break;
            default:
                throw new IllegalStateException("unknown weather type");
        }

        if (this.coordinates.getHeight() == 0 && var2 != 0) {
            this.weatherTower.unregister(this);
            Simulator.getUnregisteredObservers().add(this);
            Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): landing.");
            Simulator.getWriter().println("Tower says: " + this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ") unregistered from weather tower.");
        } else if (this.coordinates.getHeight() > 0 && var2 == 0) {
            Simulator.getRegisterObserversWaitList().add(this);
            Simulator.getWriter().println("Tower says: " + this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ") registered to weather tower.");
        }

    }

    public void registerTower(WeatherTower var1) {
        this.weatherTower = var1;
        var1.register(this);
        Simulator.getWriter().println("Tower says: " + this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ") registered to weather tower.");
    }}
