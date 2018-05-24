package tcp.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ds on 2018-05-21.
 */

public class EchoTcpClient {

    public static void main(String... args) {
        try (Socket client = new Socket()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 5000);
            client.connect(inetSocketAddress);

            try (
                    DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(client.getInputStream())
                    ) {
                String res;
                while (true) {
                    res = dataInputStream.readUTF();
                    System.out.println(res);
                    dataOutputStream.writeUTF(res);
                    if (res.equals("Q") || res.equals("q")) break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
