import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 1 ---");
        System.out.println("Select part of the solution:");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();
        String filename = "";

        if (choice.equals("1")) {
            filename = "Resources/input1.txt";
        } else if (choice.equals("2")) {
            filename = "Resources/input2.txt";
        } else {
            System.out.println("Incorrect selection.");
            scanner.close();
            return;
        }
        List<String> instructions = readInput(filename);

        if (instructions.isEmpty()) {
            System.out.println("The file is empty or does not exist.");
        } else {

            if (choice.equals("1")) {
                solvePart1(instructions);
            } else {
                solvePart2(instructions);
            }
        }

        scanner.close();
    }

    private static void solvePart1(List<String> instructions) {
        int currentPosition = 50;
        int zeroCount = 0;

        for (String line : instructions) {
            char direction = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));

            if (direction == 'R') {
                currentPosition = (currentPosition + value) % 100;
            } else if (direction == 'L') {
                currentPosition = (currentPosition - value) % 100;
                if (currentPosition < 0) currentPosition += 100;
            }

            if (currentPosition == 0) {
                zeroCount++;
            }
        }
        System.out.println("Result Part 1: " + zeroCount);
    }

    private static void solvePart2(List<String> instructions) {
        int currentPosition = 50;
        int zeroCount = 0;

        for (String line : instructions) {
            char direction = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));

            for (int i = 0; i < value; i++) {
                if (direction == 'R') {
                    currentPosition++;
                    if (currentPosition == 100) currentPosition = 0;
                } else {
                    currentPosition--;
                    if (currentPosition < 0) currentPosition = 99;
                }

                if (currentPosition == 0) {
                    zeroCount++;
                }
            }
        }
        System.out.println("Result Part 2: " + zeroCount);
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