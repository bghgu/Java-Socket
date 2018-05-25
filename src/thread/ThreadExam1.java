package thread;

/**
 * Created by ds on 2018-05-24.
 */
public class ThreadExam1 {
    public static void main(String... args) {
        ThreadExtend t1 = new ThreadExtend("1");
        ThreadExtend t2 = new ThreadExtend("2");

        t1.start();
        t2.start();
        System.out.println("!!!!!!");
        /**
         * !!!!!!
         * 1
         * 2
         * 2
         * 1
         * 1
         * 2
         * 1
         * 2
         * 1
         * 2
         * 1
         * 2
         * 1
         * 2
         * 1
         * 2
         * 1
         * 2
         * 1
         * 2
         */
    }
}
