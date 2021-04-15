import java.util.Arrays;

public class Simulation {

    private final Particle[] particles;

    public Simulation(Particle[] particles) {
        this.particles = particles;
    }

    public String toJSON() {
        String[] particlesJSON = new String[this.particles.length];
        for(int i=0; i<particlesJSON.length; i++) {
            particlesJSON[i] = particles[i].toJSON();
        }
        return String.format("draw{\"particles\": %s, \"lines\": []}", Arrays.toString(particlesJSON));
    }
}
