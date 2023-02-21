package com.github.jordanpottruiff.nuclearphysics.graphics;

import com.github.jordanpottruiff.nuclearphysics.common.Color;

public class Circle {

    private final double x;
    private final double y;
    private final double radius;
    private final Color color;

    public Circle(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }
}
