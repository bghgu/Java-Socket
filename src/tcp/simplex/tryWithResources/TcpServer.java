package tcp.simplex.tryWithResources;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ds on 2018-05-21.
 */

public class TcpServer {

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket()) {

            InetSocketAddress inetSocketAddress = new InetSocketAddress(5000);
            serverSocket.bind(inetSocketAddress);

            System.out.println("Initialize complete");

            Socket client = serverSocket.accept();
            System.out.println("Connection");

            try(OutputStream outputStream = client.getOutputStream();
                InputStream inputStream = client.getInputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream)) {

                String message = "Hello World";
                byte[] data = message.getBytes();
                //outputStream.write(data, 0, data.length);

                dataOutputStream.writeUTF(message);

                data = new byte[2];
                //inputStream.read(data, 0, data.length);
                String res = dataInputStream.readUTF();

                message = new String(data);
                String out = String.format("receive - %s", res);
                System.out.println(out);
            }
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
