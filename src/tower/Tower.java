package tower;

import aircraft.Flyable;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import simulator.Simulator;

public class Tower {
    private Queue<Flyable> observers = new ConcurrentLinkedQueue<>();

    public void register(Flyable flyable) {
        if (!this.observers.contains(flyable))
            this.observers.add(flyable);
    }

    public void unregister(Flyable flyable) {
        this.observers.remove(flyable);
    }

    public void conditionsChanged() {
        for (Flyable landedAircraft: Simulator.getUnregisteredObservers())
            landedAircraft.updateConditions();
        for (Flyable observer: observers)
            observer.updateConditions();

        observers.addAll(Simulator.getRegisterObserversWaitList());
        Simulator.getUnregisteredObservers().removeAll(Simulator.getRegisterObserversWaitList());
        Simulator.getRegisterObserversWaitList().clear();
    }
}

