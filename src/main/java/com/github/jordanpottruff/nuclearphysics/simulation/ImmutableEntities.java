package com.github.jordanpottruff.nuclearphysics.simulation;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

import java.util.Collection;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class ImmutableEntities {

    private final ImmutableSetMultimap<EntityKey<?>, Object> entityMap;

    private ImmutableEntities(
            ImmutableSetMultimap<EntityKey<?>, Object> entityMap) {
        this.entityMap = entityMap;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(entityMap);
    }

    public ImmutableList<Map.Entry<EntityKey<?>, Object>> getAll() {
        return ImmutableList.copyOf(entityMap.entries());
    }

    public <T> ImmutableList<T> get(EntityKey<T> key) {
        return entityMap.get(key).stream().map(key.getTypeKey()::cast)
                .collect(toImmutableList());
    }

    @Override
    public String toString() {
        return entityMap.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ImmutableEntities)) {
            return false;
        }
        ImmutableEntities otherCasted = (ImmutableEntities) other;
        return entityMap.equals(otherCasted.entityMap);
    }

    @Override
    public int hashCode() {
        return entityMap.hashCode();
    }

    public static class Builder {

        final SetMultimap<EntityKey<?>, Object> entityMap;

        public Builder() {
            entityMap = HashMultimap.create();
        }

        private Builder(SetMultimap<EntityKey<?>, Object> entityMap) {
            this.entityMap = HashMultimap.create(entityMap);
        }

        public <T> Builder add(EntityKey<T> key, T entity) {
            entityMap.put(key, entity);
            return this;
        }

        public <T> Builder addAll(EntityKey<T> key, Collection<T> entities) {
            entityMap.putAll(key, entities);
            return this;
        }

        public ImmutableEntities build() {
            return new ImmutableEntities(
                    ImmutableSetMultimap.copyOf(entityMap));
        }
    }
}
