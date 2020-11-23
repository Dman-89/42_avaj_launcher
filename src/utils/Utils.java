package utils;

import aircraft.AircraftFactory;
import aircraft.Flyable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.ValidationException;
import tower.WeatherTower;
import utils.Constants.AircraftTypes;

public final class Utils {
    private Utils() {
    }

    public static long validateAndParseInput(InputStreamReader var0, WeatherTower var1) throws IOException, ValidationException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException {
        BufferedReader var2 = new BufferedReader(var0);
        String var3 = var2.readLine();
        if (var3 != null && var3.matches("\\d+") && !var3.startsWith("0")) {
            long var4 = Long.valueOf(var3);
            Map var6 = createCountersMap();

            while((var3 = var2.readLine()) != null) {
                String[] var7 = var3.split(" ");
                validateFormat(var7);
                String var8 = var7[0];
                String var9 = var7[1];
                int var10 = Integer.valueOf(var7[2]);
                int var11 = Integer.valueOf(var7[3]);
                int var12 = Integer.valueOf(var7[4]);
                validateType(var8);
                validateName(var9, var8, var6);
                validateCoordinates(var10, var11, var12);
                Flyable var13 = AircraftFactory.newAircraft(var8, var9, var10, var11, var12);
                var13.registerTower(var1);
            }

            return var4;
        } else {
            throw new ValidationException("first line is null or contains invalid number: [" + var3 + "]");
        }
    }

    private static Map<String, Long> createCountersMap() throws IllegalAccessException {
        HashMap var0 = new HashMap(3);
        Class var1 = AircraftTypes.class;
        Field[] var2 = var1.getDeclaredFields();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field var5 = var2[var4];
            var0.put((String)var5.get(AircraftTypes.class), 1L);
        }

        return var0;
    }

    private static void validateFormat(String[] var0) throws ValidationException {
        if (var0.length != 5) {
            throw new ValidationException("wrong aircraft data format: " + Arrays.toString(var0));
        }
    }

    private static void validateType(String var0) throws ValidationException, IllegalAccessException {
        Class var1 = AircraftTypes.class;
        Field[] var2 = var1.getDeclaredFields();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field var5 = var2[var4];
            if (var0.equals(var5.get(AircraftTypes.class))) {
                return;
            }
        }

        throw new ValidationException("unknown aircraft type: " + var0);
    }

    private static void validateName(String var0, String var1, Map<String, Long> var2) throws ValidationException {
        if (!var0.matches("[A-Z][1-9]\\d*")) {
            throw new ValidationException("name doesn't match required format: [" + var0 + "]");
        } else if (var0.toUpperCase().charAt(0) != var1.toUpperCase().charAt(0)) {
            throw new ValidationException("name doesn't match aircraft type: [name=" + var0 + ", type=" + var1 + "]");
        } else {
            long var3 = (Long)var2.get(var1);
            if (var3 == Long.valueOf(var0.substring(1))) {
                var2.put(var1, ++var3);
            } else {
                throw new ValidationException("aircraft number is not chronologically ordered: expected " + var3 + ", found " + var0.substring(1) + "for aircraft " + var1 + " " + var0);
            }
        }
    }

    private static void validateCoordinates(int var0, int var1, int var2) throws ValidationException {
        if (var0 <= 0 || var1 <= 0 || var2 < 0) {
            throw new ValidationException("wrong coordinate values: [longitude: " + var0 + ", latitude: " + var1 + ", height: " + var2 + "]");
        }
    }
}

