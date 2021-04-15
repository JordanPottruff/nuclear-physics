import java.util.Arrays;

public class Particle {
    private final double x;
    private final double y;
    private final double radius;
    private final int[] color;

    public Particle(double x, double y, double radius, int[] color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public String toJSON() {
        return String.format("{\"x\": %s, \"y\": %s, \"radius\": %s, \"color\": %s}", this.x, this.y, this.radius, Arrays.toString(this.color));
    }
}
