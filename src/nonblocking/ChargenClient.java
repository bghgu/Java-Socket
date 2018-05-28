package nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by ds on 2018-05-28.
 */

/**
 * 채널 기반 문자 발생기 클라이언트
 */

public class ChargenClient {

    public static int DEFAULT_PORT = 5000;

    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Usage: Java ChargenClient host [port]");
            return;
        }

        int port;

        try {
            port = Integer.parseInt(args[1]);
        } catch (RuntimeException e) {
            port = DEFAULT_PORT;
        }

        try {
            SocketAddress address = new InetSocketAddress(args[0], port);
            //채널 객체 생성
            //채널은 블록(Block mode)로 열린다.
            //채널을 사용하여 채널 자체에 직접 데이터를 쓸 수 있다.
            SocketChannel client = SocketChannel.open(address);

            //74바이트 크기의 ByteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(74);
            WritableByteChannel out = Channels.newChannel(System.out);

            //채널의 read 메소드에 ByteBuffer 객체 전달
            //채널은 소켓으로부터 읽은 데이터로 버퍼를 채운다.
            while (client.read(buffer) != -1) {
                //버퍼에 데이터를 온전하게 남겨두고
                //해당 버퍼에 데이터를 읽기 위한 준비가 아닌 해당 버퍼의 데이터를 쓰기 위한 준비
                buffer.flip();
                out.write(buffer);
                //해당 버퍼를 초기 상태로 되돌린다.
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
