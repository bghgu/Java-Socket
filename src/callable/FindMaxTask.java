package callable;

import java.util.concurrent.Callable;

/**
 * Created by ds on 2018-05-27.
 */

/**
 * 큰 숫자 배열에서 최대값을 찾는다.
 * 다수의 스레드에 나눈 다음, 각 스레드를 개별 cpu 코어에 할당하면 더 빠르게 찾을 수 있다.
 */

public class FindMaxTask implements Callable<Integer> {

    private int[] data;
    private int start;
    private int end;

    public FindMaxTask(final int[] data, final int start, final int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int max = Integer.MAX_VALUE;
        for(int i = start; i < end; i++) {
            max = Math.max(max, data[i]);
        }
        return null;
    }
}
