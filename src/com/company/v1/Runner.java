package com.company.v1;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.company.v1.Logger.info;
import static com.company.v1.Logger.result;

public class Runner {

    public static final int BATCH_COUNT = 20;
    public static final int BATCH_SIZE = 5_000_000;

    private static final long RUNS = 4;
    private static final long COOL_DOWN = 120;

    private static final List<MemoryPoolMXBean> MX_HEAP_BEANS = getMxHeapBeans();

    public static void run(Replacer replacer, String inputString) throws InterruptedException {
        for (int i = 0; i < RUNS; i++) {
            info("run #" + i);
            start(replacer, inputString);
        }
    }

    private static void start(Replacer replacer, String input) throws InterruptedException {
        long millis = 0L;
        long bytes  = 0L;
        String output = null;

        for (int i = 0; i < BATCH_COUNT; i++) {
            coolDown();
            long start = System.currentTimeMillis();
            for (int k = 0; k < BATCH_SIZE; k++) {
                output = replacer.replace(input);
            }
            millis = millis + (System.currentTimeMillis() - start);
            bytes = bytes + getHeapUsage();
        }
        result(input, output, millis, bytes);
    }

    private static void coolDown() throws InterruptedException {
        System.gc();
        TimeUnit.MILLISECONDS.sleep(COOL_DOWN);
    }

    private static long getHeapUsage() {
        return MX_HEAP_BEANS.stream()
                //.peek(heapUsage -> System.out.printf("%s %,d\n", heapUsage.getName(), heapUsage.getUsage().getUsed()))
                .map(MemoryPoolMXBean::getUsage)
                .mapToLong(MemoryUsage::getUsed)
                .sum();
    }

    private static List<MemoryPoolMXBean> getMxHeapBeans() {
        return ManagementFactory.getMemoryPoolMXBeans().stream()
                .filter(mxBean -> mxBean.getType() == MemoryType.HEAP)
                .collect(Collectors.toList());
    }

}
