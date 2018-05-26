package thread.digest;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ds on 2018-05-25.
 */
public class CallbackDigest implements Runnable {

    private String filename;

    public CallbackDigest(final String filename) {
        this.filename = filename;
    }

    /**
     * 콜백을 이용해 흐름제어
     * 작업이 끝났을 때 스레드가 직접 메인 프로그램에게 알려준다.
     * 메인 프로그램은 스레드가 종료되길 기다리는 동안 유휴상태로 머물 수 있으며 스레드의 시간으 뺏지 않아도 된다.
     */

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, sha);
            while (digestInputStream.read() != -1);
            digestInputStream.close();
            byte[] digest = sha.digest();
            /**
             * 스레드의 run메소드가 거의 끝났을 때 결과값과 함께 메인 프로그램의 메소드 호출
             * 메인 프로그램이 각각의 스레드에게 결과를 물어보는것이 아니라, 각각의 스레드가 메인 프로그램에 결과를 알려주는 방식
             */
            CallbackDigestUserInterface.receiveDigest(digest, filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
