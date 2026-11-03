
package daa;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExperimentRunner {

    private static final int RUNS = 3;
    private static final long SEED = 42;

    private static final int[] EDIT_DP_SIZES = {
            100, 200, 400, 800
    };

    private static final int[] RECURSIVE_SIZES = {
            2, 4, 6, 8
    };

    private static final int[] SEARCH_SIZES = {
            1000, 2000, 4000, 8000
    };

    private static final String[] INPUT_TYPES = {
            "random", "mismatch-heavy"
    };

    private static BufferedWriter writer;

    public static void main(String[] args) throws IOException {

        Files.createDirectories(Path.of("results"));

        try (BufferedWriter output =
                     Files.newBufferedWriter(
                             Path.of("results/results.csv"))) {

            writer = output;

            writer.write(
                    "algorithm,input,n,run,time_ms,"
                    + "median_time_ms,counter,memory_cells"
            );

            writer.newLine();

            runEditDistanceExperiments();
            runStringSearchExperiments();
        }

        System.out.println("Experiment completed!");
        System.out.println(
                "Results saved to results/results.csv"
        );
    }

    private static void runEditDistanceExperiments()
            throws IOException {

        System.out.println("Running Edit Distance experiments...");

        for (String input : INPUT_TYPES) {

            for (int n : RECURSIVE_SIZES) {

                String a = generateString(n, input, SEED + n);
                String b = generateSecondString(n, input, SEED + n);

                measureEdit(
                        "Recursive", input, n, a, b, 0
                );
            }

            for (int n : EDIT_DP_SIZES) {

                String a = generateString(n, input, SEED + n);
                String b = generateSecondString(n, input, SEED + n);

                long tableCells = (long) (n + 1) * (n + 1);

                measureEdit(
                        "DP-Table", input, n, a, b, tableCells
                );

                measureEdit(
                        "Two-Rows", input, n, a, b,
                        2L * (n + 1)
                );
            }
        }
    }

    private static void measureEdit(
            String algorithm,
            String input,
            int n,
            String a,
            String b,
            long memoryCells) throws IOException {

        List<Double> times = new ArrayList<>();
        List<Long> counters = new ArrayList<>();

        for (int run = 1; run <= RUNS; run++) {

            Counter counter = new Counter();

            long start = System.nanoTime();

            int result;

            switch (algorithm) {

                case "Recursive":
                    result = EditDistance.recursive(
                            a, b, counter
                    );
                    break;

                case "DP-Table":
                    result = EditDistance.table(
                            a, b, counter
                    );
                    break;

                default:
                    result = EditDistance.twoRows(
                            a, b, counter
                    );
            }

            long end = System.nanoTime();

            if (result < 0) {
                throw new IllegalStateException(
                        "Invalid edit distance"
                );
            }

            double timeMs = (end - start) / 1_000_000.0;

            times.add(timeMs);
            counters.add(counter.getCount());
        }

        double median = median(times);

        for (int i = 0; i < RUNS; i++) {

            writeRow(
                    algorithm,
                    input,
                    n,
                    i + 1,
                    times.get(i),
                    median,
                    counters.get(i),
                    memoryCells
            );
        }
    }

    private static void runStringSearchExperiments()
            throws IOException {

        System.out.println("Running String Search experiments...");

        for (String input : new String[]{
                "early-mismatch", "repetitive-near-match"
        }) {

            for (int n : SEARCH_SIZES) {

                String text = generateSearchText(n);

                String pattern;

                if (input.equals("early-mismatch")) {
                    pattern = "B" + "A".repeat(19);
                } else {
                    pattern = "A".repeat(19) + "B";
                }

                measureSearch(
                        "Naive", input, n, text, pattern
                );

                measureSearch(
                        "KMP", input, n, text, pattern
                );
            }
        }
    }

    private static void measureSearch(
            String algorithm,
            String input,
            int n,
            String text,
            String pattern) throws IOException {

        List<Double> times = new ArrayList<>();
        List<Long> counters = new ArrayList<>();

        for (int run = 1; run <= RUNS; run++) {

            Counter counter = new Counter();

            long start = System.nanoTime();

            if (algorithm.equals("Naive")) {

                StringSearch.naive(text, pattern, counter);

            } else {

                StringSearch.kmp(text, pattern, counter);
            }

            long end = System.nanoTime();

            double timeMs = (end - start) / 1_000_000.0;

            times.add(timeMs);
            counters.add(counter.getCount());
        }

        double median = median(times);

        for (int i = 0; i < RUNS; i++) {

            writeRow(
                    algorithm,
                    input,
                    n,
                    i + 1,
                    times.get(i),
                    median,
                    counters.get(i),
                    0
            );
        }
    }

    private static String generateString(
            int n, String input, long seed) {

        if (input.equals("mismatch-heavy")) {
            return "A".repeat(n);
        }

        return randomString(n, seed);
    }

    private static String generateSecondString(
            int n, String input, long seed) {

        if (input.equals("mismatch-heavy")) {
            return "B".repeat(n);
        }

        return randomString(n, seed + 1000);
    }

    private static String randomString(int n, long seed) {

        Random random = new Random(seed);

        StringBuilder result = new StringBuilder();

        String alphabet = "ABC";

        for (int i = 0; i < n; i++) {

            result.append(
                    alphabet.charAt(
                            random.nextInt(alphabet.length())
                    )
            );
        }

        return result.toString();
    }

    private static String generateSearchText(int n) {

        return "A".repeat(n);
    }

    private static double median(List<Double> values) {

        List<Double> sorted = new ArrayList<>(values);

        Collections.sort(sorted);

        return sorted.get(sorted.size() / 2);
    }

    private static void writeRow(
            String algorithm,
            String input,
            int n,
            int run,
            double time,
            double median,
            long counter,
            long memoryCells) throws IOException {

        writer.write(
                algorithm + ","
                + input + ","
                + n + ","
                + run + ","
                + time + ","
                + median + ","
                + counter + ","
                + memoryCells
        );

        writer.newLine();
    }
}
