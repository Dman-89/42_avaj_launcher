package tower;

import aircraft.Flyable;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import simulator.Simulator;

public class Tower {
    private Queue<Flyable> observers = new ConcurrentLinkedQueue();

    public Tower() {
    }

    public void register(Flyable var1) {
        if (!this.observers.contains(var1)) {
            this.observers.add(var1);
        }

    }

    public void unregister(Flyable var1) {
        this.observers.remove(var1);
    }

    public void conditionsChanged() {
        Iterator var1 = Simulator.getUnregisteredObservers().iterator();

        Flyable var2;
        while(var1.hasNext()) {
            var2 = (Flyable)var1.next();
            var2.updateConditions();
        }

        var1 = this.observers.iterator();

        while(var1.hasNext()) {
            var2 = (Flyable)var1.next();
            var2.updateConditions();
        }

        this.observers.addAll(Simulator.getRegisterObserversWaitList());
        Simulator.getUnregisteredObservers().removeAll(Simulator.getRegisterObserversWaitList());
        Simulator.getRegisterObserversWaitList().clear();
    }
}

