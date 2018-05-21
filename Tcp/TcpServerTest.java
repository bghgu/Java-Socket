import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ds on 2018-05-21.
 * 서버 소켓
 * InputStream, OutputStream 두 스트림을 통해 프로세스간 통신(입출력)이 이루어진다.
 */

public class TcpServerTest {

    public static void main(String[] args) {
        //NULL 서버 소캣 생성
        ServerSocket serverSocket = null;
        try {
            //서버 소켓을 생성하고 5000번 포트 bind
            serverSocket = new ServerSocket(5000);
            System.out.println(getTime() + " 서버가 준비되었습니다.");
        }catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println(getTime() + " 연결요청을 기다립니다.");
                //서버 소켓은 클라이언트의 연결 요청이 들어올 때 까지 실행을 멈추고 계속 기다린다.
                //클라이언트의 연결 요청이 들어오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + socket.getInetAddress() + " 연결 요청");

                //소켓의 출력 스트림을 얻는다.
                OutputStream out = socket.getOutputStream();
                //기본형 단위로 처리하는 보조스트림
                DataOutputStream dos = new DataOutputStream(out);

                //원격 소켓(remote socket), 클라리언트에 데이터를 보낸다.
                dos.writeUTF("서버로부터의 메시지입니다.");
                System.out.println(getTime() + " 데이터를 전송했습니다.");

                //스트림 종료
                dos.close();
                out.close();
                //소켓 종료
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}
