package com.github.jordanpottruff.nuclearphysics.graphics;

import com.google.common.collect.ImmutableSet;

public class Scene {

    private final ImmutableSet<Circle> circles;
    private final ImmutableSet<Line> lines;

    public Scene(Iterable<Circle> circles, Iterable<Line> lines) {
        this.circles = ImmutableSet.copyOf(circles);
        this.lines = ImmutableSet.copyOf(lines);
    }

    public ImmutableSet<Circle> getCircles() {
        return circles;
    }

    public ImmutableSet<Line> getLines() {
        return lines;
    }
}
