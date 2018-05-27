package thread.threadPool;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * Created by ds on 2018-05-28.
 */

public class GZipRunnable implements Runnable {

    private final File input;

    public GZipRunnable(final File input) {
        this.input = input;
    }

    @Override
    public void run() {
        //압축 파일일 경우 다시 압축 하지 않는다.
        if (!input.getName().endsWith(".gz")) {
            File output = new File(input.getParent(), input.getName() + ".gz");
            if (!output.exists()) {
                try (
                        InputStream in = new BufferedInputStream(new FileInputStream(input));
                        OutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(output)))
                ) {
                    int b;
                    while ((b = in.read()) != -1) out.write(b);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
