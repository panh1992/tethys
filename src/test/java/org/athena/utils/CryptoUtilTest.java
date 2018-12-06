package org.athena.utils;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CryptoUtilTest {

    @Test
    public void getUUID() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getName() + ": \t" + CryptoUtil.getUUID()));
        }

        executor.shutdown();
        executor.awaitTermination(60L, TimeUnit.SECONDS);

    }

}
