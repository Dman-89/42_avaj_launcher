package tower;

import aircraft.Flyable;

import java.util.LinkedList;

public class Tower {

    LinkedList<Flyable> observers = new LinkedList<>();

    public void register(final Flyable flyable) {

    }

    public void unregister(final Flyable flyable) {

    }

    public void conditionsChanged() {

    }
}
