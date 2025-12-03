import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 3 ---");
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
            line = line.trim();
            if (line.isEmpty()) continue;

            int maxJoltage = 0;
            char[] digits = line.toCharArray();

            for (int i = 0; i < digits.length - 1; i++) {
                for (int j = i + 1; j < digits.length; j++) {
                    int digit1 = digits[i] - '0';
                    int digit2 = digits[j] - '0';
                    int val = (digit1 * 10) + digit2;
                    if (val > maxJoltage) {
                        maxJoltage = val;
                    }
                    if (maxJoltage == 99) break;
                }
                if (maxJoltage == 99) break;
            }
            totalSum += maxJoltage;
        }
        System.out.println("Result Part 1: " + totalSum);
    }

    private static void solvePart2(List<String> rawLines) {
        long totalSum = 0;

        for (String line : rawLines) {
            line = line.trim();

            if (line.length() < 12) continue;

            StringBuilder sb = new StringBuilder();
            int currentPos = 0;
            int len = line.length();

            for (int i = 0; i < 12; i++) {

                int remainingNeeded = 11 - i;
                int searchLimit = len - 1 - remainingNeeded;
                char maxDigit = '/';
                int maxDigitIndex = -1;

                for (int j = currentPos; j <= searchLimit; j++) {
                    char c = line.charAt(j);
                    if (c > maxDigit) {
                        maxDigit = c;
                        maxDigitIndex = j;

                        if (c == '9') break;
                    }
                }

                sb.append(maxDigit);
                currentPos = maxDigitIndex + 1;
            }

            totalSum += Long.parseLong(sb.toString());
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