package Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 4 ---");
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

        for (int i = 0; i < rows; i++) {
            grid[i] = rawLines.get(i).toCharArray();
        }

        long accessibleCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '@') {
                    if (isAccessible(grid, r, c, rows, cols)) {
                        accessibleCount++;
                    }
                }
            }
        }

        System.out.println("Result Part 1: " + accessibleCount);
    }

    private static boolean isAccessible(char[][] grid, int r, int c, int rows, int cols) {
        int neighborRolls = 0;
        int[] dr = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dc = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                if (grid[nr][nc] == '@') {
                    neighborRolls++;
                }
            }
        }

        return neighborRolls < 4;
    }

    private static void solvePart2(List<String> rawLines) {
       int rows = rawLines.size();
       int cols = rawLines.get(0).length();
       char[][] grid = new char[rows][cols];

       for (int i = 0; i < rows; i++){
           grid[i] = rawLines.get(i).toCharArray();
       }

       long totalRemoved = 0;
       boolean changed;

        do {
            changed = false;
            List<int[]> toRemove = new ArrayList<>();

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (grid[r][c] == '@') {
                        if (countNeighbors(grid, r, c, rows, cols) < 4) {
                            toRemove.add(new int[]{r, c});
                        }
                    }
                }
            }

            if (!toRemove.isEmpty()) {
                changed = true;
                totalRemoved += toRemove.size();

                for (int[] coords : toRemove) {
                    grid[coords[0]][coords[1]] = '.';
                }
            }

        } while (changed);

        System.out.println("Result Part 2: " + totalRemoved);
    }

    private static int countNeighbors(char[][] grid, int r, int c, int rows, int cols) {
        int neighborRolls = 0;
        int[] dr = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dc = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                if (grid[nr][nc] == '@') {
                    neighborRolls++;
                }
            }
        }
        return neighborRolls;
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