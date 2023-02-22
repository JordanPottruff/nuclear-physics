package com.github.jordanpottruiff.nuclearphysics.events;

import java.util.function.Supplier;

public class Event {

    private final Runnable actionFn;
    private final Supplier<Boolean> isValidFn;

    public Event(Runnable actionFn, Supplier<Boolean> isValidFn) {
        this.actionFn = actionFn;
        this.isValidFn = isValidFn;
    }

    public Event(Runnable actionFn) {
        this(actionFn, () -> true);
    }

    public Runnable getActionFn() {
        return actionFn;
    }

    void executeAction() {
        actionFn.run();
    }

    public Supplier<Boolean> getIsValidFn() {
        return isValidFn;
    }

    boolean isValid() {
        return isValidFn.get();
    }
}
