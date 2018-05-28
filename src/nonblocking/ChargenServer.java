package nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ds on 2018-05-28.
 */

public class ChargenServer {

    public static int DEFAULT_PORT = 5000;

    public static void main(String... args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException e) {
            port = DEFAULT_PORT;
        }

        System.out.println("Listening for connections on port " + port);
        byte[] rotation = new byte[190];

        for(byte i = ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        ServerSocketChannel serverChannel;
        Selector selector;

        try {
            //서버채널 생성
            serverChannel = ServerSocketChannel.open();
            //서버소켓을 얻어 온다.
            ServerSocket ss = serverChannel.socket();
            //바인드
            ss.bind(new InetSocketAddress(port));
            //연결을 논블록 모드로 변경
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                try {
                    if(key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        //다수의 동시 연결을 허용할 수 있도록 클라이언트 채널을 논블록
                        client.configureBlocking(false);
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.put(rotation, 0, 72);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip();
                        key2.attach(buffer);
                        //클라이언트 버퍼를 설정한다.
                    } else if(key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();

                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if(!buffer.hasRemaining()) {
                            //다음 줄로 버퍼를 채운다.
                            buffer.rewind();
                            //이전에 보낸 줄의 시작 문자를 구한다.
                            int first = buffer.get();
                            //버퍼의 데이터를 변경할 준비를 한다.
                            buffer.rewind();
                            //rotation 에서 새로운 시작 문자의 위치를 찾는다.
                            int position = first - ' ' + 1;
                            //rotation 에서 buffer로 데이터를 복사한다.
                            buffer.put(rotation, position, 72);
                            //버퍼의 마지막에 라인 구분자를 저장한다.
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            //버퍼를 출력할 준비를 한다.
                            buffer.flip();
                        }
                        client.write(buffer);
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                        cex.printStackTrace();
                    }
                }
            }
        }
    }
}
