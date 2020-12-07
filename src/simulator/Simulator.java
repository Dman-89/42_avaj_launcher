package simulator;

import aircraft.Flyable;
import exception.WrongNumberArgsException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.xml.bind.ValidationException;
import tower.WeatherTower;
import utils.Utils;

public final class Simulator {
    private static Queue<Flyable> unregisteredObservers = new ConcurrentLinkedQueue<>();
    private static List<Flyable> registerObserversWaitList = new LinkedList<>();
    private static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter(new File("simulation.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(final String[] args) throws IOException, ValidationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (args.length != 1) {
            throw new WrongNumberArgsException("Number of agruments in not 1");
        }
        final InputStreamReader reader = new InputStreamReader(new FileInputStream(args[0]));
        final WeatherTower weatherTower = new WeatherTower();
        long numIterations = Utils.validateAndParseInput(reader, weatherTower);
        while(numIterations-- > 0L) {
            weatherTower.conditionsChanged();
        }
        writer.close();
    }

    public static PrintWriter getWriter() {
        return writer;
    }

    public static Queue<Flyable> getUnregisteredObservers() {
        return unregisteredObservers;
    }

    public static List<Flyable> getRegisterObserversWaitList() {
        return registerObserversWaitList;
    }
}
