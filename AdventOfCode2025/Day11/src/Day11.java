import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day11 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 11 ---");
        System.out.println("Select part of the solution:");
        System.out.println("1 - Part 1");
        System.out.println("2 - Part 2");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();
        String filename = "";

        if (choice.equals("1")) {
            filename = "Resources/input1.txt";
        } else if (choice.equals("2")) {
            filename = "Resources/input2.txt";
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

    static Map<String, List<String>> graph = new HashMap<>();
    static Map<String, Long> memoPart1 = new HashMap<>();

    private static void solvePart1(List<String> rawLines) {
        buildGraph(rawLines);
        memoPart1.clear();

        long totalPaths = countPathsPart1("you");
        System.out.println("Result Part 1: " + totalPaths);
    }

    private static long countPathsPart1(String current) {
        if (current.equals("out")) return 1;
        if (memoPart1.containsKey(current)) return memoPart1.get(current);

        long count = 0;
        if (graph.containsKey(current)) {
            for (String neighbor : graph.get(current)) {
                count += countPathsPart1(neighbor);
            }
        }
        memoPart1.put(current, count);
        return count;
    }

    private static void solvePart2(List<String> rawLines) {
        buildGraph(rawLines);

        long path1 = 0;
        long leg1a = countPathsBetween("svr", "dac");
        if (leg1a > 0) {
            long leg1b = countPathsBetween("dac", "fft");
            if (leg1b > 0) {
                long leg1c = countPathsBetween("fft", "out");
                path1 = leg1a * leg1b * leg1c;
            }
        }

        long path2 = 0;
        long leg2a = countPathsBetween("svr", "fft");
        if (leg2a > 0) {
            long leg2b = countPathsBetween("fft", "dac");
            if (leg2b > 0) {
                long leg2c = countPathsBetween("dac", "out");
                path2 = leg2a * leg2b * leg2c;
            }
        }

        System.out.println("Result Part 2: " + (path1 + path2));
    }

    private static long countPathsBetween(String start, String end) {
        Map<String, Long> localMemo = new HashMap<>();
        return dfs(start, end, localMemo);
    }

    private static long dfs(String current, String target, Map<String, Long> memo) {
        if (current.equals(target)) return 1;
        if (memo.containsKey(current)) return memo.get(current);

        long count = 0;
        if (graph.containsKey(current)) {
            for (String neighbor : graph.get(current)) {
                count += dfs(neighbor, target, memo);
            }
        }
        memo.put(current, count);
        return count;
    }

    private static void buildGraph(List<String> rawLines) {
        graph.clear();
        for (String line : rawLines) {
            // Format: "aaa: you hhh"
            String[] parts = line.split(": ");
            String source = parts[0];

            if (parts.length > 1) {
                String[] destinations = parts[1].split(" ");
                graph.put(source, Arrays.asList(destinations));
            } else {
                graph.put(source, new ArrayList<>());
            }
        }
    }

    private static List<String> readInput(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
        return lines;
    }
}