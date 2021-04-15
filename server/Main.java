import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new Server(80, Main::start);
    }

    private static void start(Server.Connection connection) {
        Thread thread = new Thread(() -> {
            try {
                double x = 10;
                double y = 50;
                double radius = 5;
                int[] color = new int[]{100, 100, 100};
                for(int i=0; i<20; i++) {
                    Thread.sleep(1000);
                    Particle particle = new Particle(x, y, radius, color);
                    Simulation simulation = new Simulation(new Particle[]{particle});
                    connection.sendMessage(simulation.toJSON());
                    x += 10;
                    y += 10;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
