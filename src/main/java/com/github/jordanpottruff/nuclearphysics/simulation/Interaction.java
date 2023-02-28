package com.github.jordanpottruff.nuclearphysics.simulation;

import java.util.Optional;
import java.util.function.BiFunction;

public class Interaction<T1, T2> {
    private final BiFunction<? super T1, ? super T2, Optional<SimulationChange>>
            interactionFn;
    private final BiFunction<? super T1, ? super T2, Boolean> validationFn;

    private Interaction(
            BiFunction<? super T1, ? super T2, Optional<SimulationChange>> interactionFn,
            BiFunction<? super T1, ? super T2, Boolean> validationFn) {
        this.interactionFn = interactionFn;
        this.validationFn = validationFn;
    }

    public static <T1, T2> Builder<T1, T2> newBuilder() {
        return new Builder<>();
    }

    BiFunction<? super T1, ? super T2, Optional<SimulationChange>> getInteractionFn() {
        return interactionFn;
    }

    BiFunction<? super T1, ? super T2, Boolean> getValidationFn() {
        return validationFn;
    }

    Interaction<T2, T1> invert() {
        return new Interaction<>((a, b) -> interactionFn.apply(b, a),
                (a, b) -> validationFn.apply(b, a));
    }

    public static class Builder<T1, T2> {

        private BiFunction<? super T1, ? super T2, Optional<SimulationChange>>
                interactionFn;
        private BiFunction<? super T1, ? super T2, Boolean> validationFn;

        public Builder<T1, T2> setInteractionFn(
                BiFunction<? super T1, ? super T2,
                        Optional<SimulationChange>> interactionFn) {
            this.interactionFn = interactionFn;
            return this;
        }

        public Builder<T1, T2> setValidationFn(
                BiFunction<? super T1, ? super T2, Boolean> validationFn) {
            this.validationFn = validationFn;
            return this;
        }

        public Interaction<T1, T2> build() {
            return new Interaction<>(
                    interactionFn == null ? (a, b) -> Optional.empty() :
                            interactionFn,
                    validationFn == null ? (a, b) -> true : validationFn);
        }
    }
}
