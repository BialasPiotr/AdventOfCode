import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 5 ---");
        System.out.println("Select part of the solution:");
        System.out.println("1 - Part 1");
        System.out.println("2 - Part 2");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();
        String filename = "";

        if (choice.equals("1")) {
            filename = "Resources/input1.txt";
        } else if (choice.equals("2")) {
            filename = "Resources/input1.txt";
        } else {
            System.out.println("Invalid choice");
            scanner.close();
            return;
        }

        List<String> rawLines = readInput(filename);

        if (rawLines.isEmpty()) {
            System.out.println("No solution found (empty file)");
        } else {
            if (choice.equals("1")) {
                solvePart1(rawLines);
            } else {
                solvePart2(rawLines);
            }
        }
        scanner.close();
    }

    private static void solvePart1(List<String> rawLines) {
        List<long[]> freshRanges = new ArrayList<>();
        long freshCount = 0;
        boolean parsingRanges = true;

        for (String line : rawLines) {
            line = line.trim();
            if (line.isEmpty()) {
                parsingRanges = false;
                continue;
            }

            if (parsingRanges) {
                String[] parts = line.split("-");
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                freshRanges.add(new long[]{start, end});
            } else {
                long id = Long.parseLong(line);
                boolean isFresh = false;
                for (long[] range : freshRanges) {
                    if (id >= range[0] && id <= range[1]) {
                        isFresh = true;
                        break;
                    }
                }
                if (isFresh) {
                    freshCount++;
                }
            }
        }
        System.out.println("Result Part 1: " + freshCount);
    }

    private static void solvePart2(List<String> rawLines) {
        List<long[]> ranges = new ArrayList<>();

        for (String line : rawLines) {
            line = line.trim();
            if (line.isEmpty()) break;

            String[] parts = line.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }

        if (ranges.isEmpty()) {
            System.out.println("Result Part 2: 0");
            return;
        }

        ranges.sort(Comparator.comparingLong(a -> a[0]));

        long totalFreshIDs = 0;
        long currentStart = ranges.get(0)[0];
        long currentEnd = ranges.get(0)[1];

        for (int i = 1; i < ranges.size(); i++) {
            long nextStart = ranges.get(i)[0];
            long nextEnd = ranges.get(i)[1];

            if (nextStart <= currentEnd) {
                currentEnd = Math.max(currentEnd, nextEnd);
            } else {
                totalFreshIDs += (currentEnd - currentStart + 1);
                currentStart = nextStart;
                currentEnd = nextEnd;
            }
        }

        totalFreshIDs += (currentEnd - currentStart + 1);

        System.out.println("Result Part 2: " + totalFreshIDs);
    }

    private static List<String> readInput(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
        return lines;
    }
}