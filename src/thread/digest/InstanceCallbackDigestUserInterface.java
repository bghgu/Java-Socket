package thread.digest;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by ds on 2018-05-27.
 */

public class InstanceCallbackDigestUserInterface {

    private String filename;
    private byte[] digest;

    public InstanceCallbackDigestUserInterface(final String filename) {
        this.filename = filename;
    }

    /**
     * 스레드를 추가하기 위해 추가
     * 생성자에서 스레드를 시작하는 것은 매우 위험하다.
     * 경쟁조건이 발생할 수 있다.
     * 이론적으로 발생한 가능성이 있다.
     * 하지만 생성자에서 스레드를 실행하는 구조를 피하는 것이 좋다.
     */
    public void calculateDigest() {
        InstanceCallbackDigest instanceCallbackDigest = new InstanceCallbackDigest(filename, this);
        Thread t = new Thread(instanceCallbackDigest);
        t.start();
    }

    /**
     * 콜백 값을 받아서 필드에 저장한다.
     * @param digest
     */
    void recieveDigest(byte[] digest) {
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(filename + ": ");
        if(digest != null) result.append(DatatypeConverter.printHexBinary(digest));
        else result.append("digest not available");
        return result.toString();
    }

    public static void main(String... args) {
        for(String filename : args) {
            InstanceCallbackDigestUserInterface instanceCallbackDigestUserInterface = new InstanceCallbackDigestUserInterface(filename);
            instanceCallbackDigestUserInterface.calculateDigest();
        }
    }
}
