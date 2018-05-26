package thread.digest;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ds on 2018-05-25.
 */

public class ReturnDigest extends Thread {
    private String filename;
    private byte[] digest;

    public ReturnDigest(final String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            while (digestInputStream.read() != -1);
            digestInputStream.close();
            digest = messageDigest.digest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] getDigest() {
        return digest;
    }
}
