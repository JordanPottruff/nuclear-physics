package com.github.jordanpottruff.nuclearphysics.events;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Event {

    private final Consumer<Long> actionFn;
    private final Supplier<Boolean> validationFn;

    public Event(Consumer<Long> actionFn, Supplier<Boolean> validationFn) {
        this.actionFn = actionFn;
        this.validationFn = validationFn;
    }

    public Event(Consumer<Long> actionFn) {
        this(actionFn, () -> true);
    }

    public Consumer<Long> getActionFn() {
        return actionFn;
    }

    public Supplier<Boolean> getValidationFn() {
        return validationFn;
    }

    boolean isValid() {
        return validationFn.get();
    }
}
