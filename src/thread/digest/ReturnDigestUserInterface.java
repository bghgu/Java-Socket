package thread.digest;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by ds on 2018-05-25.
 */
public class ReturnDigestUserInterface {

    /**
     *
     * @param args
     */
    public static void main(String... args) {
        /**
         * 원하는 결과가 나오지 않는다.
         * 흐름제어의 오류
         */
        for(String filename : args) {
            ReturnDigest returnDigest = new ReturnDigest(filename);
            returnDigest.start();
        }

        /**
         * 폴링 기법으로 해결
         */
        ReturnDigest[] digests = new ReturnDigest[args.length];
        for(int i = 0; i < args.length; i++) {
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }
        for(int i = 0; i < args.length; i++) {
            while (true) {
                byte[] digest = digests[i].getDigest();
                //주기적으로 검사
                if(digest != null) {
                    StringBuilder stringBuilder = new StringBuilder(args[i]);
                    stringBuilder.append(": ").append(DatatypeConverter.printHexBinary(digest));
                    System.out.println(stringBuilder);
                    break;
                }
            }
        }


    }
}
