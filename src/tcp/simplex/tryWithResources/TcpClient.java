package tcp.simplex.tryWithResources;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by ds on 2018-05-21.
 */

public class TcpClient {
    public static void main(String... args) {
        try(Socket client = new Socket()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 5000);
            client.connect(inetSocketAddress);

            try(OutputStream outputStream = client.getOutputStream();
                InputStream inputStream = client.getInputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream)) {

                byte[] data = new byte[11];
                //inputStream.read(data, 0, 11);
                String res = dataInputStream.readUTF();

                String message = new String(data);
                String out = String.format("recieve - %s", res);
                System.out.println(out);

                message = "OK";
                data = message.getBytes();
                //outputStream.write(data, 0, data.length);
                dataOutputStream.writeUTF(message);

            }
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
