package com.github.jordanpottruiff.nuclearphysics.events;

import com.google.common.base.Stopwatch;

import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventQueue {

    private final ScheduledExecutorService scheduler;
    private final PriorityBlockingQueue<TimeEvent> events;
    private long timeMillis;
    private boolean running;
    private boolean locked;

    public EventQueue(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
        events = new PriorityBlockingQueue<>();
        timeMillis = 0;
        running = false;
        locked = false;
    }

    public void addEvent(long delayMillis, Event e) {
        if (locked) {
            throw new IllegalStateException(
                    "Cannot add more events while waiting on existing ones to" +
                            " execute.");
        }
        events.add(new TimeEvent(timeMillis + delayMillis, e));
    }

    public void start() {
        running = true;
        doNextRecursively(0);
    }

    public void stop() {
        running = false;
    }

    public void doNextRecursively(long executionDelay) {
        if (!running) {
            return;
        }
        locked = true;
        getNextEvent().ifPresent(nextEvent -> {
            long deltaTime = nextEvent.getTimeMillis() - timeMillis;
            scheduler.schedule(() -> {
                locked = false;
                timeMillis += deltaTime;
                Stopwatch stopwatch = Stopwatch.createStarted();
                nextEvent.getEvent().executeAction();
                doNextRecursively(stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }, Math.max(deltaTime - executionDelay, 0), TimeUnit.MILLISECONDS);
        });
    }

    public Optional<TimeEvent> getNextEvent() {
        TimeEvent timeEvent;
        do {
            if (events.isEmpty()) {
                return Optional.empty();
            }
            timeEvent = events.remove();
        } while (!timeEvent.getEvent().isValid());
        return Optional.of(timeEvent);
    }

    private static final class TimeEvent implements Comparable<TimeEvent> {

        private final long timeMillis;
        private final Event event;

        public TimeEvent(long timeMillis, Event event) {
            this.timeMillis = timeMillis;
            this.event = event;
        }

        public Event getEvent() {
            return event;
        }

        public long getTimeMillis() {
            return timeMillis;
        }

        public int compareTo(TimeEvent other) {
            return Double.compare(getTimeMillis(), other.getTimeMillis());
        }
    }
}
