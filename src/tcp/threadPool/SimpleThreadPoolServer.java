package tcp.threadPool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ds on 2018-05-22.
 */

public class SimpleThreadPoolServer {

    private static final int PORT = 5000;
    private static final int THREAD_CNT = 5;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);

    private static int count = 0;

    public static void main(String... args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {

            //소켓 서버가 종료될 때 까지 무한반복
            while (true) {
                Socket client = serverSocket.accept();
                //요청이 오면 스레드 풀의 스레드로 소켓을 넣어준다
                //이후는 스레드 내에서 처리한다
                try {
                    count++;
                    System.out.println(count);
                    threadPool.execute(new ConnectionWrap(client));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }
}

class ConnectionWrap implements Runnable {

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

