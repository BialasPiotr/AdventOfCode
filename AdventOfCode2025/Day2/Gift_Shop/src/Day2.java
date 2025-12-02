import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 2 ---");
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

    private static void solvePart1(List<String> rawLines) {
        long totalSum = 0;

        for (String line : rawLines) {
            String[] ranges = line.trim().split(",");

            for (String range : ranges) {
                if (range.trim().isEmpty()) continue;

                String[] parts = range.split("-");
                if (parts.length != 2) continue;

                long start = Long.parseLong(parts[0].trim());
                long end = Long.parseLong(parts[1].trim());

                for (long id = start; id <= end; id++) {

                    String s = String.valueOf(id);
                    int len = s.length();


                    if (len % 2 == 0) {
                        String firstHalf = s.substring(0, len / 2);
                        String secondHalf = s.substring(len / 2);

                        if (firstHalf.equals(secondHalf)) {
                            totalSum += id;
                        }
                    }
                }
            }
        }
        System.out.println("Result Part 1: " + totalSum);
    }

    private static void solvePart2(List<String> rawLines) {
        long totalSum = 0;

        for (String line : rawLines) {
            String[] ranges = line.trim().split(",");

            for (String range : ranges) {
                if (range.trim().isEmpty()) continue;

                String[] parts = range.split("-");
                if (parts.length != 2) continue;

                long start = Long.parseLong(parts[0].trim());
                long end = Long.parseLong(parts[1].trim());

                for (long id = start; id <= end; id++) {
                    String s = String.valueOf(id);
                    int len = s.length();
                    boolean foundPattern = false;

                    for (int patternLen = 1; patternLen <= len / 2; patternLen++) {

                        if (len % patternLen != 0) {
                            continue;
                        }

                        String pattern = s.substring(0, patternLen);
                        StringBuilder builder = new StringBuilder();
                        int repeats = len / patternLen;

                        for (int i = 0; i < repeats; i++) {
                            builder.append(pattern);
                        }

                        if (builder.toString().equals(s)) {
                            foundPattern = true;
                            break;
                        }
                    }

                    if (foundPattern) {
                        totalSum += id;
                    }
                }
            }
        }
        System.out.println("Result Part 2: " + totalSum);
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