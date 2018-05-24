package tcp.half;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ds on 2018-05-21.
 */

public class TcpServer {

    public static void main(String... args) {
        try (ServerSocket serverSocket = new ServerSocket()) {

            InetSocketAddress inetSocketAddress = new InetSocketAddress(5000);
            serverSocket.bind(inetSocketAddress);

            System.out.println("Socket Initialize");

            Socket client = serverSocket.accept();
            System.out.println("Client Connection");

            try (
                    DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(client.getInputStream())
            ) {
                String message = "I`m Server";
                dataOutputStream.writeUTF(message);

                String res = dataInputStream.readUTF();
                System.out.println(res);

                res = dataInputStream.readUTF();
                System.out.println(res);

                message = "Nice Meet you, too.";
                dataOutputStream.writeUTF(message);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
