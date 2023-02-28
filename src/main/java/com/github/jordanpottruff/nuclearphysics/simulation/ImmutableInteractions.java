package com.github.jordanpottruff.nuclearphysics.simulation;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import java.util.Optional;

public class ImmutableInteractions {

    private final ImmutableTable<EntityKey<?>, EntityKey<?>,
            Interaction<Object, Object>>
            interactionTable;

    private ImmutableInteractions(
            Table<EntityKey<?>, EntityKey<?>, Interaction<Object, Object>> interactionTable) {
        this.interactionTable = ImmutableTable.copyOf(interactionTable);
    }

    <T1, T2> Optional<Interaction<T1, T2>> getInteraction(EntityKey<T1> key1,
                                                          EntityKey<T2> key2) {
        return Optional.ofNullable(interactionTable.get(key1, key2))
                // Coerce to the expected Interaction type.
                .map(interaction -> Interaction.<T1, T2>newBuilder()
                        .setInteractionFn(interaction.getInteractionFn())
                        .setValidationFn(interaction.getValidationFn())
                        .build());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private final Table<EntityKey<?>, EntityKey<?>, Interaction<Object,
                Object>>
                interactionTable;

        public Builder() {
            interactionTable = HashBasedTable.create();
        }

        public <T1, T2, K1 extends EntityKey<T1>, K2 extends EntityKey<T2>> Builder addInteraction(
                K1 key1, K2 key2, Interaction<T1, T2> interaction) {
            // Add the given direction of the mapping.
            interactionTable
                    .put(key1, key2, castInteraction(interaction, key1, key2));
            // Add the inverted direction of the mapping.
            interactionTable.put(key2, key1,
                    castInteraction(interaction.invert(), key2, key1));
            return this;
        }

        private <T1, T2> Interaction<Object, Object> castInteraction(
                Interaction<T1, T2> interaction, EntityKey<T1> key1,
                EntityKey<T2> key2) {
            // We are effectively upcasting the Interaction by creating a
            // new interaction that downcasts the arguments of the two
            // underlying functions to the expected type.
            return Interaction.newBuilder().setInteractionFn(
                    (left, right) -> interaction.getInteractionFn()
                            .apply(key1.getTypeKey().cast(left),
                                    key2.getTypeKey().cast(right)))
                    .setValidationFn(
                            (left, right) -> interaction.getValidationFn()
                                    .apply(key1.getTypeKey().cast(left),
                                            key2.getTypeKey().cast(right)))
                    .build();
        }

        public ImmutableInteractions build() {
            return new ImmutableInteractions(interactionTable);
        }
    }
}
