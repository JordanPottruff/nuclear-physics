package com.github.jordanpottruiff.nuclearphysics;

import com.github.jordanpottruiff.nuclearphysics.common.Color;
import com.github.jordanpottruiff.nuclearphysics.graphics.Circle;
import com.github.jordanpottruiff.nuclearphysics.graphics.Scene;
import com.github.jordanpottruiff.nuclearphysics.graphics.WindowRenderer;
import com.google.common.collect.ImmutableSet;

public class Main {

    public static void main(String[] args) throws Exception {
        WindowRenderer renderer = WindowRenderer.create(1000, 1000);

        Color background = new Color(1.0, 0, 0);

        Circle a = new Circle(1, 1, 20, new Color(0.0, 0.0, 1.0));
        for (int i = 0; i < 1000; i++) {
            a = new Circle(a.getX() + 1, a.getY() + 1, a.getRadius(),
                    a.getColor());
            renderer.render(new Scene(ImmutableSet.of(a), ImmutableSet.of()),
                    background);
            Thread.sleep(1);
        }
    }
}
