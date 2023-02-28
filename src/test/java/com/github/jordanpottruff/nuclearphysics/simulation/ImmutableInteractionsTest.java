package com.github.jordanpottruff.nuclearphysics.simulation;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImmutableInteractionsTest {

    @Test
    public void handlesSuperClassing() {
        EntityKey<Entity> entity1Key = EntityKey.of(Entity.class, "ENTITY-1");
        EntityKey<Entity> entity2Key = EntityKey.of(Entity.class, "ENTITY-2");

        BiFunction<EntityParent, EntityParent, Optional<SimulationChange>>
                interactionFn = (a, b) -> Optional
                .of(SimulationChange.newBuilder().setTimeDelay(1000)
                        .addEntity(entity1Key, new Entity(a.name + "-child", 0))
                        .addEntity(entity2Key, new Entity(b.name + "-child", 0))
                        .build());


        Interaction<Entity, Entity> interaction =
                Interaction.<Entity, Entity>newBuilder()
                        .setInteractionFn(interactionFn).build();

        var interactions = ImmutableInteractions.newBuilder()
                .addInteraction(entity1Key, entity2Key, interaction).build();


        Interaction<Entity, Entity> returnedEntity =
                interactions.getInteraction(entity1Key, entity2Key).get();
        assertEquals(returnedEntity.getInteractionFn()
                        .apply(new Entity("joe", 20), new Entity("bob", 20)).get(),
                SimulationChange.newBuilder().setTimeDelay(1000)
                        .addEntity(entity1Key, new Entity("joe-child", 0))
                        .addEntity(entity2Key, new Entity("bob-child", 0))
                        .build());
    }

    private static class EntityParent {
        public final String name;

        public EntityParent(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof EntityParent)) {
                return false;
            }
            EntityParent otherEntity = (EntityParent) other;
            return name.equals(otherEntity.name);
        }
    }

    private static class Entity extends EntityParent {
        public final int age;

        public Entity(String name, int age) {
            super(name);
            this.age = age;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Entity)) {
                return false;
            }
            Entity otherEntity = (Entity) other;
            return age == otherEntity.age && name.equals(otherEntity.name);
        }
    }
}
