import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        new Server(80, Main::start);
    }

    private static void start(Server.Connection connection) {
        Thread thread = new Thread(() -> {
            try {
                for(int i=0; i<20; i++) {
                    Thread.sleep(1000);
                    System.out.println("Hello world");
                    connection.sendMessage("Hello world");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
