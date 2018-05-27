package callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ds on 2018-05-27.
 */

public class MultiThreadedMaxFinder {

    public static int max(final int[] data) throws InterruptedException, ExecutionException {

        if (data.length == 1) return data[0];
        else if (data.length == 0) throw new IllegalArgumentException();

        //작업을 둘로 분할
        FindMaxTask task1 = new FindMaxTask(data, 0, data.length/2);
        FindMaxTask task2 = new FindMaxTask(data, data.length/2, data.length);

        //두개의 스레드 생성
        ExecutorService service = Executors.newFixedThreadPool(2);

        //반환값은 Future 에 저장된다
        Future<Integer> future1 = service.submit(task1);
        Future<Integer> future2 = service.submit(task2);

        //future1.get()이 호출 될 때, findMaxTask 가 끝나기를 먼저 기다린다.
        //그다음 future2.get()를 호출한다.
        return Math.max(future1.get(), future2.get());
    }

    public static void main(String... args) {

    }
}
