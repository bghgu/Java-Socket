package tcp.half;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ds on 2018-05-21.
 */

public class TcpCient {

    public static void main(String... args) {
        try (Socket cliet = new Socket()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 5000);
            cliet.connect(inetSocketAddress);

            try (
                    DataOutputStream dataOutputStream = new DataOutputStream(cliet.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(cliet.getInputStream())
            ) {
                String res = dataInputStream.readUTF();
                System.out.println(res);

                String message = "I`m Client";
                dataOutputStream.writeUTF(message);

                message = "Nice to meet you!";
                dataOutputStream.writeUTF(message);

                res = dataInputStream.readUTF();
                System.out.println(res);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
