package tcp.echo;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ds on 2018-05-21.
 */

public class EchoTcpServer {

    public static void main(String... args) {

        try (ServerSocket serverSocket = new ServerSocket()) {

            InetSocketAddress inetSocketAddress = new InetSocketAddress(5000);
            serverSocket.bind(inetSocketAddress);

            System.out.println("Socket Initialize");

            Socket client = serverSocket.accept();
            System.out.println("Client Connection");

            try (
                    DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))
            ) {
                System.out.println("메시지를 입력하세요.");
                System.out.println("종료하시려면 Q, q를 입력하세요.");

                String message, res;

                while (true) {
                    System.out.print("입력 : ");
                    message = br.readLine();
                    dataOutputStream.writeUTF(message);
                    System.out.println(dataInputStream.readUTF());
                    if(message.equals("Q") || message.equals("q")) {
                        break;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
