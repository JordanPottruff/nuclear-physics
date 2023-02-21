package com.github.jordanpottruiff.nuclearphysics.graphics;

import com.github.jordanpottruiff.nuclearphysics.common.Color;
import com.google.common.collect.ImmutableSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class WindowRenderer implements Renderer {

    private final RenderPanel panel;

    private WindowRenderer(RenderPanel panel) {
        this.panel = panel;
    }

    public static WindowRenderer create(int width, int height) {
        RenderPanel panel = new RenderPanel(width, height);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Nuclear physics");
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(width, height);
            frame.setVisible(true);
        });
        return new WindowRenderer(panel);
    }

    @Override
    public void render(Scene scene, Color background) {
        panel.scene = scene;
        panel.background = background;
        panel.repaint();
    }

    private static class RenderPanel extends JPanel {

        private final int width;
        private final int height;

        private Scene scene;
        private Color background;

        private RenderPanel(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            if (background != null) {
                g2.setColor(toAwtColor(background));
                g2.fillRect(0, 0, height, width);
            }
            if (scene != null) {
                drawCircles(g2, scene.getCircles());
                drawLines(g2, scene.getLines());
            }
        }

        private void drawCircles(Graphics2D g2, ImmutableSet<Circle> circles) {
            for (Circle circle : circles) {
                g2.setStroke(new BasicStroke(0));
                g2.setColor(toAwtColor(circle.getColor()));
                Shape shape =
                        new Ellipse2D.Double(circle.getX() - circle.getRadius(),
                                circle.getY() - circle.getRadius(),
                                circle.getRadius() * 2.0,
                                circle.getRadius() * 2.0);
                g2.fill(shape);
            }
        }

        private void drawLines(Graphics2D g2, ImmutableSet<Line> lines) {
            for (Line line : lines) {
                g2.setStroke(new BasicStroke((float) line.getWidth(),
                        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                Color color = line.getColor();
                g2.setColor(toAwtColor(color));
                g2.drawLine(round(line.getX1()), round(line.getY1()),
                        round(line.getX2()), round(line.getY2()));
            }
        }

        private static int round(double value) {
            return Math.round((float) value);
        }

        private static java.awt.Color toAwtColor(Color color) {
            return new java.awt.Color((float) color.getRed(),
                    (float) color.getGreen(), (float) color.getBlue());
        }
    }
}
