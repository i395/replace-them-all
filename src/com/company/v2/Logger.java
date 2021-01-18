package com.company.v2;

import static com.company.v2.Runner.BATCH_COUNT;
import static com.company.v2.Runner.BATCH_SIZE;

public class Logger {

    static final boolean VERBOSE = true;

    static void header(String msg, String target, String replacement) {
        var border = "---------------------------------------------------";
        System.out.printf("%s\n%s\n%s\n", border, msg, border);

        if (VERBOSE) {
            System.out.printf("Target: '%s'\tReplacement: '%s'\n\n", target, replacement);
        }
    }

    static void info(String msg) {
        if (VERBOSE) System.out.printf("⋮ %s\n", msg);
    }

    static void result(String input, String output, long millis, long bytes) {
        if (VERBOSE) System.out.printf("⋮ Input:\t%s\n⋮ Output:\t%s\n⋮\n⋮ ", input, output);

        System.out.printf(
                "Total (sec): %f    Avg (millis): %f    Heap (B): %,.0f\n\n",
                (double) millis / 1000,
                (double) millis / (BATCH_COUNT * BATCH_SIZE),
                (double) bytes / BATCH_COUNT);
    }

    static void result(String[] input, String[] output, long millis, long bytes) {
        if (VERBOSE) {
            for (int i = 0; i < input.length; i++) {
                System.out.printf("⋮ In/Out #%d: %s -> %s\n", i, input[i], output[i]);
            }
            System.out.print("⋮\n⋮ ");
        }

        System.out.printf(
                "Total (sec): %f    Avg (millis): %f    Heap (B): %,.0f\n\n",
                (double) millis / 1000,
                (double) millis / (BATCH_COUNT * BATCH_SIZE),
                (double) bytes / BATCH_COUNT);
    }

}
