package com.github.jordanpottruff.nuclearphysics.simulation;

import com.github.jordanpottruff.nuclearphysics.events.EventQueue;
import com.google.common.collect.ImmutableSet;

import java.util.concurrent.ScheduledExecutorService;

public class SimulationRunner {

    private final SimulationState simulationState;
    private final InteractionRegistry interactionRegistry;
    private final EventQueue eventQueue;

    public SimulationRunner(SimulationState simulationState,
                            InteractionRegistry interactionRegistry,
                            ScheduledExecutorService executorService) {
        this.simulationState = simulationState;
        this.interactionRegistry = interactionRegistry;
        this.eventQueue = new EventQueue(executorService);
    }

    public void start() {
        ImmutableSet<EntityType<?>> entityTypes = simulationState.getTypes();
         

    }


}
