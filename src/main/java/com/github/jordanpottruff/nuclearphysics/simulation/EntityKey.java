package com.github.jordanpottruff.nuclearphysics.simulation;

import java.util.Objects;

public class EntityKey<T> {

    private final Class<T> typeKey;
    private final String identifierKey;

    private EntityKey(Class<T> typeKey, String identifierKey) {
        this.typeKey = typeKey;
        this.identifierKey = identifierKey;
    }

    public static <T> EntityKey<T> of(Class<T> typeKey, String identifierKey) {
        return new EntityKey<>(typeKey, identifierKey);
    }

    public Class<T> getTypeKey() {
        return typeKey;
    }

    public String getIdentifierKey() {
        return identifierKey;
    }

    @Override
    public String toString() {
        return String.format("typeKey: %s\nidentifierKey: %s\n", typeKey,
                identifierKey);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof EntityKey<?>)) {
            return false;
        }
        EntityKey<?> otherCasted = (EntityKey<?>) other;
        return typeKey.equals(otherCasted.typeKey) &&
                identifierKey.equals(otherCasted.identifierKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeKey, identifierKey);
    }
}
