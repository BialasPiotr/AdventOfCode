import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Day7 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 7 ---");
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
        int rows = rawLines.size();
        int cols = rawLines.get(0).length();
        char[][] grid = new char[rows][cols];

        int startR = 0;
        int startC = 0;

        for (int r = 0; r < rows; r++) {
            String line = rawLines.get(r);
            grid[r] = line.toCharArray();
            int sIndex = line.indexOf('S');
            if (sIndex != -1) {
                startR = r;
                startC = sIndex;
            }
        }

        Set<String> hitSplitters = new HashSet<>();
        boolean[][] visited = new boolean[rows][cols];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startR, startC});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int r = pos[0];
            int c = pos[1];

            if (r >= rows || c < 0 || c >= cols) continue;
            if (visited[r][c]) continue;
            visited[r][c] = true;

            char cell = grid[r][c];

            if (cell == '^') {
                hitSplitters.add(r + "," + c);
                queue.add(new int[]{r + 1, c - 1});
                queue.add(new int[]{r + 1, c + 1});
            } else {
                queue.add(new int[]{r + 1, c});
            }
        }

        System.out.println("Result Part 1: " + hitSplitters.size());
    }

    private static void solvePart2(List<String> rawLines) {
        int rows = rawLines.size();
        int cols = rawLines.get(0).length();
        char[][] grid = new char[rows][cols];

        int startC = 0;

        for (int r = 0; r < rows; r++) {
            String line = rawLines.get(r);
            grid[r] = line.toCharArray();
            int sIndex = line.indexOf('S');
            if (sIndex != -1) {
                startC = sIndex;
            }
        }
        Map<Integer, BigInteger> currentParticles = new HashMap<>();
        currentParticles.put(startC, BigInteger.ONE);

        for (int r = 0; r < rows; r++) {
            Map<Integer, BigInteger> nextParticles = new HashMap<>();

            for (Map.Entry<Integer, BigInteger> entry : currentParticles.entrySet()) {
                int c = entry.getKey();
                BigInteger count = entry.getValue();
                char cell = '.';
                if (c >= 0 && c < cols) {
                    cell = grid[r][c];
                }

                if (cell == '^') {
                    nextParticles.merge(c - 1, count, BigInteger::add);
                    nextParticles.merge(c + 1, count, BigInteger::add);
                } else {
                    nextParticles.merge(c, count, BigInteger::add);
                }
            }
            currentParticles = nextParticles;
        }
        BigInteger totalTimelines = BigInteger.ZERO;
        for (BigInteger count : currentParticles.values()) {
            totalTimelines = totalTimelines.add(count);
        }

        System.out.println("Result Part 2: " + totalTimelines);
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