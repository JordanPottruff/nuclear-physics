package com.github.jordanpottruff.nuclearphysics.simulation;

import com.google.common.collect.*;

import java.util.Collection;
import java.util.HashMap;

import static com.google.common.collect.ImmutableList.toImmutableList;

class SimulationState {

    final SetMultimap<EntityType<?>, Object> typeToEntityMap;
    final HashMap<Object, Long> entityToModifiedTimeMap;

    public SimulationState() {
        typeToEntityMap = HashMultimap.create();
        entityToModifiedTimeMap = new HashMap<>();
    }

    public ImmutableMultimap<EntityType<?>, Object> getAll() {
        return ImmutableMultimap.copyOf(typeToEntityMap);
    }

    public <T> ImmutableList<T> get(EntityType<T> key) {
        return typeToEntityMap.get(key).stream().map(key.getTypeKey()::cast)
                .collect(toImmutableList());
    }

    public long getLastModifiedTime(Object entity) {
        return entityToModifiedTimeMap.get(entity);
    }

    <T> void add(EntityType<T> key, T entity, long time) {
        typeToEntityMap.put(key, entity);
        entityToModifiedTimeMap.put(entity, time);
    }

    <T> void addAll(EntityType<T> key, Collection<T> entities, long time) {
        typeToEntityMap.putAll(key, entities);
        entities.forEach(entity -> entityToModifiedTimeMap.put(entity, time));
    }

    ImmutableSet<EntityType<?>> getTypes() {
        return ImmutableSet.copyOf(typeToEntityMap.keySet());
    }
}
