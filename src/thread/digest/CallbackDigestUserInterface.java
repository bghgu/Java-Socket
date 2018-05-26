package thread.digest;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by ds on 2018-05-25.
 */
public class CallbackDigestUserInterface {

    public static void receiveDigest(final byte[] digest, final String name) {
        StringBuilder stringBuilder = new StringBuilder(name);
        stringBuilder.append(": ").append(DatatypeConverter.printHexBinary(digest));
        System.out.println(stringBuilder);
    }

    public static void main(String... args) {
        for(String filename : args) {
            CallbackDigest callbackDigest = new CallbackDigest(filename);
            Thread thread = new Thread(callbackDigest);
            thread.start();
        }
    }
}
