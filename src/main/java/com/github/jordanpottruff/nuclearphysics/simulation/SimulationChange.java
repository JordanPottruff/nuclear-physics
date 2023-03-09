package com.github.jordanpottruff.nuclearphysics.simulation;

import java.util.Collection;
import java.util.Objects;

public class SimulationChange {

    private final long timeDelay;
    private final Entities entitiesAdded;
    private final Entities entitiesRemoved;

    private SimulationChange(long timeDelay, Entities entitiesAdded,
                             Entities entitiesRemoved) {
        this.timeDelay = timeDelay;
        this.entitiesAdded = entitiesAdded;
        this.entitiesRemoved = entitiesRemoved;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getTimeDelay() {
        return timeDelay;
    }

    public Entities getEntitiesAdded() {
        return entitiesAdded;
    }

    public Entities getEntitiesRemoved() {
        return entitiesRemoved;
    }

    @Override
    public String toString() {
        return String
                .format("timeDelay: %d\nentitiesAdded: %s\nentitiesRemoved: %s",
                        timeDelay, entitiesAdded, entitiesRemoved);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SimulationChange)) {
            return false;
        }
        SimulationChange otherCasted = (SimulationChange) other;
        return timeDelay == otherCasted.timeDelay &&
                entitiesAdded.equals(otherCasted.entitiesAdded) &&
                entitiesRemoved.equals(otherCasted.entitiesRemoved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeDelay, entitiesAdded, entitiesRemoved);
    }

    public static class Builder {

        private Long timeDelay;
        private Entities.Builder entitiesAdded;
        private Entities.Builder entitiesRemoved;

        public Builder() {
            entitiesAdded = Entities.newBuilder();
            entitiesRemoved = Entities.newBuilder();
        }

        public Builder setTimeDelay(long timeDelay) {
            this.timeDelay = timeDelay;
            return this;
        }

        public Builder setEntitiesAdded(Entities added) {
            this.entitiesAdded = added.toBuilder();
            return this;
        }

        public <T> Builder addEntity(EntityType<T> key, T entity) {
            entitiesAdded.add(key, entity);
            return this;
        }

        public <T> Builder addEntities(EntityType<T> key,
                                       Collection<T> entities) {
            entitiesAdded.addAll(key, entities);
            return this;
        }

        public Builder setEntitiesRemoved(Entities removed) {
            this.entitiesRemoved = removed.toBuilder();
            return this;
        }

        public <T> Builder removeEntities(EntityType<T> key,
                                          Collection<T> entities) {
            entitiesRemoved.addAll(key, entities);
            return this;
        }

        public <T> Builder removeEntity(EntityType<T> key, T entity) {
            entitiesRemoved.add(key, entity);
            return this;
        }

        public SimulationChange build() {
            if (timeDelay == null) {
                throw new IllegalStateException(
                        "Time delay must be defined for simulation changes.");
            }
            return new SimulationChange(timeDelay, entitiesAdded.build(),
                    entitiesRemoved.build());
        }
    }
}
