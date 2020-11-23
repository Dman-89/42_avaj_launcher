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

    public static long validateAndParseInput(InputStreamReader reader, WeatherTower weatherTower) throws IOException, ValidationException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String s = bufferedReader.readLine();
        if (s == null || !s.matches("\\d+") || s.startsWith("0"))
            throw new ValidationException("first line is null or contains invalid number: [" + s + "]");
        long numIterations = Long.valueOf(s);
        Map<String, Long> counters = createCountersMap();
        while((s = bufferedReader.readLine()) != null) {
            String[] splitString = s.split(" ");
            validateFormat(splitString);
            String type = splitString[0];
            String name = splitString[1];
            int longitude = Integer.valueOf(splitString[2]);
            int latitude = Integer.valueOf(splitString[3]);
            int height = Integer.valueOf(splitString[4]);
            validateType(type);
            validateName(name, type, counters);
            validateCoordinates(longitude, latitude, height);
            Flyable aircraft = AircraftFactory.newAircraft(type, name, longitude, latitude, height);
            aircraft.registerTower(weatherTower);
        }
        return numIterations;
    }

    private static Map<String, Long> createCountersMap() throws IllegalAccessException {
        Map<String, Long> map = new HashMap<>(3);
        Class<?> clazz = AircraftTypes.class;
        Field[] aircraftTypes = clazz.getDeclaredFields();
        for(Field aircraftType: aircraftTypes)
            map.put((String)aircraftType.get(clazz), 1L);
        return map;
    }

    private static void validateFormat(String[] splitString) throws ValidationException {
        if (splitString.length != 5)
            throw new ValidationException("wrong aircraft data format: " + Arrays.toString(splitString));
    }

    private static void validateType(String type) throws ValidationException, IllegalAccessException {
        Class<?> clazz = AircraftTypes.class;
        Field[] aircraftTypes = clazz.getDeclaredFields();
        for(Field aircraftType: aircraftTypes)
            if (type.equals(aircraftType.get(AircraftTypes.class)))
                return;
        throw new ValidationException("unknown aircraft type: " + type);
    }

    private static void validateName(String name, String type, Map<String, Long> counters) throws ValidationException {
        if (!name.matches("[A-Z][1-9]\\d*"))
            throw new ValidationException("name doesn't match required format: [" + name + "]");
        else if (name.toUpperCase().charAt(0) != type.toUpperCase().charAt(0))
            throw new ValidationException("name doesn't match aircraft type: [name=" + name + ", type=" + type + "]");
        else {
            long expectedNumber = counters.get(type);
            if (expectedNumber == Long.valueOf(name.substring(1)))
                counters.put(type, ++expectedNumber);
            else
                throw new ValidationException("aircraft number is not chronologically ordered: expected " + expectedNumber + ", found " + name.substring(1) + "for aircraft " + type + " " + name);
        }
    }

    private static void validateCoordinates(int longitude, int latitude, int height) throws ValidationException {
        if (longitude <= 0 || latitude <= 0 || height < 0) {
            throw new ValidationException("wrong coordinate values: [longitude: " + longitude + ", latitude: " + latitude + ", height: " + height + "]");
        }
    }
}

