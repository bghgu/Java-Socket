package thread.digest;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ds on 2018-05-25.
 */

public class DigestThread extends Thread {

    private String filename;

    public DigestThread(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, sha);
            while (digestInputStream.read() != -1);
            digestInputStream.close();

            byte[] digest = sha.digest();
            StringBuilder result = new StringBuilder(filename);
            result.append(": ").append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        for(String filename : args) {
            Thread t = new DigestThread(filename);
            t.start();
        }
    }

}
