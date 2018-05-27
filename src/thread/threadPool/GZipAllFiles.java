package thread.threadPool;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ds on 2018-05-28.
 */

/**
 * Executors 클래스를 사용하면 스레드 풀을 쉽게 만들 수 있다.
 * 간단히 수행할 작업을 Runnable 객체로 풀에 등록하기만 하면 작업의 진행 상황을 검사할 수 있는 Future 객체가 반환된다.
 */

public class GZipAllFiles {

    public final static int THREAD_COUNT = 4;

    public static void main(String... args) {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

        for(String filename : args) {
            File f = new File(filename);
            if (f.exists()) {
                if(f.isDirectory()) {
                    File[] files = f.listFiles();
                    for(int i = 0; i < files.length; i++) {
                        //디렉터리 아래에 있는 디렉터리는 처리하지 않는다.
                        if(!files[i].isDirectory()) {
                            Runnable task = new GZipRunnable(files[i]);
                            pool.submit(task);
                        }
                    }
                }
            } else {
                Runnable task = new GZipRunnable(f);
                pool.submit(task);
            }
        }

        /**
         * 풀에 여전히 처리해야 할 작업이 남아 잇을 때 호출되지만 대기 중인 작업을 중단시키지 않는다.
         * 풀에게 더 이상 추가적인 작업이 내부 큐에 추가될 수 없으며 대기 중인 작업이 모두 끝나는 즉시 종료된다.
         */
        pool.shutdown();
    }
}
