import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    private final ServerSocket server;

    public Server(int port, Consumer<Connection> onConnect) throws IOException {
        this.server = new ServerSocket(port);
        while(true) {
            Socket connection = server.accept();
            try {
                Connection client = new Connection(connection);
                onConnect.accept(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Connection {
        private static final String PATTERN_DELIMITER = "\\r\\n\\r\\n";
        private static final String PATTERN_WEB_SOCKET_KEY = "Sec-WebSocket-Key: (.*)";
        private final Socket client;
        private final Scanner in;
        private final OutputStream out;

        private Connection(Socket client) throws IOException {
            this.client = client;
            this.in = new Scanner(client.getInputStream()).useDelimiter(PATTERN_DELIMITER);
            this.out = client.getOutputStream();
            this.handShake();
            this.sendMessage("Hello!");
        }

        public boolean sendMessage(String message) {
            byte[] payloadSegment = message.getBytes();
            int payloadLen = payloadSegment.length;
            byte[] lengthSegment = encodeMessageLength(payloadSegment);
            int lengthLen = lengthSegment.length;

            byte[] reply = new byte[1 + lengthLen + payloadLen];
            reply[0] = (byte) 129;
            System.arraycopy(lengthSegment, 0, reply, 1, lengthLen);
            System.arraycopy(payloadSegment, 0, reply, 1+lengthLen, payloadLen);

            try {
                this.out.write(reply, 0, reply.length);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        private byte[] encodeMessageLength(byte[] message) {
            int len = message.length;
            byte[] frameSegment;
            if(message.length <= 125){
                // Single byte representation for lengths <= 125.
                frameSegment = new byte[1];
                frameSegment[0] = (byte) message.length;
            }else if(message.length <= 65535){
                // Message lengths requiring two bytes uses next two bytes.
                frameSegment = new byte[3];
                frameSegment[0] = (byte) 126; // Indicates next two bytes store length.
                frameSegment[1] = (byte)((len >> 8 ) & (byte)255);
                frameSegment[2] = (byte)(len & (byte)255);
            }else{
                // Message lengths requiring more bytes uses next eight bytes.
                frameSegment = new byte[9];
                frameSegment[0] = (byte) 127; // Indicates next eight bytes store length.
                for(int i=0; i<8; i++) {
                    frameSegment[i] = (byte)((len >> (56-i*8)) & (byte)255);
                }
            }
            return frameSegment;
        }

        private void handShake() throws IOException {
            String handShakeRequest = this.in.next();
            Matcher match = Pattern.compile(PATTERN_WEB_SOCKET_KEY).matcher(handShakeRequest);
            match.find();
            String response = getHandShakeResponse(match.group(1));
            System.out.println(response);
            byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
            this.out.write(responseBytes, 0, responseBytes.length);
        }

        private String getHandShakeResponse(String key) {
            String encodedKey = "";
            try {
                encodedKey = Base64.getEncoder().encodeToString(
                        MessageDigest.getInstance("SHA-1")
                                .digest((key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                                        .getBytes(StandardCharsets.UTF_8)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "HTTP/1.1 101 Switching Protocols\r\n"
                + "Connection: Upgrade\r\n"
                + "Upgrade: websocket\r\n"
                + "Sec-WebSocket-Accept: "
                + encodedKey
                + "\r\n\r\n";
        }
    }
}