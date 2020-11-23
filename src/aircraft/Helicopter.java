package aircraft;

import simulator.Simulator;
import tower.WeatherTower;

public class Helicopter extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    Helicopter(String var1, Coordinates var2) {
        super(var1, var2);
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
                this.coordinates = new Coordinates(this.coordinates.getLongitude() + 5, this.coordinates.getLatitude(), this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Rainy days never say goodbye, To desire when we are together");
                break;
            case 1:
                this.coordinates = new Coordinates(this.coordinates.getLongitude() + 1, this.coordinates.getLatitude(), this.coordinates.getHeight());
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Eto byl obychniy tuman? Konechno net, eto ne byl obychniy tuman");
                break;
            case 2:
                this.coordinates = new Coordinates(this.coordinates.getLongitude() + 10, this.coordinates.getLatitude(), this.coordinates.getHeight() + 2);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): Oh, sunny days, Lift me when I'm down");
                break;
            case 3:
                this.coordinates = new Coordinates(this.coordinates.getLongitude(), this.coordinates.getLatitude(), this.coordinates.getHeight() - 12);
                Simulator.getWriter().println(this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + "): This is my December, This is my time of the year");
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
    }
}
