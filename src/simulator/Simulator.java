package simulator;

import aircraft.Flyable;
import exception.WrongNumberArgsException;
import java.io.BufferedReader;
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
    private static Queue<Flyable> unregisteredObservers = new ConcurrentLinkedQueue();
    private static List<Flyable> registerObserversWaitList = new LinkedList();
    private static PrintWriter writer;

    public Simulator() {
    }

    public static void main(String[] var0) throws IOException, ValidationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (var0.length != 1) {
            throw new WrongNumberArgsException("Number of agruments in not 1");
        } else {
            InputStreamReader var1 = new InputStreamReader(new FileInputStream(var0[0]) {
            });
            new BufferedReader(var1);
            WeatherTower var3 = new WeatherTower();
            long var4 = Utils.validateAndParseInput(var1, var3);

            while(var4-- > 0L) {
                var3.conditionsChanged();
            }

            writer.close();
        }
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

    static {
        try {
            writer = new PrintWriter(new File("simulation.txt"));
        } catch (FileNotFoundException var1) {
            var1.printStackTrace();
            System.exit(1);
        }

    }
}
