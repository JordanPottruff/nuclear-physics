package com.github.jordanpottruff.nuclearphysics.simulation;

import java.util.Optional;

@FunctionalInterface
public interface InteractionEvent<A, B> {

    Optional<SimulationChange> getInteraction(A a, B b);

    default InteractionEvent<B, A> getInverted() {
        return (b, a) -> getInteraction(a, b);
    }
}
