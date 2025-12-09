import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Advent of Code 2025: Day 9 ---");
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

    static class Point {
        long x, y;
        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void solvePart1(List<String> rawLines) {
        List<Point> points = parsePoints(rawLines);
        long maxArea = 0;

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);

                long width = Math.abs(p1.x - p2.x) + 1;
                long height = Math.abs(p1.y - p2.y) + 1;
                long area = width * height;

                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        System.out.println("Result Part 1: " + maxArea);
    }

    private static void solvePart2(List<String> rawLines) {
        List<Point> polygon = parsePoints(rawLines);
        long maxValidArea = 0;
        for (int i = 0; i < polygon.size(); i++) {
            for (int j = i + 1; j < polygon.size(); j++) {
                Point p1 = polygon.get(i);
                Point p2 = polygon.get(j);

                long minX = Math.min(p1.x, p2.x);
                long maxX = Math.max(p1.x, p2.x);
                long minY = Math.min(p1.y, p2.y);
                long maxY = Math.max(p1.y, p2.y);

                long width = maxX - minX + 1;
                long height = maxY - minY + 1;
                long area = width * height;

                if (area <= maxValidArea) continue;

                double midX = (minX + maxX) / 2.0;
                double midY = (minY + maxY) / 2.0;

                if (!isPointInPolygon(midX, midY, polygon)) {
                    continue;
                }

                if (polygonEdgesIntersectRect(polygon, minX, maxX, minY, maxY)) {
                    continue;
                }

                maxValidArea = area;
            }
        }

        System.out.println("Result Part 2: " + maxValidArea);
    }

    private static boolean isPointInPolygon(double x, double y, List<Point> polygon) {
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Point pi = polygon.get(i);
            Point pj = polygon.get(j);

            if (((pi.y > y) != (pj.y > y)) &&
                    (x < (pj.x - pi.x) * (y - pi.y) / (double)(pj.y - pi.y) + pi.x)) {
                inside = !inside;
            }
        }
        return inside;
    }

    private static boolean polygonEdgesIntersectRect(List<Point> polygon, long minX, long maxX, long minY, long maxY) {
        int n = polygon.size();
        for (int i = 0; i < n; i++) {
            Point p1 = polygon.get(i);
            Point p2 = polygon.get((i + 1) % n);

            if (p1.y == p2.y) {
                long y = p1.y;
                long startX = Math.min(p1.x, p2.x);
                long endX = Math.max(p1.x, p2.x);

                if (y > minY && y < maxY) {
                    if (Math.max(startX, minX) < Math.min(endX, maxX)) {
                        return true;
                    }
                }
            }
            else if (p1.x == p2.x) {
                long x = p1.x;
                long startY = Math.min(p1.y, p2.y);
                long endY = Math.max(p1.y, p2.y);

                if (x > minX && x < maxX) {
                    if (Math.max(startY, minY) < Math.min(endY, maxY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static List<Point> parsePoints(List<String> rawLines) {
        List<Point> points = new ArrayList<>();
        for (String line : rawLines) {
            String[] parts = line.split(",");
            long x = Long.parseLong(parts[0].trim());
            long y = Long.parseLong(parts[1].trim());
            points.add(new Point(x, y));
        }
        return points;
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