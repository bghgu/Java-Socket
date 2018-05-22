package tcp.duplex.threadPool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by ds on 2018-05-22.
 */

/**
 * 소켓 처리용 래퍼 클래스
 */
public class ConnectionWrap implements Runnable {

    private Socket socket = null;

    public ConnectionWrap(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //응답을 위해 스트림을 얻어 온다
        try(DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            dataOutputStream.writeUTF(new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
