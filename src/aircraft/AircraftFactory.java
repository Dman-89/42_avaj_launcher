package aircraft;

import utils.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import exception.ValidationException;

public final class AircraftFactory {

    private static String[] aircraftTypesArr;

    private AircraftFactory() {
    }

    public static Flyable newAircraft(String type, String name, int longitude, int latitude, int height) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException {
        final Coordinates coordinates = new Coordinates(longitude, latitude, height);
        String[] aircraftTypes = getAircraftTypes();
        Class<?> aircraftClass;
        Constructor<?> ctor;
        for(String aircraftType: aircraftTypes)
            if (aircraftType.equals(type)) {
                aircraftClass = Class.forName(Constants.PACKAGE + type);
                ctor = aircraftClass.getDeclaredConstructor(String.class, Coordinates.class);
                return (Flyable)ctor.newInstance(name, coordinates);
            }
        throw new ValidationException("unknown aircraft type");
    }

    public static String[] getAircraftTypes() throws IllegalAccessException {
        if (aircraftTypesArr != null)
            return aircraftTypesArr;
        final Class<?> clazz = Constants.AircraftTypes.class;
        final Field[] aircraftTypes = clazz.getDeclaredFields();
        aircraftTypesArr = new String[aircraftTypes.length];
        for (int i = 0; i < aircraftTypes.length;i++)
            aircraftTypesArr[i] = (String) aircraftTypes[i].get(Constants.AircraftTypes.class);
        return aircraftTypesArr;
    }
}

