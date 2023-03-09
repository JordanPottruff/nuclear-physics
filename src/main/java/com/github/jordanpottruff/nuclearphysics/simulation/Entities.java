package com.github.jordanpottruff.nuclearphysics.simulation;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

import java.util.Collection;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class Entities {

    private final ImmutableSetMultimap<EntityType<?>, Object> entityMap;

    private Entities(ImmutableSetMultimap<EntityType<?>, Object> entityMap) {
        this.entityMap = entityMap;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(entityMap);
    }

    public ImmutableList<Map.Entry<EntityType<?>, Object>> getAll() {
        return ImmutableList.copyOf(entityMap.entries());
    }

    public <T> ImmutableList<T> get(EntityType<T> key) {
        return entityMap.get(key).stream().map(key.getTypeKey()::cast)
                .collect(toImmutableList());
    }

    @Override
    public String toString() {
        return entityMap.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Entities)) {
            return false;
        }
        Entities otherCasted = (Entities) other;
        return entityMap.equals(otherCasted.entityMap);
    }

    @Override
    public int hashCode() {
        return entityMap.hashCode();
    }

    public static class Builder {

        final SetMultimap<EntityType<?>, Object> entityMap;

        public Builder() {
            entityMap = HashMultimap.create();
        }

        private Builder(SetMultimap<EntityType<?>, Object> entityMap) {
            this.entityMap = HashMultimap.create(entityMap);
        }

        public <T> Builder add(EntityType<T> key, T entity) {
            entityMap.put(key, entity);
            return this;
        }

        public <T> Builder addAll(EntityType<T> key, Collection<T> entities) {
            entityMap.putAll(key, entities);
            return this;
        }

        public Entities build() {
            return new Entities(ImmutableSetMultimap.copyOf(entityMap));
        }
    }
}
