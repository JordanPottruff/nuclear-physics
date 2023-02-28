package com.github.jordanpottruff.nuclearphysics.simulation;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SetMultimap;

import java.util.Collection;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;

class SimulationState {

    final SetMultimap<EntityKey<?>, Object> entityMap;

    public SimulationState() {
        entityMap = HashMultimap.create();
    }

    public ImmutableList<Map.Entry<EntityKey<?>, Object>> getAll() {
        return ImmutableList.copyOf(entityMap.entries());
    }

    public <T> ImmutableList<T> get(EntityKey<T> key) {
        return entityMap.get(key).stream().map(key.getTypeKey()::cast)
                .collect(toImmutableList());
    }

    public <T> void add(EntityKey<T> key, T entity) {
        entityMap.put(key, entity);
    }

    public <T> void addAll(EntityKey<T> key, Collection<T> entities) {
        entityMap.putAll(key, entities);
    }
}
