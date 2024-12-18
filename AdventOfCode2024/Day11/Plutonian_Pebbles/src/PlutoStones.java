import java.io.*;
import java.util.*;

public class PlutoStones {

    public static void main(String[] args) throws IOException {
        String input = readInput("input.txt");
        long[] totals = new long[2];
        solve(input, totals);
        System.out.println("After 25 changes: " + totals[0]);
        System.out.println("After 75 changes: " + totals[1]);
    }

    private static String readInput(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.readLine();
        }
    }

    private static void solve(String input, long[] totals) {
        int part = 0;
        for (int max : Arrays.asList(25, 75)) {
            Map<Long, Long> stones = new HashMap<>();
            long total = 0;

            for (String s : input.split(" ")) {
                process(Long.parseLong(s), 1L, stones, total);
            }

            for (int blink = 1; blink <= max; blink++) {
                total = 0;
                List<Map.Entry<Long, Long>> list = new ArrayList<>(stones.entrySet());
                stones.clear();

                for (Map.Entry<Long, Long> stone : list) {
                    long key = stone.getKey();
                    long value = stone.getValue();

                    if (key == 0) {
                        process(1, value, stones, total);
                    } else if (numDigits(key) % 2 == 0) {
                        long tens = (long) Math.pow(10, numDigits(key) / 2);
                        long left = key / tens;
                        long right = key % tens;
                        process(left, value, stones, total);
                        process(right, value, stones, total);
                    } else {
                        process(key * 2024, value, stones, total);
                    }
                }
            }
            totals[part++] = stones.values().stream().mapToLong(Long::longValue).sum();
        }
    }

    private static void process(long stoneNum, long toAdd, Map<Long, Long> stones, long total) {
        stones.put(stoneNum, stones.getOrDefault(stoneNum, 0L) + toAdd);
    }

    private static int numDigits(long number) {
        return (int) Math.floor(Math.log10(number) + 1);
    }
}
