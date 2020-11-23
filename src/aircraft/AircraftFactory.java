package aircraft;

import utils.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.xml.bind.ValidationException;

public final class AircraftFactory {
    private AircraftFactory() {
    }

    public static Flyable newAircraft(String type, String name, int longitude, int latitude, int height) throws IllegalAccessException, ValidationException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException {
        Coordinates coordinates = new Coordinates(longitude, latitude, height);
        Class<?> clazz = Constants.AircraftTypes.class;
        Field[] aircraftTypes = clazz.getDeclaredFields();
        for(Field aircraftType: aircraftTypes)
            if (aircraftType.get(clazz).equals(type)) {
                Class<?> aircraftClass = Class.forName(Constants.PACKAGE + type);
                Constructor ctor = aircraftClass.getDeclaredConstructor(String.class, Coordinates.class);
                return (Flyable)ctor.newInstance(name, coordinates);
            }
        throw new ValidationException("unknown aircraft type");
    }
}

