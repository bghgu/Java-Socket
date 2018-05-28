package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by ds on 2018-05-28.
 */

/**
 * 하나의 소켓으로 보내기와 받기 모두를 할 수 있다.
 * 하나의 소켓으로 동시에 다수의 호스트에 대해 보내거나 받을 수 있다.
 */

//입력된 모든 내용을 보낸다.
public class UDPDiscardClient {
    public static final int PORT = 5000;
    public static void main(String... args) {
        String hostname = args.length > 0 ? args[0] : "localhost";

        try (DatagramSocket theSocket = new DatagramSocket()) {
            InetAddress server = InetAddress.getByName(hostname);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String theLine = userInput.readLine();
                if (theLine.equals(".")) break;
                byte[] data = theLine.getBytes();
                DatagramPacket theOutput = new DatagramPacket(data, data.length, server, PORT);
                //패킷 전달
                theSocket.send(theOutput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
