package tcp.thread;

/**
 * Created by ds on 2018-05-24.
 */

public class ThreadExtend extends Thread {
    private String str;

    public ThreadExtend (final String str) {
        this.str = str;
    }

    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println(str);
            try {
                Thread.sleep((int) Math.random() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
