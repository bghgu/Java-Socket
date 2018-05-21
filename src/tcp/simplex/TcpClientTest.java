package tcp.simplex;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Created by ds on 2018-05-21.
 * 클라이언트 소켓
 */

public class TcpClientTest {

    public static void main(String[] args) {
        try {
            String serverIP = "127.0.0.1";
            System.out.println("서버에 연결중입니다. 서버 IP : " + serverIP);

            //소켓을 생성하여 연결을 요청한다.
            //ip, 포트번호
            Socket socket = new Socket(serverIP, 5000);

            //소켓의 입력 스트림을 얻는다.
            InputStream in = socket.getInputStream();
            //기본형 단위로 처리하는 보조 스트림
            DataInputStream dis = new DataInputStream(in);

            System.out.println("서버로 부터 받은 메시지 : " + dis.readUTF());
            System.out.println("연결을 종료합니다.");

            //스트림 종료
            in.close();
            dis.close();
            //소켓 종료
            socket.close();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
