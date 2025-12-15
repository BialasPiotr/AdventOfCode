import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 12 ---");
        System.out.println("Select part of the solution:");
        System.out.println("1 - Part 1");
        System.out.print("Your choice: ");

        String choice = scanner.nextLine();
        String filename = "";

        if (choice.equals("1")) {
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
            }
        }
        scanner.close();
    }

    private static void solvePart1(List<String> rawLines) {
        Map<Integer, Integer> shapeAreas = new HashMap<>();
        int currentShapeIndex = -1;
        int currentShapeArea = 0;
        long validRegionsCount = 0;
        Pattern queryPattern = Pattern.compile("(\\d+)x(\\d+):([\\d\\s]+)");

        for (String line : rawLines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (line.matches("\\d+:")) {

                if (currentShapeIndex != -1) {
                    shapeAreas.put(currentShapeIndex, currentShapeArea);
                }

                currentShapeIndex = Integer.parseInt(line.replace(":", ""));
                currentShapeArea = 0;
                continue;
            }

            if (line.contains("#") || line.contains(".")) {
                for (char c : line.toCharArray()) {
                    if (c == '#') {
                        currentShapeArea++;
                    }
                }
                continue;
            }

            if (currentShapeIndex != -1 && !shapeAreas.containsKey(currentShapeIndex)) {
                shapeAreas.put(currentShapeIndex, currentShapeArea);
            }

            Matcher m = queryPattern.matcher(line);
            if (m.find()) {
                long regionWidth = Long.parseLong(m.group(1));
                long regionHeight = Long.parseLong(m.group(2));
                long regionArea = regionWidth * regionHeight;

                String countsPart = m.group(3).trim();
                String[] counts = countsPart.split("\\s+");

                long presentsTotalArea = 0;
                for (int i = 0; i < counts.length; i++) {
                    int count = Integer.parseInt(counts[i]);
                    int shapeArea = shapeAreas.getOrDefault(i, 0);
                    presentsTotalArea += count * shapeArea;
                }
                if (presentsTotalArea <= regionArea) {
                    validRegionsCount++;
                }
            }
        }

        System.out.println("Result: " + validRegionsCount);
    }

    private static List<String> readInput(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("[")) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
        return lines;
    }
}