package com.github.jordanpottruff.nuclearphysics.graphics;

import com.github.jordanpottruff.nuclearphysics.common.Color;

public class Line {

    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final double width;
    private final Color color;

    public Line(double x1, double y1, double x2, double y2, double width,
                Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = width;
        this.color = color;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public double getWidth() {
        return width;
    }

    public Color getColor() {
        return color;
    }
}
