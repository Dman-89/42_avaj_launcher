package aircraft;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.xml.bind.ValidationException;

import utils.Constants.AircraftTypes;

public final class AircraftFactory {
    private AircraftFactory() {
    }

    public static Flyable newAircraft(String type, String name, int var2, int var3, int var4) throws IllegalAccessException, ValidationException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException {
        Coordinates var5 = new Coordinates(var2, var3, var4);
        Class<?> clazz = AircraftTypes.class;
        Field[] var7 = clazz.getDeclaredFields();
        int var8 = var7.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            Field var10 = var7[var9];
            if (var10.get(AircraftTypes.class).equals(type)) {
                Class<?> var11 = Class.forName(AircraftTypes.PACKAGE + type);
                Constructor var12 = var11.getDeclaredConstructor(String.class, Coordinates.class);
                return (Flyable)var12.newInstance(name, var5);
            }
        }

        throw new ValidationException("unknown aircraft type");
    }
}

