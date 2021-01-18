package com.company.v2;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Runner {

    static final int RUNS_COUNT = 4;
    static final int BATCH_COUNT = 20;
    static final int BATCH_SIZE = 5_000_000;
    static final long COOL_DOWN_MILLIS = 200;

    private static final List<MemoryPoolMXBean> MX_HEAP_BEANS = getMxHeapBeans();

    static void singleCase(final Function<String, String> replacer, final String input) {
        for (int i = 0; i < RUNS_COUNT; i++) {
            Logger.info("run #" + i);

            long millis = 0L;
            long bytes = 0L;
            String output = null;

            for (int j = 0; j < BATCH_COUNT; j++) {
                cleanUp();

                long start = System.currentTimeMillis();
                for (int k = 0; k < BATCH_SIZE; k++) {
                    output = replacer.apply(input);
                }
                millis = millis + (System.currentTimeMillis() - start);
                bytes = bytes + getHeapUsage();
            }

            Logger.result(input, output, millis, bytes);
        }
    }

    static void mixedCase(Function<String, String> replacer, String... input) {
        for (int runNo = 0; runNo < RUNS_COUNT; runNo++) {
            Logger.info("run #" + runNo);

            long millis = 0L;
            long bytes = 0L;
            String[] output = new String[input.length];

            for (int batchNo = 0; batchNo < BATCH_COUNT; batchNo++) {
                cleanUp();

                long start = System.currentTimeMillis();
                for (int callNo = 0; callNo < BATCH_SIZE; ) {
                    for (int inputIdx = 0; inputIdx < input.length; inputIdx++) {
                        output[inputIdx] = replacer.apply(input[inputIdx]);
                        callNo++;
                    }
                }
                millis = millis + (System.currentTimeMillis() - start);
                bytes = bytes + getHeapUsage();
            }

            Logger.result(input, output, millis, bytes);
        }
    }

    private static void cleanUp() {
        System.gc();
        try {
            TimeUnit.MILLISECONDS.sleep(COOL_DOWN_MILLIS);
        } catch (InterruptedException e) {
            System.err.println("Something went wrong");
        }
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
