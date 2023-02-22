package com.github.jordanpottruiff.nuclearphysics;

import com.github.jordanpottruiff.nuclearphysics.common.Color;
import com.github.jordanpottruiff.nuclearphysics.events.Event;
import com.github.jordanpottruiff.nuclearphysics.events.EventQueue;
import com.github.jordanpottruiff.nuclearphysics.graphics.Circle;
import com.github.jordanpottruiff.nuclearphysics.graphics.Scene;
import com.github.jordanpottruiff.nuclearphysics.graphics.WindowRenderer;
import com.google.common.collect.ImmutableSet;

public class Main {

    public static void main(String[] args) throws Exception {
        //        WindowRenderer renderer = WindowRenderer.create(1000, 1000);
        //        EventQueue queue = new EventQueue(Executors
        //        .newScheduledThreadPool(3));
        //        addEvent(renderer, queue, 0);
        //        System.out.println("Start");
        //queue.start();

        double[] answer = reflect(5, 0, -1, 0);
        System.out.println(answer[0]);
        System.out.println(answer[1]);
    }

    private static double result(double cx, double cy, double vx, double vy,
                                 double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return (vx * (y1 - cy) - vy * (x1 - cx)) / (vy * dx - vx * dy);
    }

    private static double[] reflect(double vx, double vy, double nx,
                                    double ny) {
        double common = 2 * (vx * nx + vy * ny);
        double rx = vx - nx * common;
        double ry = vy - ny * common;
        return new double[]{rx, ry};
    }

    private static void addEvent(WindowRenderer windowRenderer,
                                 EventQueue queue, int i) {
        Circle a = new Circle(1 + i, 1 + i, 20, new Color(0.0, 0.0, 1.0));
        Color background = new Color(1.0, 0, 0);
        queue.addEvent(5, new Event(() -> {
            windowRenderer
                    .render(new Scene(ImmutableSet.of(a), ImmutableSet.of()),
                            background);
            addEvent(windowRenderer, queue, i + 1);
        }));
    }


}
