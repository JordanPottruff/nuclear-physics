package com.github.jordanpottruff.nuclearphysics.simulation;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import java.util.Optional;

public class InteractionRegistry {

    private final ImmutableTable<EntityType<?>, EntityType<?>,
            InteractionEvent<Object, Object>>
            interactionTable;

    private InteractionRegistry(
            Table<EntityType<?>, EntityType<?>, InteractionEvent<Object,
                    Object>> interactionTable) {
        this.interactionTable = ImmutableTable.copyOf(interactionTable);
    }

    <T1, T2> Optional<InteractionEvent<? super T1, ? super T2>> getInteraction(
            EntityType<T1> key1, EntityType<T2> key2) {
        return Optional.ofNullable(interactionTable.get(key1, key2));
    }

    ImmutableTable<EntityType<?>, EntityType<?>, InteractionEvent<Object,
            Object>> getInteractionTable(
                    
    )

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private final Table<EntityType<?>, EntityType<?>,
                InteractionEvent<Object, Object>>
                interactionTable;

        public Builder() {
            interactionTable = HashBasedTable.create();
        }

        public <T1, T2, K1 extends EntityType<T1>, K2 extends EntityType<T2>> Builder addInteraction(
                K1 key1, K2 key2, InteractionEvent<T1, T2> interaction) {
            // Add the given direction of the mapping.
            interactionTable
                    .put(key1, key2, castInteraction(interaction, key1, key2));
            // Add the inverted direction of the mapping.
            interactionTable.put(key2, key1,
                    castInteraction(interaction.getInverted(), key2, key1));
            return this;
        }

        private <T1, T2> InteractionEvent<Object, Object> castInteraction(
                InteractionEvent<T1, T2> interaction, EntityType<T1> key1,
                EntityType<T2> key2) {
            return (left, right) -> interaction
                    .getInteraction(key1.getTypeKey().cast(left),
                            key2.getTypeKey().cast(right));
        }

        public InteractionRegistry build() {
            return new InteractionRegistry(interactionTable);
        }
    }
}
