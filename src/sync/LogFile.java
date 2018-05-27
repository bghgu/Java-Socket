package sync;

import java.io.*;
import java.util.Date;

/**
 * Created by ds on 2018-05-27.
 */

/**
 * 로그 파일을 쓰는 클래스
 */

public class LogFile {
    private Writer out;

    public LogFile(final File file) throws IOException {
        this.out = new BufferedWriter(new FileWriter(file));
    }

    /**
     * 자체로는 멀티스레드로 동작하지 않는다.
     * @param message
     * @throws IOException
     */
    public void writeEntity(final String message) throws IOException {
        Date d = new Date();
        out.write(d.toString());
        out.write('\t');
        out.write(message);
        out.write("\r\n");
    }

    /**
     * out 객체 동기화
     * out이 private로 선언된 것은 문제가 되지 않는다.
     * 비록 다른 스레드나 객체에 의해 사용되기는 하지만, sync.LogFile 클래스 안에서만 참조되기 때문이다.
     * @param message
     * @throws IOException
     */
    public void writeEntitySync(final String message) throws IOException {
        synchronized (out) {
            Date d = new Date();
            out.write(d.toString());
            out.write('\t');
            out.write(message);
            out.write("\r\n");
        }
    }

    /**
     * sync.LogFile 객체 자체를 동기화
     * @param message
     * @throws IOException
     */
    public void writeEntry(final String message) throws IOException {
        synchronized (this) {
            Date d = new Date();
            out.write(d.toString());
            out.write('\t');
            out.write(message);
            out.write("\r\n");
        }
    }

    public void close() throws IOException {
        out.flush();
        out.close();
    }
}
