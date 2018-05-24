package tcp.threadPool;

import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ds on 2018-05-22.
 */

public class SimpleSocketClient {

    private static final int PORT = 5000;
    private static final String HOST = "127.0.0.1";

    public static void main(String... args) {
        try (Socket socket = new Socket()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
            socket.connect(inetSocketAddress);
            try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
                String res = dataInputStream.readUTF();
                System.out.println(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
