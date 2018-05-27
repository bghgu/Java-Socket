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
    public void writeEntity1(final String message) throws IOException {
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
    public void writeEntity2(final String message) throws IOException {
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
    public void writeEntry3(final String message) throws IOException {
        synchronized (this) {
            Date d = new Date();
            out.write(d.toString());
            out.write('\t');
            out.write(message);
            out.write("\r\n");
        }
    }

    /**
     * 동기화 지정자(modifier) 사용
     * this 가 참조하는 현재 객체의 메소드 전체를 동기화
     * 많은 가상 머신에서 심각한 성능 저하가 발생하며 여러가지 요인에 의해 코드의 실행 속도가 느려진다.
     * 데드락 발생 가능성이 급격히 높아진다.
     * 동시 변경이나 접근으로부터 보호하기 위해 항상 객체 자체를 보호해야 하는 것은 아니며, 해당 메소드를 포함한 클래스의 인스턴스를 동기화해도 실제 보호해야 할 객체를 보호하지 못할 수도 있다.
     * @param message
     * @throws IOException
     */
    public synchronized void writeEntity4(final String message) throws IOException {
        Date d = new Date();
        out.write(d.toString());
        out.write('\t');
        out.write(message);
        out.write("\r\n");
    }

    public void close() throws IOException {
        out.flush();
        out.close();
    }
}
